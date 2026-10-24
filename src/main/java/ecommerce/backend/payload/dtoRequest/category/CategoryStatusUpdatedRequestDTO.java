package ecommerce.backend.payload.dtoRequest.category;

import ecommerce.backend.constant.CategoryStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryStatusUpdatedRequestDTO {

    private CategoryStatus status;
}
