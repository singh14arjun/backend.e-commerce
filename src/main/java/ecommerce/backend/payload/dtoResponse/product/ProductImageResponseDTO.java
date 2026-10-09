package ecommerce.backend.payload.dtoResponse.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductImageResponseDTO {
    private Long id;

    private String imageUrl;

    private String altText;

    private Integer displayOrder;

    private Boolean isPrimary;
}
