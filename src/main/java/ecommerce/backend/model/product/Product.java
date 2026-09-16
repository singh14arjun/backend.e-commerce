//package ecommerce.backend.model.product;
//
//import ecommerce.backend.model.user.User;
//import ecommerce.backend.model.brand.Brand;
//import ecommerce.backend.model.category.SubCategory;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "products")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class Product {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    // Seller who owns/listed the product
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "seller_id", nullable = false)
//    private User seller;
//
//    // Product belongs to a sub-category
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "sub_category_id", nullable = false)
//    private SubCategory subCategory;
//
//    // Product belongs to a brand
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "brand_id", nullable = false)
//    private Brand brand;
//
//    @Column(nullable = false, length = 200)
//    private String productTitle;
//
//    @Column(nullable = false, unique = true, length = 250)
//    private String slug;
//
//    @Column(columnDefinition = "TEXT")
//    private String description;
//
//    @Column(length = 500)
//    private String shortDescription;
//
//    @Column(nullable = false, precision = 12, scale = 2)
//    private BigDecimal basePrice;
//
//    @Column(precision = 12, scale = 2)
//    private BigDecimal discountPrice;
//
//    @Column(nullable = false)
//    private Boolean status = true;
//
//    @Column(nullable = false)
//    private Boolean featured = false;
//
//    @Column(nullable = false, updatable = false)
//    private LocalDateTime createdAt;
//
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