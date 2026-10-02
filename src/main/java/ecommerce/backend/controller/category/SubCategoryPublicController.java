package ecommerce.backend.controller.category;

import ecommerce.backend.model.category.SubCategory;
import ecommerce.backend.payload.dtoResponse.APIResponse;
import ecommerce.backend.payload.dtoResponse.category.SubCategoryResponseDTO;
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
@RequiredArgsConstructor
@RequestMapping("/api/subcategories")
public class SubCategoryPublicController {

    private  final SubCategoryService subCategoryService;

    @GetMapping()
    public ResponseEntity<APIResponse<List<SubCategoryResponseDTO>>>  getAllSubCategories() throws  Exception{

        return ResponseEntity.status(HttpStatus.OK).body(
                subCategoryService.getAllSubCategories()

        );
    }

    @GetMapping("/{subCategoryId}")
    public ResponseEntity<APIResponse<SubCategoryResponseDTO>> getSubCategoryById(
            @PathVariable Long subCategoryId
    )
    {
        return ResponseEntity.status(HttpStatus.OK).body(subCategoryService.getSubCategoryById(subCategoryId));
    }
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<APIResponse<List<SubCategoryResponseDTO>>> getAllSubCategoriesByCategoryId(
            @PathVariable Long categoryId
    )
    {
        return ResponseEntity.status(HttpStatus.OK).body(subCategoryService.getSubCategoriesByCategoryId(categoryId));
    }
}
