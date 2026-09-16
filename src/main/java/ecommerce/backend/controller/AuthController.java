package ecommerce.backend.controller;

import ecommerce.backend.exception.UserException;
import ecommerce.backend.payload.dtoRequest.UserLoginRequestDTO;
import ecommerce.backend.payload.dtoRequest.UserRegisterRequestDTO;
import ecommerce.backend.payload.dtoResponse.APIResponse;
import ecommerce.backend.payload.dtoResponse.LoginResponseDTO;
import ecommerce.backend.payload.dtoResponse.UserResponseDTO;
import ecommerce.backend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    @PostMapping(
            value = "/register",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<APIResponse<UserResponseDTO>> registerUser(

            @Valid
            @ModelAttribute UserRegisterRequestDTO userRegisterRequestDTO,

            @RequestParam(
                    value = "profilePicture",
                    required = false
            )
            MultipartFile multipartFile

    ) throws IOException {

        return ResponseEntity.ok(
                userService.createUser(
                        userRegisterRequestDTO,
                        multipartFile
                )
        );
    }

    @PostMapping("/login")
    public ResponseEntity<APIResponse<LoginResponseDTO>> login(
            @Valid @RequestBody UserLoginRequestDTO userLoginRequestDTO
            )
    {
        return ResponseEntity.ok(userService.login(userLoginRequestDTO.getEmail(),userLoginRequestDTO.getPassword()));
    }

    @DeleteMapping("/delete/{userId}")

    public ResponseEntity<APIResponse<String>> deleteUser(@PathVariable Long userId, Authentication authentication) throws UserException {

        String email = authentication.getName();

        return ResponseEntity.ok(userService.deleteUserByUserId(userId,email));
    }
}