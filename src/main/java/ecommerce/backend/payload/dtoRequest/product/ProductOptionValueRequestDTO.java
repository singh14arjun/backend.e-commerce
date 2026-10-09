package ecommerce.backend.payload.dtoRequest.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductOptionValueRequestDTO {

    @NotBlank(message = "Option values is required")
    @Size(max = 100,message = "Option value must  not exceed 100 characters")
    private String value;
}
