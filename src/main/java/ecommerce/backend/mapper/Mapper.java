package ecommerce.backend.mapper;

import ecommerce.backend.constant.CategoryStatus;
import ecommerce.backend.model.brand.Brand;
import ecommerce.backend.model.category.Category;
import ecommerce.backend.model.category.SubCategory;
import ecommerce.backend.model.product.Product;
import ecommerce.backend.model.product.ProductAttribute;
import ecommerce.backend.model.product.ProductOption;
import ecommerce.backend.model.product.ProductOptionValue;
import ecommerce.backend.model.seller.Seller;
import ecommerce.backend.model.seller.SellerAddress;
import ecommerce.backend.model.seller.SellerApplication;
import ecommerce.backend.model.user.User;
import ecommerce.backend.payload.dtoResponse.IdNameResponseDTO;
import ecommerce.backend.payload.dtoResponse.UserResponseDTO;
import ecommerce.backend.payload.dtoResponse.brand.BrandResponseDTO;
import ecommerce.backend.payload.dtoResponse.category.CategoryResponseDTO;
import ecommerce.backend.payload.dtoResponse.category.SubCategoryResponseDTO;
import ecommerce.backend.payload.dtoResponse.product.*;
import ecommerce.backend.payload.dtoResponse.seller.SellerAddressResponseDTO;
import ecommerce.backend.payload.dtoResponse.seller.SellerApplicationResponseDTO;
import ecommerce.backend.payload.dtoResponse.seller.SellerResponseDTO;

import java.util.ArrayList;
import java.util.List;

public class Mapper {
    public static UserResponseDTO mapToResponseDTO(User user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setUsername(user.getUsername());
        userResponseDTO.setFirstName(user.getFirstName());
        userResponseDTO.setLastName(user.getLastName());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setProfilePicture(user.getProfilePicture());
//        userResponseDTO.setPassword(user.getPassword());
        userResponseDTO.setRole(user.getRole());
        userResponseDTO.setPhoneNumber(user.getPhoneNumber());
        userResponseDTO.setAccountStatus(user.getAccountStatus());
        return userResponseDTO;
    }

    public static SellerApplicationResponseDTO createSellerApplicationResponseDTO(SellerApplication sellerApplication){

        SellerApplicationResponseDTO sellerApplicationResponseDTO=new SellerApplicationResponseDTO();

        sellerApplicationResponseDTO.setId(sellerApplication.getId());
        sellerApplicationResponseDTO.setUserId(sellerApplication.getUser().getId());
        sellerApplicationResponseDTO.setStoreName(sellerApplication.getStoreName());
        sellerApplicationResponseDTO.setStoreEmail(sellerApplication.getStoreEmail());

        sellerApplicationResponseDTO.setStorePhoneNumber(sellerApplication.getStorePhoneNumber());
        sellerApplicationResponseDTO.setGstNumber(sellerApplication.getGstNumber());
        sellerApplicationResponseDTO.setPanNumber(sellerApplication.getPanNumber());
        sellerApplicationResponseDTO.setStatus(sellerApplication.getStatus());
        sellerApplicationResponseDTO.setCreatedAt(sellerApplication.getCreatedAt());
        sellerApplicationResponseDTO.setUpdatedAt(sellerApplication.getUpdatedAt());

        return sellerApplicationResponseDTO;


    }

    public static SellerAddressResponseDTO createSellerAddressResponseDTO(
            SellerAddress address
    ) {

        SellerAddressResponseDTO dto =
                new SellerAddressResponseDTO();

        dto.setId(address.getId());
        dto.setSellerId(address.getSeller().getId());
        dto.setAddressLine1(address.getAddressLine1());
        dto.setAddressLine2(address.getAddressLine2());
        dto.setLandmark(address.getLandmark());
        dto.setCity(address.getCity());
        dto.setState(address.getState());
        dto.setCountry(address.getCountry());
        dto.setPostalCode(address.getPostalCode());
        dto.setAddressType(address.getAddressType());
        dto.setIsDefault(address.getIsDefault());
        dto.setCreatedAt(address.getCreatedAt());
        dto.setUpdatedAt(address.getUpdatedAt());

        return dto;
    }

    public static SellerResponseDTO createSellerResponseDTO(Seller seller) {

        SellerResponseDTO dto = new SellerResponseDTO();

        dto.setId(seller.getId());
        dto.setUserId(seller.getUser().getId());
        dto.setStoreName(seller.getStoreName());
        dto.setStoreEmail(seller.getStoreEmail());
        dto.setStorePhoneNumber(seller.getStorePhoneNumber());
        dto.setGstNumber(seller.getGstNumber());
        dto.setPanNumber(seller.getPanNumber());
        dto.setIsActive(seller.getIsActive());
        dto.setCreatedAt(seller.getCreatedAt());
        dto.setUpdatedAt(seller.getUpdatedAt());

        return dto;
    }

    public static CategoryResponseDTO createCategoryResponseDTO(Category category) {
        CategoryResponseDTO response =
                new CategoryResponseDTO();

        response.setId(category.getId());
        response.setName(category.getName());
        response.setDescription(category.getDescription());
        response.setImage(category.getImage());
        response.setStatus(category.getStatus());
        response.setSubCategoryCount(category.getSubCategoryCount());
        response.setCreatedAt(category.getCreatedAt());
        response.setUpdatedAt(category.getUpdatedAt());

        return response;
    }

    public static SubCategoryResponseDTO createSubCategoryResponseDTO(SubCategory subCategory) {
        SubCategoryResponseDTO response =
                new SubCategoryResponseDTO();

        response.setId(subCategory.getId());
        response.setName(subCategory.getName());
        response.setDescription(subCategory.getDescription());
        response.setImage(subCategory.getImage());
        response.setStatus(subCategory.getStatus());
        response.setCategoryName(subCategory.getCategory().getName());
        response.setCategoryId(subCategory.getCategory().getId());

        return  response;
    }

    public static BrandResponseDTO createBrandResponseDTO(Brand brand) {
        BrandResponseDTO response =
                new BrandResponseDTO();
        response.setId(brand.getId());
        response.setName(brand.getName());
        response.setSlug(brand.getSlug());
        response.setDescription(brand.getDescription());
        response.setSubCategoryId(brand.getSubCategory().getId());
        response.setSubCategoryName(brand.getSubCategory().getName());
        response.setStatus(brand.getStatus());
        response.setLogoUrl(brand.getLogoUrl());
        response.setCreatedAt(brand.getCreatedAt());
        response.setUpdatedAt(brand.getUpdatedAt());

        return response;
    }

    public static ProductResponseDTO createProductResponseDTO(Product product,List<ProductAttribute> productAttributes,List<ProductOption> productOptions) {
        ProductResponseDTO response =
                new ProductResponseDTO();

        response.setId(product.getId());

        // seller
        response.setSeller(
                new IdNameResponseDTO(
                        product.getSeller().getId()
                        ,product.getSeller().getStoreName()));

        // brand

        response.setBrand(
                new IdNameResponseDTO(
                        product.getBrand().getId(),
                        product.getBrand().getName()
                )
        );

        // subCategory

        response.setSubCategory(
                new IdNameResponseDTO(
                        product.getSubCategory().getId(),
                        product.getSubCategory().getName()
                )
        );

        Category category = product.getSubCategory().getCategory();

        response.setCategory(
                new IdNameResponseDTO(
                        category.getId(),
                        category.getName()

                )
        );

        response.setProductTitle(product.getProductTitle());
        response.setSlug(product.getSlug());
        response.setDescription(product.getDescription());
        response.setShortDescription(product.getShortDescription());
        response.setStatus(product.getStatus());
        response.setFeatured(product.isFeatured());
        response.setCreatedAt(product.getCreatedAt());
        response.setUpdatedAt(product.getUpdatedAt());
        response.setIsWishlisted(product.getIsWishlisted());

        response.setAttributes(
                createProductAttributeResponseDTO(productAttributes)
        );

        response.setOptions(
                createProductOptionResponseDTO(productOptions)
        );

        response.setVariants(
                mapVariants(product.getId())
        );

        response.setImages(
                mapImages(product.getId())
        );
        return response;
    }

    private static List<ProductImageResponseDTO> mapImages(Long id) {
        return null;
    }

    private static List<ProductVariantResponseDTO> mapVariants(Long id) {
        return null;
    }

    private static List<ProductOptionResponseDTO> createProductOptionResponseDTO(List<ProductOption> productOptions) {

        if(productOptions == null || productOptions.isEmpty()) {
            return new ArrayList<>();
        }
        List<ProductOptionResponseDTO> options = new ArrayList<>();
        for(ProductOption productOption : productOptions) {
            ProductOptionResponseDTO productOptionResponseDTO = new ProductOptionResponseDTO();
            productOptionResponseDTO.setId(productOption.getId());
            productOptionResponseDTO.setName(productOption.getName());
            productOptionResponseDTO.setValues(createProductOptionValueResponseDTO(productOption.getValues()));
            options.add(productOptionResponseDTO);
        }
        return options;
    }

    private static List<ProductAttributeResponseDTO> createProductAttributeResponseDTO(List<ProductAttribute> productAttributes) {

        if(productAttributes == null || productAttributes.isEmpty()) {
            return new ArrayList<>();
        }

        List<ProductAttributeResponseDTO> attributes = new ArrayList<>();
        for(ProductAttribute productAttribute : productAttributes) {
            ProductAttributeResponseDTO productAttributeResponseDTO =
                    new ProductAttributeResponseDTO();
            productAttributeResponseDTO.setId(productAttribute.getId());
            productAttributeResponseDTO.setName(productAttribute.getAttributeName());
            productAttributeResponseDTO.setValue(productAttribute.getAttributeValue());
         attributes.add(productAttributeResponseDTO);
        }


        return attributes;
    }

    private static List<ProductOptionValueResponseDTO> createProductOptionValueResponseDTO(
            List<ProductOptionValue> productOptionValues) {

        if (productOptionValues == null || productOptionValues.isEmpty()) {
            return new ArrayList<>();
        }

        List<ProductOptionValueResponseDTO> values = new ArrayList<>();

        for (ProductOptionValue productOptionValue : productOptionValues) {

            ProductOptionValueResponseDTO responseDTO =
                    new ProductOptionValueResponseDTO();

            responseDTO.setId(productOptionValue.getId());
            responseDTO.setValue(productOptionValue.getValue());

            values.add(responseDTO);
        }

        return values;
    }
}
