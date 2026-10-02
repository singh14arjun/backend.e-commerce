package ecommerce.backend.service.impl.category;

import ecommerce.backend.cloudinary.CloudImageService;
import ecommerce.backend.constant.CategoryStatus;
import ecommerce.backend.constant.Role;
import ecommerce.backend.exception.UserNotFoundException;
import ecommerce.backend.mapper.Mapper;
import ecommerce.backend.model.category.Category;
import ecommerce.backend.model.user.User;
import ecommerce.backend.payload.dtoRequest.category.CategoryRequestDTO;
import ecommerce.backend.payload.dtoRequest.category.CategoryUpdateRequest;
import ecommerce.backend.payload.dtoResponse.APIResponse;
import ecommerce.backend.payload.dtoResponse.category.CategoryResponseDTO;
import ecommerce.backend.repository.UserRepository;
import ecommerce.backend.repository.category.CategoryRepository;
import ecommerce.backend.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final CloudImageService cloudImageService;


    // =========================================================
    // ADD CATEGORY
    // =========================================================

    @Override
    @Transactional
    public APIResponse<CategoryResponseDTO> addCategory(
            CategoryRequestDTO categoryRequestDTO,
            String loggedInEmail,
            MultipartFile multipartFile) throws IOException {

        User loggedUser = getLoggedInUser(loggedInEmail);

        checkAdmin(loggedUser);

        // Check duplicate category
        if (categoryRepository.existsByNameIgnoreCase(
                categoryRequestDTO.getName())) {

            throw new RuntimeException(
                    "Category already exists: "
                            + categoryRequestDTO.getName()
            );
        }



        Category category = new Category();

        category.setName(categoryRequestDTO.getName());
        category.setDescription(categoryRequestDTO.getDescription());
        category.setStatus(CategoryStatus.ACTIVE);

        // Upload image
        if (multipartFile != null && !multipartFile.isEmpty()) {

            validateImage(multipartFile);

            Map<?, ?> uploadResult =
                    cloudImageService.uploadImage(multipartFile);

            Object secureUrl = uploadResult.get("secure_url");

            if (secureUrl == null) {
                throw new RuntimeException("Image upload failed");
            }

            category.setImage(secureUrl.toString());
        }

        Category savedCategory =
                categoryRepository.save(category);

        return new APIResponse<>(
                true,
                "Category added successfully",
                Mapper.createCategoryResponseDTO(savedCategory)
        );
    }


    // =========================================================
    // GET CATEGORY BY ID
    // =========================================================

    @Override
    public APIResponse<CategoryResponseDTO> getCategoryByCategoryId(
            Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Category not found with id: " + categoryId
                        )
                );

        return new APIResponse<>(
                true,
                "Category fetched successfully",
                Mapper.createCategoryResponseDTO(category)
        );
    }


    // =========================================================
    // GET ALL CATEGORIES
    // =========================================================

    @Override
    public APIResponse<List<CategoryResponseDTO>> getAllCategoryList() {

        List<CategoryResponseDTO> categories =
                categoryRepository.findAll()
                        .stream()
                        .map(Mapper::createCategoryResponseDTO)
                        .toList();

        if(categories.isEmpty())
        {
            return new APIResponse<>(
                    false,
                    "No categories found",
                    null
            );
        }

        return new APIResponse<>(
                true,
                "Categories fetched successfully",
                categories
        );
    }


    // =========================================================
    // UPDATE CATEGORY
    // =========================================================

    @Override
    @Transactional
    public APIResponse<CategoryResponseDTO> updateCategoryById(
            Long categoryId,
            CategoryUpdateRequest categoryUpdateRequest,
            String loggedInEmail) {

        User loggedUser = getLoggedInUser(loggedInEmail);

        checkAdmin(loggedUser);

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Category not found with id: " + categoryId
                        )
                );

        // Check duplicate name
        if (categoryRequestDTOHasName(
                categoryUpdateRequest)) {

            boolean exists =
                    categoryRepository.existsByNameIgnoreCase(
                            categoryUpdateRequest.getName()
                    );

            if (exists &&
                    !category.getName().equalsIgnoreCase(
                            categoryUpdateRequest.getName())) {

                throw new RuntimeException(
                        "Category already exists: "
                                + categoryUpdateRequest.getName()
                );
            }

            category.setName(
                    categoryUpdateRequest.getName()
            );
        }

        if (categoryUpdateRequest.getDescription() != null) {
            category.setDescription(
                    categoryUpdateRequest.getDescription()
            );
        }

        Category updatedCategory =
                categoryRepository.save(category);

        return new APIResponse<>(
                true,
                "Category updated successfully",
                Mapper.createCategoryResponseDTO(updatedCategory)
        );
    }


    // =========================================================
    // UPDATE CATEGORY IMAGE
    // =========================================================

    @Override
    @Transactional
    public APIResponse<CategoryResponseDTO> updateCategoryImage(
            Long categoryId,
            MultipartFile image,
            String loggedInEmail) throws IOException {

        User loggedUser = getLoggedInUser(loggedInEmail);

        checkAdmin(loggedUser);

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Category not found with id: " + categoryId
                        )
                );

        if (image == null || image.isEmpty()) {
            throw new RuntimeException(
                    "Category image is required"
            );
        }

        validateImage(image);

        Map<?, ?> uploadResult =
                cloudImageService.uploadImage(image);

        Object secureUrl = uploadResult.get("secure_url");

        if (secureUrl == null) {
            throw new RuntimeException(
                    "Image upload failed"
            );
        }

        category.setImage(secureUrl.toString());

        Category updatedCategory =
                categoryRepository.save(category);

        return new APIResponse<>(
                true,
                "Category image updated successfully",
                Mapper.createCategoryResponseDTO(updatedCategory)
        );
    }


    // =========================================================
    // DELETE CATEGORY
    // =========================================================

    @Override
    @Transactional
    public APIResponse<String> deleteCategoryById(
            Long categoryId,
            String loggedInEmail) {

        User loggedUser = getLoggedInUser(loggedInEmail);

        checkAdmin(loggedUser);

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Category not found with id: " + categoryId
                        )
                );

        categoryRepository.delete(category);

        return new APIResponse<>(
                true,
                "Category deleted successfully",
                null
        );
    }


    // =========================================================
    // GET LOGGED-IN USER
    // =========================================================

    private User getLoggedInUser(String loggedInEmail) {

        return userRepository.findByEmail(loggedInEmail)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with email: "
                                        + loggedInEmail
                        )
                );
    }


    // =========================================================
    // ADMIN CHECK
    // =========================================================

    private void checkAdmin(User user) {

        if (user.getRole() != Role.ADMIN) {
            throw new AccessDeniedException(
                    "Only admin can perform this action"
            );
        }
    }


    // =========================================================
    // IMAGE VALIDATION
    // =========================================================

    private void validateImage(MultipartFile image) {

        String contentType = image.getContentType();

        if (contentType == null ||
                !contentType.startsWith("image/")) {

            throw new RuntimeException(
                    "Only image files are allowed"
            );
        }
    }


    // =========================================================
    // UPDATE REQUEST NAME CHECK
    // =========================================================

    private boolean categoryRequestDTOHasName(
            CategoryUpdateRequest request) {

        return request.getName() != null &&
                !request.getName().trim().isEmpty();
    }
}