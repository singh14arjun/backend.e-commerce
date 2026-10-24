package ecommerce.backend.model.seller;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class SellerStoreImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_application_id", nullable = false)
    private SellerApplication sellerApplication;

    @Column(nullable = false, length = 1000)
    private String imageUrl;

    private String imageType; 
}
