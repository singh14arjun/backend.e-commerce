package ecommerce.backend.service.impl;

import com.cloudinary.Cloudinary;
import ecommerce.backend.cloudinary.CloudImageService;
import ecommerce.backend.constant.Role;
import ecommerce.backend.exception.EmailAlreadyExistsException;
import ecommerce.backend.exception.PhoneNumberAlreadyExistsException;
import ecommerce.backend.exception.UserException;
import ecommerce.backend.exception.UserNotFoundException;
import ecommerce.backend.model.user.User;
import ecommerce.backend.payload.dtoRequest.UserRegisterRequestDTO;
import ecommerce.backend.payload.dtoResponse.APIResponse;
import ecommerce.backend.payload.dtoResponse.LoginResponseDTO;
import ecommerce.backend.payload.dtoResponse.UserResponseDTO;
import ecommerce.backend.repository.UserRepository;
import ecommerce.backend.secuirty.JwtService;
import ecommerce.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final Cloudinary cloudinary;
    private final CloudImageService  cloudImageService;



    @Override
    public APIResponse<UserResponseDTO> createUser(UserRegisterRequestDTO user, MultipartFile multipartFile) throws IOException {

        Optional<User> emailUser = userRepository.findByEmail(user.getEmail());
        if (emailUser.isPresent()) {
            throw new EmailAlreadyExistsException("Email Already Exists");
        }

        Optional<User> phoneUser=userRepository.findByPhoneNumber(user.getPhoneNumber());

        if (phoneUser.isPresent()) {
            throw new PhoneNumberAlreadyExistsException("Phone number already exists.");
        }

        User newUser = new User();
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());
        newUser.setUsername(user.getEmail());
        newUser.setPhoneNumber(user.getPhoneNumber());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setRole(Role.CUSTOMER);
        newUser.setAccountStatus(true);

        if (multipartFile != null && !multipartFile.isEmpty()) {

            Map<?, ?> uploadResult =
                    cloudImageService.uploadImage(multipartFile);

            Object secureUrl = uploadResult.get("secure_url");

            if (secureUrl != null) {
                newUser.setProfilePicture(secureUrl.toString());
            }
        }

        User savedUser = userRepository.save(newUser);

        UserResponseDTO userResponseDTO = new UserResponseDTO();


        return new APIResponse<>(
                true,
                "User register successfully",
                mapToResponseDTO(savedUser)
        );
    }

    @Override
    public User findUserByEmail(String email) throws UserException {
        return null;
    }

    @Override
    public APIResponse<LoginResponseDTO> login(String email, String password) {

        User user=userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException("User not found with email : "+email));

        if(!passwordEncoder.matches(password,user.getPassword())) {
            return new APIResponse<>(
                    false,
                    "Invalid Password",
                    null
            );
        }

        String token=jwtService.generateToken(user.getId(),user.getEmail(),user.getRole().name());

        UserResponseDTO userResponseDTO = mapToResponseDTO(user);

        LoginResponseDTO loginResponseDTO =  LoginResponseDTO.builder().token(token).userResponseDTO(userResponseDTO).build();


        return new APIResponse<>(
                true,
                "Login successfully",
                loginResponseDTO
        );
    }

    @Override
    public APIResponse<String> deleteUserByUserId(
            Long userId,
            String loggedInEmail
    ) throws UserException {

        User loggedInUser = getLoggedInUser(loggedInEmail);

        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            return new APIResponse<>(
                    false,
                    "User not found with id: " + userId,
                    null
            );
        }

        if (loggedInUser.getRole() == Role.CUSTOMER) {

            if (loggedInUser.getId().equals(userId)) {

                userRepository.delete(optionalUser.get());

                return new APIResponse<>(
                        true,
                        "User deleted successfully",
                        "Deleted user with ID: " + userId
                );
            }

            return new APIResponse<>(
                    false,
                    "You cannot delete this account: " + userId,
                    null
            );
        }

        return new APIResponse<>(
                false,
                "Only customers can delete their own account",
                null
        );
    }
    private static UserResponseDTO mapToResponseDTO(User user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setUsername(user.getUsername());
        userResponseDTO.setFirstName(user.getFirstName());
        userResponseDTO.setLastName(user.getLastName());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setProfilePicture(user.getProfilePicture());
//        userResponseDTO.setPassword(user.getPassword());
        userResponseDTO.setRole(user.getRole());
        userResponseDTO.setPhoneNumber(user.getPhoneNumber());
        userResponseDTO.setAccountStatus(user.getAccountStatus());
        return userResponseDTO;
    }


    private User getLoggedInUser(String loggedInEmail)  {


        return userRepository.findByEmail(loggedInEmail)
                .orElseThrow(
                        ()->new RuntimeException("User not found with email : "+loggedInEmail));
    }
}
