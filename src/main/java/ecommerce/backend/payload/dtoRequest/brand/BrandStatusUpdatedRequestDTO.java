package ecommerce.backend.payload.dtoRequest.brand;

import ecommerce.backend.constant.BrandStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandStatusUpdatedRequestDTO {

    private BrandStatus status;
}
