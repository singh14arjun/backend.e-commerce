package ecommerce.backend.controller.category;

import ecommerce.backend.payload.dtoResponse.APIResponse;
import ecommerce.backend.payload.dtoResponse.category.CategoryResponseDTO;
import ecommerce.backend.payload.dtoResponse.category.SubCategoryResponseDTO;
import ecommerce.backend.service.category.CategoryService;
import ecommerce.backend.service.category.SubCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryPublicController {

    private final CategoryService categoryService;
    private  final SubCategoryService subCategoryService;

    @GetMapping
    public ResponseEntity<APIResponse<List<CategoryResponseDTO>>> getAllCategories() {

        return ResponseEntity.ok(
                categoryService.getAllCategoryList()
        );
    }

    @GetMapping("/active")
    public ResponseEntity<APIResponse<List<CategoryResponseDTO>>> getAllActiveCategories() {

        return ResponseEntity.ok(
                categoryService.getAllActiveCategoryList()
        );
    }


    @GetMapping("/{categoryId}")
    public ResponseEntity<APIResponse<CategoryResponseDTO>> getCategoryById(
            @PathVariable Long categoryId
    ) {


        return ResponseEntity.ok(
                categoryService.getCategoryByCategoryId(categoryId)
        );
    }

}
