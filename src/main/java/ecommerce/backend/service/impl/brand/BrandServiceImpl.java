package ecommerce.backend.service.impl.brand;

import ecommerce.backend.cloudinary.CloudImageService;
import ecommerce.backend.constant.BrandStatus;
import ecommerce.backend.constant.Role;
import ecommerce.backend.constant.SubCategoryStatus;
import ecommerce.backend.exception.UserNotFoundException;
import ecommerce.backend.mapper.Mapper;
import ecommerce.backend.model.brand.Brand;
import ecommerce.backend.model.category.SubCategory;
import ecommerce.backend.model.seller.Seller;
import ecommerce.backend.payload.dtoRequest.brand.BrandRequestDTO;
import ecommerce.backend.payload.dtoRequest.brand.BrandStatusUpdatedRequestDTO;
import ecommerce.backend.payload.dtoRequest.brand.BrandUpdateRequestDTO;
import ecommerce.backend.payload.dtoResponse.APIResponse;
import ecommerce.backend.payload.dtoResponse.brand.BrandResponseDTO;
import ecommerce.backend.repository.BrandRepository;
import ecommerce.backend.repository.SellerRepository;
import ecommerce.backend.repository.UserRepository;
import ecommerce.backend.repository.category.SubCategoryRepository;
import ecommerce.backend.service.brand.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ecommerce.backend.model.user.User;


import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final SellerRepository sellerRepository;
    private final UserRepository userRepository;
    private final CloudImageService cloudImageService;

    @Override
    @Transactional
    public APIResponse<BrandResponseDTO> addNewBrand(BrandRequestDTO brandRequestDTO, String loggedInEmail, MultipartFile image) throws IOException {

        User user = userRepository.findByEmail(loggedInEmail).orElseThrow(
                ()-> new UserNotFoundException("User not found with email: " + loggedInEmail)
        );

        if(user.getRole() == Role.CUSTOMER) {
            throw new AccessDeniedException("Only Admin or Seller can add new brand");
        }

//        SubCategory subCategory=subCategoryRepository.findById(brandRequestDTO.getSubCategoryId()).orElseThrow(
//                ()-> new RuntimeException("SubCategory not found with id: " + brandRequestDTO.getSubCategoryId())
//        );

//        if(subCategory.getStatus() != SubCategoryStatus.ACTIVE){
//            return new APIResponse<>(
//                    false,
//                    "Cannot add brand , Sab Category is not active",
//                    null
//            );
//
//        }

        Brand brand = new Brand();
        brand.setName(brandRequestDTO.getName());
        brand.setSlug(brandRequestDTO.getSlug());
        brand.setDescription(brandRequestDTO.getDescription());
        brand.setStatus(BrandStatus.ACTIVE);
        brand.setCreatedBy(user);

//        brand.setSubCategory(subCategory);

        if(image != null && !image.isEmpty()){
            String contentType=image.getContentType();
            if(contentType == null || !contentType.startsWith("image/")){
                throw new AccessDeniedException("Only images are supported");
            }

            var uploadResult =cloudImageService.uploadImage(image);
            Object secureUrl= uploadResult.get("secure_url");

            if(secureUrl==null){
                return new APIResponse<>(
                        false,
                        "Image failed to upload",
                        null
                );
            }


            brand.setLogoUrl(secureUrl.toString());
        }

        Brand savedBrand = brandRepository.save(brand);
        return new APIResponse<>(
                true,
                "New Brand added successfully",
                Mapper.createBrandResponseDTO(savedBrand,getCreatorBrandDisplayName(user))
        );
    }

    @Override
    public APIResponse<List<BrandResponseDTO>> getAllBrands() {

        List<BrandResponseDTO> brandResponseDTOS=brandRepository.findAll()
                .stream()
                .map(brand -> Mapper.createBrandResponseDTO(brand,getCreatorBrandDisplayName(brand.getCreatedBy())))
                .toList();
          if (brandResponseDTOS.isEmpty()) {
           return new APIResponse<>(
                false,
                "No brand found",
                null
           );
        }


        return new APIResponse<>(
                true,
                "All brand list",
                brandResponseDTOS
        );
    }

    @Override
    public APIResponse<List<BrandResponseDTO>> getAllActiveBrands() {

        List<BrandResponseDTO> brandResponseDTOS=brandRepository.findAllByStatus(BrandStatus.ACTIVE)
                .stream()
                .map(brand -> Mapper.createBrandResponseDTO(brand,getCreatorBrandDisplayName(brand.getCreatedBy())))
                .toList();
        if (brandResponseDTOS.isEmpty()) {
            return new APIResponse<>(
                    false,
                    "No brand found",
                    null
            );
        }


        return new APIResponse<>(
                true,
                "All brand list",
                brandResponseDTOS
        );
    }

    @Override
    public APIResponse<BrandResponseDTO> getBrandById(Long brandId) {

        Optional<Brand> brand=brandRepository.findById(brandId);
        return brand.map(value -> new APIResponse<>(
                true,
                "Brand is here",
                Mapper.createBrandResponseDTO(value,getCreatorBrandDisplayName(value.getCreatedBy()))
        )).orElseGet(() -> new APIResponse<>(
                false,
                "No brand found",
                null
        ));
    }

    @Override
    @Transactional
    public APIResponse<BrandResponseDTO> updateBrandStatusByBrandId(Long brandId, BrandStatusUpdatedRequestDTO status,String loggedInEmail) {


        User user = getLoggedInUser(loggedInEmail);
        Brand brand= brandRepository.findById(brandId).orElse(null);;
        if(brand== null){
            return new APIResponse<>(
                    false,
                    "Brand not found with id: " + brandId,
                    null
            );
        }

        if(status == null || status.getStatus()==null){
            return new APIResponse<>(
                    false,
                    "Brand status is required",
                    null
            );
        }
        User createdBy=brand.getCreatedBy();

        if(createdBy==null){
            return new APIResponse<>(
                    false,

                    "brand create not found",
                    null
            );
        }


        if(createdBy.getRole() != user.getRole()){
            return new APIResponse<>(
                    false,
                    "You can only update brands created by you",
                    null
            );
        }

        if(!createdBy.getId().equals(user.getId())){
            return new APIResponse<>(
                    false,
                    "You can only update brands created by you",
                    null
            );        }
        brand.setStatus(status.getStatus());

        brandRepository.save(brand);

        return new APIResponse<>(
                true,
                "Brand status updated",
                Mapper.createBrandResponseDTO(brand,getCreatorBrandDisplayName(createdBy))
        );
    }

    @Override
    @Transactional
    public APIResponse<BrandResponseDTO> updateBrandByBrandId(Long brandId,
                                                              BrandUpdateRequestDTO brandUpdateRequestDTO,
                                                              String loggedInEmail) {
        User user = getLoggedInUser(loggedInEmail);
        Brand brand = brandRepository.findById(brandId).orElse(null);
        ;
        if (brand == null) {
            return new APIResponse<>(
                    false,
                    "Brand not found with id: " + brandId,
                    null
            );
        }

        User createdBy = brand.getCreatedBy();

        if (createdBy == null) {
            return new APIResponse<>(
                    false,

                    "brand create not found",
                    null
            );
        }


        if (createdBy.getRole() != user.getRole()) {
            return new APIResponse<>(
                    false,
                    "You can only update brands created by you",
                    null
            );
        }

        if (!createdBy.getId().equals(user.getId())) {
            return new APIResponse<>(
                    false,
                    "You can only update brands created by you",
                    null
            );
        }

//        SubCategory subCategory = brand.getSubCategory();

        if (brandUpdateRequestDTO.getName() != null && !brandUpdateRequestDTO.getName().isBlank()) {
            brand.setName(brandUpdateRequestDTO.getName());

        }
        if (brandUpdateRequestDTO.getDescription() != null && !brandUpdateRequestDTO.getDescription().isBlank()) {
            brand.setDescription(brandUpdateRequestDTO.getDescription());

        }
        if (brandUpdateRequestDTO.getSlug() != null && !brandUpdateRequestDTO.getSlug().isBlank()) {
            brand.setSlug(brandUpdateRequestDTO.getSlug());

        }
//        if (brandUpdateRequestDTO.getSubCategoryId() != null) {
//            brand.setSubCategory(subCategory);
//
//        }


        brandRepository.save(brand);

        return new APIResponse<>(
                true,
                "Brand status updated",
                Mapper.createBrandResponseDTO(brand, getCreatorBrandDisplayName(createdBy))
        );

    }

    @Override
    public APIResponse<String> deleteBrandById(Long brandId,String loggedInEmail) {


        Brand brand=brandRepository.findById(brandId).orElse(null);
        if(brand==null){
            return new APIResponse<>(
                    false,
                    "Brand not found",
                    null
            );
        }

        User createdBy=brand.getCreatedBy();

        User user = getLoggedInUser(loggedInEmail);

        if (createdBy.getRole() != user.getRole()) {
            return new APIResponse<>(
                    false,
                    "You can only delete brands created by you",
                    null
            );
        }

        if (!createdBy.getId().equals(user.getId())) {
            return new APIResponse<>(
                    false,
                    "You cannot delete this brand ",
                    null
            );
        }

        brandRepository.deleteById(brandId);

        return new APIResponse<>(
                true,
                "Delete successfully"
                ,"Brand deleted "+brand.getName()
        );
    }

    private  String getCreatorBrandDisplayName(User user){
        if(user.getRole() == Role.SELLER){
            Seller seller=sellerRepository.findByUser(user).orElseThrow(()->new UserNotFoundException("User not found with email: " ));

            return seller.getStoreName();
        }

        return user.getFirstName()+" "+user.getLastName();
    }

    private  User getLoggedInUser(String email){
        return  userRepository.findByEmail(email)
                .orElseThrow(()->
                        new UserNotFoundException(
                                "User not found with email"+email
                        ));
    }
}
