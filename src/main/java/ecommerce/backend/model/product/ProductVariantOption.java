//package ecommerce.backend.model.product;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.awt.*;
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "variant_options")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//
//public class ProductVariantOption {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "variant_id", nullable = false)
//    private ProductVariant variant;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "option_id", nullable = false)
//    private ProductOption option;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "option_value_id", nullable = false)
//    private ProductOptionValue optionValue;
//}
//
