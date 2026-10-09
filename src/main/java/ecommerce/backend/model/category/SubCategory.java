package ecommerce.backend.model.category;

import ecommerce.backend.constant.SubCategoryStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "subcategories" ,uniqueConstraints = {
        @UniqueConstraint(
                name = "uk_category_subcategory_name",
                columnNames = {"category_id","name"}
        )
})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(nullable = false,length = 100)
    private String name;

    @Column(nullable = false,length = 500)
    private String description;

    private String image;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubCategoryStatus status= SubCategoryStatus.ACTIVE;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
