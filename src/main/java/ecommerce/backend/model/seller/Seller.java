package ecommerce.backend.model.seller;


import ecommerce.backend.constant.SellerStatus;
import ecommerce.backend.model.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sellers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(
            name = "user_id",
            nullable = false,
            unique = true
    )
    private User user;

    @Column(nullable = false)
    private String storeName;

    @Column(nullable = false)
    private String storeEmail;

    @Column(nullable = false)
    private String storePhoneNumber;

    private String gstNumber;

    private String panNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SellerStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @OneToMany(
            mappedBy = "seller",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<SellerAddress> addresses = new ArrayList<>();
}
