package ecommerce.backend.payload.dtoRequest.seller;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellerApplicationRequestDTO {

    @NotBlank(message = "Store name is required")
    @Size(max = 150, message = "Store name must not exceed 150 characters")
    private String storeName;

    @NotBlank(message = "Store email is required")
    @Email(message = "Invalid store email")
    @Size(max = 254, message = "Store email must not exceed 254 characters")
    private String storeEmail;

    @NotBlank(message = "Store phone number is required")
    @Pattern(
            regexp = "^[6-9]\\d{9}$",
            message = "Enter a valid 10-digit Indian mobile number"
    )
    private String storePhoneNumber;

    @NotBlank(message = "GST Number is required")
    @Pattern(
            regexp = "^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z][1-9A-Z]Z[0-9A-Z]$",
            message = "Invalid GSTIN format"
    )
    private String gstNumber;

    @NotBlank(message = "PAN number is required")
    @Pattern(
            regexp = "^[A-Z]{5}[0-9]{4}[A-Z]$",
            message = "Invalid PAN format"
    )
    private String panNumber;


}