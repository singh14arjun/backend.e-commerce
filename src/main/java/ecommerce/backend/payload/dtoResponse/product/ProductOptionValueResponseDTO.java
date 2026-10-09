package ecommerce.backend.payload.dtoResponse.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductOptionValueResponseDTO {

    private Long id;

    private String value;
}
