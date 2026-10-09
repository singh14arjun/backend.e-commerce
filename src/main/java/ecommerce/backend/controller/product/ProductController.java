package ecommerce.backend.controller.product;

import ecommerce.backend.payload.dtoRequest.product.ProductCreateRequestDTO;
import ecommerce.backend.payload.dtoResponse.APIResponse;
import ecommerce.backend.payload.dtoResponse.product.ProductResponseDTO;
import ecommerce.backend.service.product.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/seller/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<APIResponse<ProductResponseDTO>> createProduct(

            @RequestPart("products")
            String productJson,
            Authentication authentication,
            @RequestParam(
                    value = "images",
                    required = false
            ) List<MultipartFile> images
    ) throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        ProductCreateRequestDTO productCreateRequestDTO =
                mapper.readValue(
                        productJson,
                        ProductCreateRequestDTO.class);


        String loggedInEmail = authentication.getName();

        APIResponse<ProductResponseDTO> response =
                productService.createNewProduct(
                        productCreateRequestDTO,
                        images,
                        loggedInEmail
                );

        return ResponseEntity.ok(response);
    }




}