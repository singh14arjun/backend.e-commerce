package ecommerce.backend.payload.dtoRequest.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductAttributeRequestDTO {

    @NotBlank(message = "Product attribute is required")
    @Size(max = 100)
    private String name;

    @NotBlank(message = "Product Attribute value is required")
   @Size(max = 500)
    private String value;
}
