package ecommerce.backend.payload.dtoRequest.product;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class ProductOptionRequestDTO {

    @NotBlank(message = "Product Option name is required")
    @Size(max = 100)
    private String name;

    @NotEmpty(message = "Option mist have at least one values")
    @Valid
    private List<ProductOptionValueRequestDTO> values;
}
