package ecommerce.backend.payload.dtoResponse.seller;


import ecommerce.backend.constant.SellerStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellerResponseDTO {

    private Long id;
    private Long userId;
    private String sellerName;
    private String profileImage;

    private String storeName;
    private String storeEmail;
    private String storePhoneNumber;

    private String gstNumber;
    private String panNumber;

    private SellerStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
