package ecommerce.backend.payload.dtoResponse.category;


import ecommerce.backend.constant.CategoryStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponseDTO {

    private Long id;

    private String name;

    private String description;

    private String image;

    private CategoryStatus status;

    private Integer subCategoryCount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
