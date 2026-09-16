package ecommerce.backend.service;

import ecommerce.backend.exception.UserException;
import ecommerce.backend.model.user.User;
import ecommerce.backend.payload.dtoRequest.UserRegisterRequestDTO;
import ecommerce.backend.payload.dtoResponse.APIResponse;
import ecommerce.backend.payload.dtoResponse.LoginResponseDTO;
import ecommerce.backend.payload.dtoResponse.UserResponseDTO;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {

    public APIResponse<UserResponseDTO> createUser(UserRegisterRequestDTO user, MultipartFile multipartFile) throws IOException;

    public User findUserByEmail(String email)throws UserException;

    public APIResponse<LoginResponseDTO> login (String email, String password);

    public APIResponse<UserResponseDTO> updateUser(String loggedInEmail);

    public APIResponse<String> deleteUserByUserId(Long userId,String loggedInEmail)throws UserException;
}
