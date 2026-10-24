package ecommerce.backend.controller.category;

import ecommerce.backend.payload.dtoRequest.category.CategoryRequestDTO;
import ecommerce.backend.payload.dtoRequest.category.CategoryStatusUpdatedRequestDTO;
import ecommerce.backend.payload.dtoRequest.category.CategoryUpdateRequest;
import ecommerce.backend.payload.dtoResponse.APIResponse;
import ecommerce.backend.payload.dtoResponse.category.CategoryResponseDTO;
import ecommerce.backend.service.category.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/admin/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<APIResponse<CategoryResponseDTO>> createCategory(

            @Valid @ModelAttribute CategoryRequestDTO categoryRequestDTO,

            Authentication authentication,

            @RequestParam(
                    value = "image",
                    required = false
            ) MultipartFile image

    ) throws IOException {

        String loggedInEmail = authentication.getName();

        APIResponse<CategoryResponseDTO> response =
                categoryService.addCategory(
                        categoryRequestDTO,
                        loggedInEmail,
                        image
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PatchMapping("/{categoryId}/status")
    public ResponseEntity<APIResponse<CategoryResponseDTO>> updateCategoryStatusById(
           @PathVariable Long categoryId,
           @Valid @RequestBody CategoryStatusUpdatedRequestDTO status,
            Authentication authentication
    ){
        String loggedInEmail = authentication.getName();

        APIResponse<CategoryResponseDTO> response=categoryService.changeCategoryStatusById(categoryId,status,loggedInEmail);
        return ResponseEntity.ok(response);
    }

    @PutMapping("{categoryId}/update")
    public ResponseEntity<APIResponse<CategoryResponseDTO>> updateCategoryByCategoryId(
            @PathVariable  Long categoryId,
           @Valid @RequestBody CategoryUpdateRequest updateRequest,
            Authentication authentication
    )
    {
        String loggedInEmail = authentication.getName();
        APIResponse<CategoryResponseDTO> response=categoryService.updateCategoryById(categoryId,updateRequest,loggedInEmail);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{categoryId}/delete")
    public ResponseEntity<APIResponse<String>> deleteCategory(
            Authentication authentication,
            @PathVariable Long categoryId
    )
    {
        String loggedInEmail = authentication.getName();

        APIResponse<String> response =categoryService.deleteCategoryById(categoryId,loggedInEmail);



        return ResponseEntity.ok(response);
    }
}