package ecommerce.backend.payload.dtoResponse.product;

import ecommerce.backend.constant.ProductVariantStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductVariantResponseDTO {

    private Long id;
    private String sku;
    private BigDecimal price;
    private BigDecimal discountPercentage;

    private BigDecimal discountAmount;

    private BigDecimal payableAmount;
    private BigDecimal weight;

    private ProductVariantStatus status;

    private Map<String ,String> options;

    private InventoryResponseDTO inventory;
}
