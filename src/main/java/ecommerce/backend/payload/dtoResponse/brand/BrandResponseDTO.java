package ecommerce.backend.payload.dtoResponse.brand;

import ecommerce.backend.constant.BrandStatus;
import ecommerce.backend.constant.CategoryStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandResponseDTO {
    private Long id;
    private String name;
    private String slug;
    private String description;
    private String logoUrl;
    private Long subCategoryId;
    private String subCategoryName;

    private BrandStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
