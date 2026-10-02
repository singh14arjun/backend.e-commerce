package ecommerce.backend.service.impl.category;

import ecommerce.backend.cloudinary.CloudImageService;
import ecommerce.backend.constant.CategoryStatus;
import ecommerce.backend.constant.Role;
import ecommerce.backend.exception.UserNotFoundException;
import ecommerce.backend.mapper.Mapper;
import ecommerce.backend.model.category.Category;
import ecommerce.backend.model.category.SubCategory;
import ecommerce.backend.model.user.User;
import ecommerce.backend.payload.dtoRequest.category.SubCategoryRequestDTO;
import ecommerce.backend.payload.dtoResponse.APIResponse;
import ecommerce.backend.payload.dtoResponse.category.SubCategoryResponseDTO;
import ecommerce.backend.repository.UserRepository;
import ecommerce.backend.repository.category.CategoryRepository;
import ecommerce.backend.repository.category.SubCategoryRepository;
import ecommerce.backend.service.category.SubCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubCategoryServiceImpl implements SubCategoryService {

    private final SubCategoryRepository subCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final CloudImageService  cloudImageService;

    @Override
    public APIResponse<SubCategoryResponseDTO> addSubCategory(SubCategoryRequestDTO subCategoryRequestDTO, String loggedEmail, MultipartFile image) throws IOException {


        User loggedUser= getLoggedInUser(loggedEmail);

        checkAdmin(loggedUser);

        Category category=categoryRepository.findById(subCategoryRequestDTO.getCategoryId()).orElseThrow(
                () -> new RuntimeException("Category Not Found with id : "+subCategoryRequestDTO.getCategoryId())
        );

        if(category.getStatus() != CategoryStatus.ACTIVE){
            throw  new RuntimeException("Cannot Add Sub Category under in active category");
        }

        if(subCategoryRepository.existsByCategoryIdAndNameIgnoreCase(category.getId(),subCategoryRequestDTO.getName())){
            throw  new RuntimeException("Sub Category already exists in this category "+subCategoryRequestDTO.getName());
        }

        SubCategory subCategory=new SubCategory();
        subCategory.setName(subCategoryRequestDTO.getName());
        subCategory.setDescription(subCategoryRequestDTO.getDescription());
        subCategory.setCategory(category);
        subCategory.setStatus(true);

        if(image !=null && !image.isEmpty()){
            String contentType=image.getContentType();
            if(contentType == null || !contentType.startsWith("image/")){
                throw new RuntimeException("Only image files are allowed");
            }

            var uploadResult=cloudImageService.uploadImage(image);

            Object secureUrl=uploadResult.get("secure_url");

            if(secureUrl==null){
                throw new  RuntimeException("Image upload failed");
            }

            subCategory.setImage(secureUrl.toString());
        }

        SubCategory savedSubCategory=subCategoryRepository.save(subCategory);



        return new APIResponse<>(
                true,
                "Subcategory added Successfully to " + category.getName(),
                Mapper.createSubCategoryResponseDTO(savedSubCategory)
        );
    }



    @Override
    public APIResponse<SubCategoryResponseDTO> getSubCategoryById(Long subCategoryId) {

        SubCategory subCategory =
                subCategoryRepository
                        .findByIdAndStatusTrue(subCategoryId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Subcategory not found with id: "
                                                + subCategoryId
                                )
                        );

        return  new APIResponse<>(
                true,
                "Subcategory fetched successfully",
                Mapper.createSubCategoryResponseDTO(subCategory)
        );

    }

    @Override
    public APIResponse<List<SubCategoryResponseDTO>> getSubCategoriesByCategoryId(Long categoryId) {

        Category category=categoryRepository.findById(categoryId).orElseThrow(()->new RuntimeException("Category Not Found with id : "+categoryId));

        List<SubCategory> subCategories=subCategoryRepository.findByCategoryIdAndStatusTrue(categoryId);

        List<SubCategoryResponseDTO> subCategoryResponseDTOS=subCategories
                .stream()
                .map(Mapper::createSubCategoryResponseDTO)
                .toList();

        return new APIResponse<>(
                true,
                "SubCategories list with category "+category.getName()+" fetched successfully",
                subCategoryResponseDTOS
        );
    }

    @Override
    public APIResponse<List<SubCategoryResponseDTO>> getAllSubCategories() {

        List<SubCategoryResponseDTO> subCategories=subCategoryRepository.findAll()
                .stream()
                .map(Mapper::createSubCategoryResponseDTO)
                .toList();

        if(subCategories.isEmpty()){
            return  new APIResponse<>(
                    false,
                    "No subcategories found",
                    null
            );
        }
        return new APIResponse<>(
                true,
                "All subcategories",
                subCategories
        );
    }

    @Override
    public APIResponse<SubCategoryResponseDTO> updateSubCategory(Long subCategoryId, SubCategoryRequestDTO subCategoryRequestDTO, String loggedEmail) {
        return null;
    }


    private User getLoggedInUser(String loggedEmail)
    {
        return
                userRepository.findByEmail(loggedEmail)
                        .orElseThrow(()->
                                new UserNotFoundException(
                                        "User not found with email "
                                                + loggedEmail));
    }

    private  void checkAdmin(User user){
        if(user.getRole() != Role.ADMIN){
            throw new AccessDeniedException("You are not allowed to perform this action.");
        }
    }
}