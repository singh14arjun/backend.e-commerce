package ecommerce.backend.payload.dtoResponse.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryResponseDTO {

    private Long id;

    private Integer quantity;
    private Integer reservedQuantity;
    private Integer availableQuantity;
    private Integer reorderLevel;
}
