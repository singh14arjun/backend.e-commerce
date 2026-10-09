package ecommerce.backend.payload.dtoRequest.product;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class ProductVariantRequestDTO {

    @NotBlank(message = "Product Variant sku is required")
    @Size(max = 100)
    private String sku;

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal price;

    @DecimalMin(value = "0.00")
    @DecimalMax(value = "100.00")
    private BigDecimal discountPercentage;

    @DecimalMin(value = "0.001")
    private BigDecimal weight;

    @NotNull
    private Map<String, String> options;

    @NotNull
    private InventoryRequestDTO inventory;

}
