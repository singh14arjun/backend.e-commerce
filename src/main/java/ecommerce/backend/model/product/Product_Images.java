//package ecommerce.backend.model.product;
//
//import jakarta.persistence.*;
//import jakarta.validation.constraints.NotBlank;
//
//import java.awt.*;
//import java.time.LocalDateTime;
//
//public class Product_Images {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    private Product product;
//
//    @Column(nullable = false)
//    @NotBlank(message = "image url is required")
//    private String image_url;
//
//    private String alternative_image_url;
//    private String alt_text;
//
//    private Integer displayOrder;
//
//    private Boolean is_Primary;
//    @Column(name = "created_at", nullable = false, updatable = false)
//
//    private LocalDateTime created_at;
//    private LocalDateTime updated_at;
//
//
//}
