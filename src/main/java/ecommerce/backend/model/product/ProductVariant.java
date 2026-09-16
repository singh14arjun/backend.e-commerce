//package ecommerce.backend.model.product;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//
//@Entity
//@Table(
//        name = "product_variants",
//        uniqueConstraints = {
//                @UniqueConstraint(
//                        name = "uk_product_variant_sku",
//                        columnNames = "sku"
//                )
//        }
//)
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class ProductVariant {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "product_id", nullable = false)
//    private Product product;
//
//    @Column(nullable = false, unique = true, length = 100)
//    private String sku;
//
//    @Column(precision = 12, scale = 2)
//    private BigDecimal price;
//
//    @Column(name = "discount_price", precision = 12, scale = 2)
//    private BigDecimal discountPrice;
//
//    @Column(precision = 10, scale = 3)
//    private BigDecimal weight;
//
//    @Column(nullable = false)
//    private Boolean status = true;
//
//    @Column(name = "created_at", nullable = false, updatable = false)
//    private LocalDateTime createdAt;
//
//    @Column(name = "updated_at")
//    private LocalDateTime updatedAt;
//
//    @PrePersist
//    protected void onCreate() {
//        createdAt = LocalDateTime.now();
//        updatedAt = LocalDateTime.now();
//    }
//
//    @PreUpdate
//    protected void onUpdate() {
//        updatedAt = LocalDateTime.now();
//    }
//}
