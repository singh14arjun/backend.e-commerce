package ecommerce.backend.payload.dtoRequest.seller;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SellerApplicationRequestDTO {

    @NotBlank(message = "Store name is required")
    private String storeName;

    @NotBlank(message = "Store email is required")
    @Email(message = "Invalid store email")
    private String storeEmail;

    @NotBlank(message = "Store phone number is required")
    @Pattern(
            regexp = "^[6-9]\\d{9}$",
            message = "Invalid Indian phone number"
    )
    private String storePhoneNumber;

    private String gstNumber;

    private String panNumber;
}
