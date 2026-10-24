package ecommerce.backend.service.category;

import com.cloudinary.api.ApiResponse;
import ecommerce.backend.constant.SubCategoryStatus;
import ecommerce.backend.payload.dtoRequest.category.SubCategoryRequestDTO;
import ecommerce.backend.payload.dtoRequest.category.SubCategoryStatusUpdateRequestDTO;
import ecommerce.backend.payload.dtoRequest.category.SubCategoryUpdateRequestDTO;
import ecommerce.backend.payload.dtoResponse.APIResponse;
import ecommerce.backend.payload.dtoResponse.category.SubCategoryResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface SubCategoryService {

    APIResponse<SubCategoryResponseDTO> addSubCategory(
            SubCategoryRequestDTO subCategoryRequestDTO,
            String loggedEmail,
            MultipartFile image)
            throws IOException;

    APIResponse<SubCategoryResponseDTO> getSubCategoryById(Long subCategoryId);

    APIResponse<List<SubCategoryResponseDTO>> getSubCategoriesByCategoryId(Long categoryId);
    APIResponse<List<SubCategoryResponseDTO>> getAllSubCategories();
    APIResponse<List<SubCategoryResponseDTO>> getAllActiveSubCategories() ;

    APIResponse<SubCategoryResponseDTO> updateSubCategory(
            Long subCategoryId,
            SubCategoryUpdateRequestDTO subCategoryRequestDTO,
            String loggedEmail
    );
    APIResponse<SubCategoryResponseDTO> updateSubCategoryStatus(
            Long subCategoryId,
            SubCategoryStatusUpdateRequestDTO subCategoryStatusUpdateRequestDTO,
            String loggedEmail);

        APIResponse<String> deleteSubCategoryById(
            Long subCategoryId,
            String loggedEmail);
}

