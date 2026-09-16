//package ecommerce.backend.model.product;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "inventories")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class Inventory {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(
//            name = "variant_id",
//            nullable = false,
//            unique = true
//    )
//    private ProductVariant variant;
//
//    @Column(nullable = false)
//    private Integer quantity = 0;
//
//    @Column(name = "reserved_quantity", nullable = false)
//    private Integer reservedQuantity = 0;
//
//    @Column(name = "reorder_level", nullable = false)
//    private Integer reorderLevel = 10;
//
//    @Column(name = "updated_at")
//    private LocalDateTime updatedAt;
//
//    @PrePersist
//    @PreUpdate
//    protected void updateTimestamp() {
//        updatedAt = LocalDateTime.now();
//    }
//
//    public Integer getAvailableQuantity() {
//        return quantity - reservedQuantity;
//    }
//}
