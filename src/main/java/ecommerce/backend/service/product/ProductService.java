package ecommerce.backend.service.product;

import ecommerce.backend.model.product.Product;
import ecommerce.backend.payload.dtoRequest.product.ProductCreateRequestDTO;
import ecommerce.backend.payload.dtoResponse.APIResponse;
import ecommerce.backend.payload.dtoResponse.product.ProductResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    APIResponse<ProductResponseDTO> createNewProduct(
            ProductCreateRequestDTO productCreateRequestDTO,
            List<MultipartFile> images,
             String loggedInEmail);

    APIResponse<ProductResponseDTO> getProductById(Long id);
    APIResponse<List<ProductResponseDTO>> getAllProductsBySubCategoryId(Long id);
    APIResponse<List<ProductResponseDTO>> getAllProductsByCategoryId(Long id);
    APIResponse<List<ProductResponseDTO>> getAllProductsByBrandId(Long id);
    APIResponse<List<ProductResponseDTO>> getAllProducts();

    ProductResponseDTO updateProduct(
            Long id,
            ProductCreateRequestDTO request
    );

    void deleteProduct(Long id);



}
