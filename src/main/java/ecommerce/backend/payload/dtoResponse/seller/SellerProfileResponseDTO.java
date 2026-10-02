package ecommerce.backend.payload.dtoResponse.seller;

import ecommerce.backend.payload.dtoResponse.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellerProfileResponseDTO {

    private UserResponseDTO personalDetails;
    private SellerResponseDTO shopDetails;
    private List<SellerAddressResponseDTO> addresses;


}