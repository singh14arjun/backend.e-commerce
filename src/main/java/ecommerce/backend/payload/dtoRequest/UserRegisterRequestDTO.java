package ecommerce.backend.payload.dtoRequest;

import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegisterRequestDTO {

    @NotBlank(message = "Password is required")
    @Size(
            min = 8,
            max = 50,
            message = "Password must be between 8 and 50 characters"
    )
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$",
            message = "Password must contain uppercase, lowercase, number and special character"
    )
    private String password;

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50,message = "First name must from 2 to 50 character")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50,message = "Last name must from 2 to 50 character")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "Email must contain a valid domain extension"
    )
    private String email;


    @NotBlank(message = "Phone number is required")
    @Pattern(
            regexp = "^[6-9][0-9]{9}$",
            message = "Phone number must be exactly 10 digits and start with 6, 7, 8 or 9"
    )
    private String phoneNumber;

    private Boolean accountStatus ;

}

