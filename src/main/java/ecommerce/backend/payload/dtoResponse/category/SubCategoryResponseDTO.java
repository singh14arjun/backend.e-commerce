package ecommerce.backend.payload.dtoResponse.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubCategoryResponseDTO {

    private Long id;

    private String name;

    private String description;

    private String image;

    private Boolean status;

    private Long categoryId;

    private String categoryName;
}