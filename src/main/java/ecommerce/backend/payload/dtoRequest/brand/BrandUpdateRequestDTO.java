package ecommerce.backend.payload.dtoRequest.brand;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandUpdateRequestDTO {

    private String name;
    private String slug;
    private String description;
//    private Long subCategoryId;

}
