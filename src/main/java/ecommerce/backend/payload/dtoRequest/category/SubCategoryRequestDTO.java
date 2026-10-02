package ecommerce.backend.payload.dtoRequest.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SubCategoryRequestDTO {

    @NotBlank(message = "Subcategory name is required")
    @Size(max = 100, message = "Subcategory name cannot exceed 100 characters")
    private String name;

    @Size(
            max = 500,
            message = "Description cannot exceed 500 characters"
    )
    private String description;

    @NotNull(message = "Category ID is required")
    private Long categoryId;
}