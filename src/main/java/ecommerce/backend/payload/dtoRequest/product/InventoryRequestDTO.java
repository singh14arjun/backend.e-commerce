package ecommerce.backend.payload.dtoRequest.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InventoryRequestDTO {

    @NotNull
    @Min(0)
    private Integer quantity;

    @Min(0)
    private Integer reorderLevel=10;
}
