package ecommerce.backend.payload.dtoRequest;

import ecommerce.backend.constant.SellerStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellerUpdateStatusRequestDTO {
    private SellerStatus status;
}
