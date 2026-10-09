package ecommerce.backend.controller.product;

import ecommerce.backend.payload.dtoResponse.APIResponse;
import ecommerce.backend.payload.dtoResponse.product.ProductResponseDTO;
import ecommerce.backend.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products/")
public class ProductPublicController {

    private final ProductService productService;

    @GetMapping()
    public ResponseEntity<APIResponse<List<ProductResponseDTO>>> getAllProducts(){
        APIResponse<List<ProductResponseDTO>> response =
                productService.getAllProducts();

        return ResponseEntity.ok(response);
    }

    @GetMapping("{productId}")
    public ResponseEntity<APIResponse<ProductResponseDTO>> getProductById(
            @PathVariable Long productId){
        APIResponse<ProductResponseDTO> response =
                productService.getProductById(productId);

        return ResponseEntity.ok(response);
    }



    @GetMapping("subCategory/{subCategoryId}")
    public ResponseEntity<APIResponse<List<ProductResponseDTO>>> getAllProductsBySubCategoryId(
            @PathVariable Long subCategoryId){
        APIResponse<List<ProductResponseDTO>> response =
                productService.getAllProductsBySubCategoryId(subCategoryId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("category/{categoryId}")
    public ResponseEntity<APIResponse<List<ProductResponseDTO>>> getAllProductsByCategoryId(
            @PathVariable Long categoryId){
        APIResponse<List<ProductResponseDTO>> response =
                productService.getAllProductsByCategoryId(categoryId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("brand/{brandId}")
    public ResponseEntity<APIResponse<List<ProductResponseDTO>>> getAllProductsByBrandId(
            @PathVariable Long brandId){
        APIResponse<List<ProductResponseDTO>> response =
                productService.getAllProductsByBrandId(brandId);

        return ResponseEntity.ok(response);
    }
}
