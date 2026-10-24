package ecommerce.backend.service.impl.product;

import ecommerce.backend.cloudinary.CloudImageService;
import ecommerce.backend.constant.ProductStatus;
import ecommerce.backend.constant.ProductVariantStatus;
import ecommerce.backend.constant.Role;
import ecommerce.backend.exception.UserNotFoundException;
import ecommerce.backend.mapper.Mapper;
import ecommerce.backend.model.brand.Brand;
import ecommerce.backend.model.category.Category;
import ecommerce.backend.model.category.SubCategory;
import ecommerce.backend.model.product.*;
import ecommerce.backend.model.seller.Seller;
import ecommerce.backend.model.user.User;

import ecommerce.backend.payload.dtoRequest.product.*;
import ecommerce.backend.payload.dtoResponse.APIResponse;
import ecommerce.backend.payload.dtoResponse.product.ProductResponseDTO;
import ecommerce.backend.repository.BrandRepository;
import ecommerce.backend.repository.SellerRepository;
import ecommerce.backend.repository.UserRepository;
import ecommerce.backend.repository.category.CategoryRepository;
import ecommerce.backend.repository.category.SubCategoryRepository;
import ecommerce.backend.repository.product.*;
import ecommerce.backend.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {


    private final ProductRepository productRepository;
    private final ProductAttributeRepository productAttributeRepository;
    private final ProductOptionRepository productOptionRepository;
    private  final ProductOptionValueRepository productOptionValueRepository;
    private final ProductVariantRepository productVariantRepository;
    private final ProductVariantOptionRepository productVariantOptionRepository;
    private final ProductImageRepository productImageRepository;
    private final InventoryRepository inventoryRepository;

    private final SellerRepository sellerRepository;
    private final UserRepository userRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;

    private final CloudImageService cloudImageService;


    @Override
    @Transactional
    public APIResponse<ProductResponseDTO> createNewProduct(ProductCreateRequestDTO productCreateRequestDTO, List<MultipartFile> images, String loggedInEmail)  {

        //Logged-In User

        User user=userRepository.findByEmail(loggedInEmail).orElseThrow(
                ()-> new UserNotFoundException("Seller not found "+loggedInEmail)
        );

        Seller seller=sellerRepository.findByUser(user).orElseThrow(
                ()-> new UserNotFoundException("Seller not found "+loggedInEmail)
        );

        // Only Seller

        if(user.getRole() != Role.SELLER){
            return new APIResponse<>(
                    false,
                    "Only Seller can add products",
                    null
            );
        }

        // find subCategory
        SubCategory subCategory=subCategoryRepository.findById(productCreateRequestDTO.getSubCategoryId()).orElseThrow(
                ()-> new RuntimeException("SubCategory not found "+productCreateRequestDTO.getSubCategoryId())
        );


        // find brand
        Brand brand=brandRepository.findById(productCreateRequestDTO.getBrandId()).orElseThrow(
                ()-> new RuntimeException("Brand not found "+productCreateRequestDTO.getBrandId())
        );

        Product product=new Product();
        product.setSeller(seller);
        product.setSubCategory(subCategory);
        product.setBrand(brand);
        product.setProductTitle(productCreateRequestDTO.getProductTitle());
        product.setSlug(productCreateRequestDTO.getSlug());
        product.setDescription(productCreateRequestDTO.getDescription());
        product.setShortDescription(productCreateRequestDTO.getShortDescription());
        product.setFeatured(Boolean.TRUE.equals(productCreateRequestDTO.getFeatured()));
        product.setStatus(ProductStatus.ACTIVE);
        product.setIsWishlisted(Boolean.FALSE.equals(productCreateRequestDTO.getIsWishlisted()));
        product.setSubCategory(subCategory);



        product = productRepository.save(product);


        // attributes

        if(productCreateRequestDTO.getAttributes()!=null) {


            Set<String> attributeNames = new HashSet<>();

            for (ProductAttributeRequestDTO dto : productCreateRequestDTO.getAttributes()) {
                String name = dto.getName();
                if (!attributeNames.add(name.toLowerCase())) {
                    throw new RuntimeException("Duplicate attribute name " + name);
                }


                ProductAttribute productAttribute = new ProductAttribute();

                productAttribute.setProduct(product);
                productAttribute.setAttributeName(name);
                productAttribute.setAttributeValue(dto.getValue());
                productAttributeRepository.save(productAttribute);
            }


        }

        // options and values

        Map<String, ProductOption> productOptionMap =new HashMap<>();

        Map<String ,Map<String, ProductOptionValue>> productOptionValueMap=new HashMap<>();

        if(productCreateRequestDTO.getOptions()!=null && !productCreateRequestDTO.getOptions().isEmpty()) {

            Set<String> optionNames = new HashSet<>();


            for(ProductOptionRequestDTO dto : productCreateRequestDTO.getOptions()){

                String optionName = dto.getName().trim();
                String optionKey=optionName.toLowerCase();

                if (!optionNames.add(optionName)) {
                    throw new RuntimeException("Duplicate option name " + optionName);
                }

                ProductOption productOption = new ProductOption();
                productOption.setProduct(product);
                productOption.setName(optionName);

                productOption = productOptionRepository.save(productOption);

                productOptionMap.put(optionName.toLowerCase(), productOption);

                Map<String,ProductOptionValue> values=new HashMap<>();

                Set<String> uniqueValues=new HashSet<>();

                if(dto.getValues() != null && !dto.getValues().isEmpty()) {

                    for(ProductOptionValueRequestDTO valueDTO : dto.getValues()){
                        String cleanValue=valueDTO.getValue();
                        String valueKey=cleanValue.toLowerCase();

                        if(!uniqueValues.add(valueKey)){
                            throw new RuntimeException("Duplicate value " + valueKey + " for " + cleanValue);
                        }

                        ProductOptionValue productOptionValue = new ProductOptionValue();
                        productOptionValue.setOption(productOption);
                        productOptionValue.setValue(cleanValue);
                        productOptionValue=productOptionValueRepository.save(productOptionValue);
                        values.put(valueKey,productOptionValue);
                    }
                }
                productOptionValueMap.put(optionKey,values);
                }
        }


        // variants

        if(productCreateRequestDTO.getVariants()!=null && !productCreateRequestDTO.getVariants().isEmpty()) {


            Set<String> variantSkus = new HashSet<>();
            for (ProductVariantRequestDTO dto : productCreateRequestDTO.getVariants()) {
                String sku = dto.getSku();
                if (!variantSkus.add(sku.toLowerCase())) {
                    throw new RuntimeException("Duplicate sku " + sku);
                }

                if (productVariantRepository.existsBySku(sku)) {
                    throw new RuntimeException("SKU already exists " + sku);
                }

                ProductVariant productVariant = new ProductVariant();

                productVariant.setProduct(product);
                productVariant.setSku(sku);

                productVariant.setPrice(dto.getPrice());
                productVariant.setWeight(dto.getWeight());

                BigDecimal discountPercentage = dto.getDiscountPercentage() == null
                        ? BigDecimal.ZERO
                        : dto.getDiscountPercentage();

                // calculate discount

                BigDecimal discountAmount = dto.getPrice().
                        multiply(discountPercentage)
                        .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

                BigDecimal payAbleAmount = dto.getPrice().subtract(discountAmount);

                productVariant.setDiscountAmount(discountAmount);
                productVariant.setDiscountPercentage(discountPercentage);
                productVariant.setPayableAmount(payAbleAmount);

                productVariant.setStatus(ProductVariantStatus.ACTIVE);

                productVariant = productVariantRepository.save(productVariant);

                // variant options


                Set<String> usedOptions = new HashSet<>();

                if (dto.getOptions() != null) {
                    for (Map.Entry<String, String> entry :
                            dto.getOptions().entrySet()) {
                        String optionName = entry.getKey();
                        String optionValue = entry.getValue();
                        String optionKey = optionName.toLowerCase();
                        if (!usedOptions.add(optionKey)) {
                            throw new RuntimeException("Duplicate option key " + optionKey);
                        }

                        ProductOption productOption = productOptionMap.get(optionKey);

                        if (productOption == null) {
                            throw new RuntimeException("Option not found " + optionKey);
                        }

                        Map<String, ProductOptionValue> values = productOptionValueMap.get(optionKey);

                        ProductOptionValue productOptionValue = values.get(optionValue.toLowerCase());

                        if (productOptionValue == null) {
                            throw new RuntimeException("Value : " + optionValue + " doest not belong to option " + optionName + " ");
                        }

                        ProductVariantOption productVariantOption = new ProductVariantOption();


                        productVariantOption.setId(productOption.getId());
                        productVariantOption.setVariant(productVariant);
                        productVariantOption.setOptionValue(productOptionValue);

                        productVariantOptionRepository.save(productVariantOption);


                    }
                }

                // Inventory

                InventoryRequestDTO inventoryRequestDTO = dto.getInventory();

                if (inventoryRequestDTO == null) {
                    throw new RuntimeException("Inventory Request cannot be null" + sku);
                }

                Inventory inventory = new Inventory();

                inventory.setVariant(productVariant);
                inventory.setQuantity(inventoryRequestDTO.getQuantity());
                inventory.setReservedQuantity(0);
                inventory.setReorderLevel(inventoryRequestDTO.getReorderLevel());

                inventoryRepository.save(inventory);

            }
        }

        if(images != null && !images.isEmpty()){
            int displayOrder=0;

            for(MultipartFile imageFile : images){
                if(imageFile.isEmpty()){
                    continue;
                }

                String imageUrl=null;

                try {
                    Map<?,?> uploadResult=cloudImageService.uploadImage(imageFile);
                    imageUrl=uploadResult.get("secure_url").toString();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


                ProductImage productImage = new ProductImage();
                productImage.setProduct(product);
                productImage.setImageUrl(imageUrl);
                productImage.setDisplayOrder(displayOrder);
                productImage.setIsPrimary(displayOrder==0);
                productImageRepository.save(productImage);
                displayOrder++;
            }
        }

        List<ProductAttribute> productAttributes = productAttributeRepository.findByProductId(product.getId());
        List<ProductOption>  productOptions = productOptionRepository.findByProductId(product.getId());



        return new APIResponse<>(
                true,
                "Product added successfully",
                Mapper.createProductResponseDTO(product,productAttributes,productOptions)
        );
    }

    @Override
    public APIResponse<ProductResponseDTO> getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Product not found with id: " + id)
                );

        List<ProductAttribute> productAttributes = productAttributeRepository.findByProductId(id);
        List<ProductOption> productOptions = productOptionRepository.findByProductId(id);



        ProductResponseDTO productResponse =
                Mapper.createProductResponseDTO(product,productAttributes,productOptions);

        return new APIResponse<>(
                true,
                "Product fetched successfully",
                productResponse
        );


    }

    @Override
    public APIResponse<List<ProductResponseDTO>> getAllProducts() {

        List<ProductResponseDTO> productList=productRepository.findAll()
               .stream()
               .map(this::mapProductTpResponse)
               .toList();

       if(productList.isEmpty()){
           return new APIResponse<>(
                   false,
                   "No product found",
                   null
           );
        }

       return  new APIResponse<>(
               true,
               "All Product listed",
               productList
       );

    }

    @Override
    public APIResponse<List<ProductResponseDTO>> getAllProductsBySellerId(String loggedInEmail) {


        Optional<User> userOptional= userRepository.findByEmail(loggedInEmail);
        if(userOptional.isEmpty()){
            return new APIResponse<>(
                    false,
                    "No user found",
                    null
            );
        }

        User user=userOptional.get();

        if(user.getRole()!= Role.SELLER){
            return new APIResponse<>(
                    false,
                    "You are not seller",
                    null
            );
        }

        Optional<Seller> seller=sellerRepository.findByUser(user);

        if(seller.isEmpty()){
            return new APIResponse<>(
                    false,
                    "No Seller found by with this email "+user.getEmail(),
                    null
            );
        }

        List<ProductResponseDTO> productResponseDTOList=productRepository.findBySellerId(seller.get().getId())
                .stream()
                .map(this::mapProductTpResponse)
                .toList();

        String sellerName=seller.get().getStoreName();

        if(productResponseDTOList.isEmpty()){
            return new APIResponse<>(
                    false,
                    "No products for this seller account "+sellerName,
                    null
            );
        }


        return new APIResponse<>(
                true,
                "All products for the seller "+sellerName,
                productResponseDTOList
        );
    }


    @Override
    public ProductResponseDTO updateProduct(Long id, ProductCreateRequestDTO request) {
        return null;
    }

    @Override
    public void deleteProduct(Long id) {

    }

    @Override
    public APIResponse<List<ProductResponseDTO>> getAllProductsBySubCategoryId(Long id) {

        Optional<SubCategory> subCategory=subCategoryRepository.findById(id);
        if(subCategory.isEmpty()){
            return new APIResponse<>(
                    false,
                    "No subcategories found",
                    null
            );
        }

        List<ProductResponseDTO> productResponseDTOList=productRepository.findBySubCategory_Id(id)
                .stream()
                .map(this::mapProductTpResponse)
                .toList();

        String subCategoryName=subCategory.get().getName();

        if(productResponseDTOList.isEmpty()){
            return new APIResponse<>(
                    false,
                    "No products related to subcategories "+subCategoryName,
                    null
            );
        }

        return new APIResponse<>(
                true,
                "All products related to subcategories "+subCategoryName,
                productResponseDTOList
        );

    }

    @Override
    public APIResponse<List<ProductResponseDTO>> getAllProductsByCategoryId(Long id) {


        Optional<Category> category=categoryRepository.findById(id);
        if(category.isEmpty()){
            return new APIResponse<>(
                    false,
                    "No category found with id " + id,
                    null
            );
        }

        List<ProductResponseDTO> productResponseDTOList=productRepository.findBySubCategory_Category_Id(id)
                .stream()
                .map(this::mapProductTpResponse)
                .toList();

        String categoryName=category.get().getName();

        if(productResponseDTOList.isEmpty()){
            return new APIResponse<>(
                    false,
                    "No products related to category "+categoryName,
                    null
            );
        }

        return new APIResponse<>(
                true,
                "All products related to category "+categoryName,
                productResponseDTOList
        );

    }

    @Override
    public APIResponse<List<ProductResponseDTO>> getAllProductsByBrandId(Long id) {
        Optional<Brand> brand=brandRepository.findById(id);
        if(brand.isEmpty()){
            return new APIResponse<>(
                    false,
                    "No brand found with id: " +id ,
                    null
            );
        }

        String brandName=brand.get().getName();

        List<ProductResponseDTO> productResponseDTOList=productRepository.findByBrand_Id(id)
                .stream()
                .map(this::mapProductTpResponse)
                .toList();

        if(productResponseDTOList.isEmpty()){
            return new APIResponse<>(
                    false,
                    "No products related to brand "+brandName,
                    null
            );
        }

        return new APIResponse<>(
                true,
                "All products related to brand "+brandName,
                productResponseDTOList
        );


    }

public  ProductResponseDTO mapProductTpResponse(Product product){

        List<ProductAttribute> attributes= productAttributeRepository.findByProductId(product.getId());
        List<ProductOption> productOptions = productOptionRepository.findByProductId(product.getId());

        return Mapper.createProductResponseDTO(
                product,
                attributes,
                productOptions
        );

}
}
