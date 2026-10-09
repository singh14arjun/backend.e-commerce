package ecommerce.backend.payload.dtoResponse.product;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductAttributeResponseDTO {

    private Long id;
    private String name;
    private String value;
}
