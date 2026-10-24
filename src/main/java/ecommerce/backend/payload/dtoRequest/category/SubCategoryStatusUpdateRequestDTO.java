package ecommerce.backend.payload.dtoRequest.category;

import ecommerce.backend.constant.SubCategoryStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubCategoryStatusUpdateRequestDTO {

    private SubCategoryStatus status;
}
