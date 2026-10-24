package ecommerce.backend.payload.dtoRequest.brand;

import ecommerce.backend.constant.BrandStatus;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandRequestDTO {

    @NotBlank(message = "Brand name is required")
    @Size(
            min = 2,
            max = 100,
            message = "Brand name must be between 2 and 100 characters"
    )
    private String name;

    @NotBlank(message = "Brand slug is required")
    @Size(
            min = 2,
            max = 120,
            message = "Brand slug must be between 2 and 120 characters"
    )
    @Pattern(
            regexp = "^[a-z0-9]+(?:-[a-z0-9]+)*$",
            message = "Slug must contain only lowercase letters, numbers and hyphens"
    )
    private String slug;

    @Size(
            max = 500,
            message = "Description must not exceed 500 characters"
    )
    private String description;

    @NotNull(message = "Brand status is required")
    private BrandStatus status = BrandStatus.ACTIVE;

//    @NotNull(message = "Sub category is required")
//    @Positive(message = "Sub category ID must be greater than 0")
//    private Long subCategoryId;
}