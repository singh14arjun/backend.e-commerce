//package ecommerce.backend.model.product;
//
//import ecommerce.backend.constant.InventoryTransactionType;
//import ecommerce.backend.model.user.User;
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "inventory_transactions")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class InventoryTransaction {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "inventory_id", nullable = false)
//    private Inventory inventory;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "transaction_type", nullable = false, length = 30)
//    private InventoryTransactionType transactionType;
//
//    @Column(nullable = false)
//    private Integer quantity;
//
//    @Column(name = "reference_type", length = 50)
//    private String referenceType;
//
//    @Column(name = "reference_id")
//    private Long referenceId;
//
//    @Column(name = "previous_quantity", nullable = false)
//    private Integer previousQuantity;
//
//    @Column(name = "new_quantity", nullable = false)
//    private Integer newQuantity;
//
//    @Column(length = 500)
//    private String reason;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "created_by")
//    private User createdBy;
//
//    @Column(name = "created_at", nullable = false, updatable = false)
//    private LocalDateTime createdAt;
//
//    @PrePersist
//    protected void onCreate() {
//        createdAt = LocalDateTime.now();
//    }
//}
