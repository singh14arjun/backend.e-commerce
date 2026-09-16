package ecommerce.backend.payload.dtoResponse;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ecommerce.backend.constant.Role;
import ecommerce.backend.model.Address;
//import ecommerce.backend.model.PaymentInformation;
//import ecommerce.backend.model.Ratings;
//import ecommerce.backend.model.Reviews;
import ecommerce.backend.model.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

    private Long id;

    private String username;

    private String profilePicture;


    private String password;
    private String firstName;
    private String lastName;

    private String email;

    private String phoneNumber;

    private Role role;

    private Boolean accountStatus ;

    private List<Address> addresses =new ArrayList<>();

//    private List<PaymentInformation> paymentInformation =new ArrayList<>();
//
//    private List<Ratings> ratings=new ArrayList<>();
//
//    private List<Reviews> reviews=new ArrayList<>();

  }
