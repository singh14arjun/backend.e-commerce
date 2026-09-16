package ecommerce.backend.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ecommerce.backend.constant.Role;
import ecommerce.backend.model.Address;
//import ecommerce.backend.model.PaymentInformation;
//import ecommerce.backend.model.Ratings;
//import ecommerce.backend.model.Reviews;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String profilePicture;



    private String password;

    private String firstName;
    private String lastName;

    @Column(unique = true,nullable = false)
    private String email;

    @Column(unique = true ,nullable = false)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Role is required")
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Address> addresses =new ArrayList<>();

//    @Embedded
//    @ElementCollection(fetch = FetchType.EAGER)
//    @CollectionTable(name = "payment_information" ,joinColumns = @JoinColumn(name = "user_id"))
//    private List<PaymentInformation> paymentInformation =new ArrayList<>();

//    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
//    @JsonIgnore
//    private List<Ratings> ratings=new ArrayList<>();

//    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL)
//    @JsonIgnore
//    private List<Reviews> reviews=new ArrayList<>();

    private Boolean accountStatus ;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
