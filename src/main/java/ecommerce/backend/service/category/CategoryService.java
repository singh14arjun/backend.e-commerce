package ecommerce.backend.service.category;

import ecommerce.backend.constant.CategoryStatus;
import ecommerce.backend.payload.dtoRequest.category.CategoryRequestDTO;
import ecommerce.backend.payload.dtoRequest.category.CategoryStatusUpdatedRequestDTO;
import ecommerce.backend.payload.dtoRequest.category.CategoryUpdateRequest;
import ecommerce.backend.payload.dtoResponse.APIResponse;
import ecommerce.backend.payload.dtoResponse.category.CategoryResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CategoryService {

    APIResponse<CategoryResponseDTO> addCategory(
            CategoryRequestDTO categoryRequest,
            String loggedInEmail,
            MultipartFile image
    ) throws IOException;


    APIResponse<CategoryResponseDTO> getCategoryByCategoryId(
            Long categoryId);


    APIResponse<List<CategoryResponseDTO>> getAllCategoryList(

    );
    APIResponse<List<CategoryResponseDTO>> getAllActiveCategoryList();


    APIResponse<CategoryResponseDTO> updateCategoryById(
            Long categoryId,
            CategoryUpdateRequest categoryUpdateRequest,
            String loggedInEmail
    );

    APIResponse<CategoryResponseDTO> changeCategoryStatusById(
            Long categoryId,
            CategoryStatusUpdatedRequestDTO categoryStatus,
            String loggedInEmail);

    APIResponse<CategoryResponseDTO> updateCategoryImage(
            Long categoryId,
            MultipartFile image,
            String loggedInEmail
    ) throws IOException;


    APIResponse<String> deleteCategoryById(
            Long categoryId,
            String loggedInEmail
    );
}