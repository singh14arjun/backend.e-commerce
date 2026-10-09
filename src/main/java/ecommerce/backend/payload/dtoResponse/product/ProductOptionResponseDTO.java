package ecommerce.backend.payload.dtoResponse.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductOptionResponseDTO  {

    private Long id;
    private String name;

    private List<ProductOptionValueResponseDTO> values;
}
