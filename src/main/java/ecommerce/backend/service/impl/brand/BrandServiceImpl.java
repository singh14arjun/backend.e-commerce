package ecommerce.backend.service.impl.brand;

import ecommerce.backend.cloudinary.CloudImageService;
import ecommerce.backend.constant.BrandStatus;
import ecommerce.backend.constant.Role;
import ecommerce.backend.constant.SubCategoryStatus;
import ecommerce.backend.exception.UserNotFoundException;
import ecommerce.backend.mapper.Mapper;
import ecommerce.backend.model.brand.Brand;
import ecommerce.backend.model.category.SubCategory;
import ecommerce.backend.payload.dtoRequest.brand.BrandRequestDTO;
import ecommerce.backend.payload.dtoResponse.APIResponse;
import ecommerce.backend.payload.dtoResponse.brand.BrandResponseDTO;
import ecommerce.backend.repository.BrandRepository;
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

@Service
@RequiredArgsConstructor

public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final SubCategoryRepository subCategoryRepository;
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

        SubCategory subCategory=subCategoryRepository.findById(brandRequestDTO.getSubCategoryId()).orElseThrow(
                ()-> new RuntimeException("SubCategory not found with id: " + brandRequestDTO.getSubCategoryId())
        );

        if(subCategory.getStatus() != SubCategoryStatus.ACTIVE){
            return new APIResponse<>(
                    false,
                    "Cannot add brand , Sab Category is not active",
                    null
            );

        }

        Brand brand = new Brand();
        brand.setName(brandRequestDTO.getName());
        brand.setSlug(brandRequestDTO.getSlug());
        brand.setDescription(brandRequestDTO.getDescription());
        brand.setStatus(BrandStatus.ACTIVE);

        brand.setSubCategory(subCategory);

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
                Mapper.createBrandResponseDTO(savedBrand)
        );
    }
}
