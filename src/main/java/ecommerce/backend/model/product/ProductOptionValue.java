//package ecommerce.backend.model.product;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Entity
//@Table(name = "product_option_values")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class ProductOptionValue {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "option_id", nullable = false)
//    private ProductOption option;
//
//    @Column(nullable = false, length = 100)
//    private String value;
//}
