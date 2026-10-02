package ecommerce.backend.payload.dtoResponse.seller;


import ecommerce.backend.constant.SellerAddressType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellerAddressResponseDTO {

    private Long id;
    private Long sellerId;

    private String addressLine1;
    private String addressLine2;
    private String landmark;

    private String city;
    private String state;
    private String country;
    private String postalCode;

    private SellerAddressType addressType;

    private Boolean isDefault;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
