//package ecommerce.backend.model.brand;
//
//import jakarta.persistence.*;
//import tools.jackson.databind.annotation.EnumNaming;
//
//import java.time.LocalDateTime;
//
//@Entity
//public class Brand {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;
//    private String name;
//    private String slug;
//    private String description;
//    private String logo_url;
//    private String status;
//
//
//    @Column(nullable = false, updatable = false)
//    private LocalDateTime createdAt;
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
