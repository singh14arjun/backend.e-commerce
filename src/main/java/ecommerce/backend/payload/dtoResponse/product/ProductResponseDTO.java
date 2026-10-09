package ecommerce.backend.payload.dtoResponse.product;

import ecommerce.backend.constant.ProductStatus;
import ecommerce.backend.payload.dtoResponse.IdNameResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDTO {

    private Long id;
    private IdNameResponseDTO seller;
    private IdNameResponseDTO category;
    private IdNameResponseDTO subCategory;
    private IdNameResponseDTO brand;
    private String productTitle;
    private String slug;
    private String description;
    private String shortDescription;
    private Boolean featured;
    private Boolean isWishlisted;

    private ProductStatus status;

    private List<ProductAttributeResponseDTO> attributes;
    private List<ProductOptionResponseDTO> options;
    private List<ProductVariantResponseDTO> variants;
    private List<ProductImageResponseDTO> images;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
