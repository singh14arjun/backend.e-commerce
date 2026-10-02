package ecommerce.backend.payload.dtoResponse.seller;


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

    private String storeName;
    private String storeEmail;
    private String storePhoneNumber;

    private String gstNumber;
    private String panNumber;

    private Boolean isActive;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
