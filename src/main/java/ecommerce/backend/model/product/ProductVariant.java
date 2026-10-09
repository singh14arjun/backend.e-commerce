package ecommerce.backend.model.product;

import ecommerce.backend.constant.ProductVariantStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "product_variants",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_product_variant_sku",
                        columnNames = "sku"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false, unique = true, length = 100)
    private String sku;

    // Original price
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    // Discount percentage
    @Column(
            name = "discount_percentage",
            precision = 5,
            scale = 2
    )
    private BigDecimal discountPercentage;

    // Calculated discount amount
    @Column(
            name = "discount_amount",
            precision = 12,
            scale = 2
    )
    private BigDecimal discountAmount;

    // Final selling price
    @Column(
            name = "payable_amount",
            nullable = false,
            precision = 12,
            scale = 2
    )
    private BigDecimal payableAmount;

    // Product weight
    @Column(precision = 10, scale = 3)
    private BigDecimal weight;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductVariantStatus status = ProductVariantStatus.ACTIVE;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}