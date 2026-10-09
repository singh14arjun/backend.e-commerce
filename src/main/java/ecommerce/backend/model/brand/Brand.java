package ecommerce.backend.model.brand;

import ecommerce.backend.constant.BrandStatus;
import ecommerce.backend.model.category.SubCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "brands",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_subcategory_brand_name",
                        columnNames = {"sub_category_id", "name"}
                ),
                @UniqueConstraint(
                        name = "uk_brand_slug",
                        columnNames = {"sub_category_id", "slug"}
                )
        }
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 120)
    private String slug;

    @Column(length = 500)
    private String description;

    private String logoUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BrandStatus status = BrandStatus.ACTIVE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "sub_category_id",
            nullable = false
    )
    private SubCategory subCategory;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();

        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}