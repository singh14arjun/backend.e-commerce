package ecommerce.backend.controller;

import ecommerce.backend.payload.dtoRequest.SellerUpdateStatusRequestDTO;
import ecommerce.backend.payload.dtoRequest.UserUpdateStatusRequestDTO;
import ecommerce.backend.payload.dtoResponse.APIResponse;
import ecommerce.backend.payload.dtoResponse.UserResponseDTO;
import ecommerce.backend.payload.dtoResponse.seller.SellerApplicationResponseDTO;
import ecommerce.backend.payload.dtoResponse.seller.SellerResponseDTO;
import ecommerce.backend.service.AdminService;
import ecommerce.backend.service.SellerApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/sellers")
public class AdminController {


   private final AdminService adminService;

    @GetMapping("/all")
    public ResponseEntity<APIResponse<List<SellerResponseDTO>>> getAllSellers(Authentication authentication){

        String email = authentication.getName();
        APIResponse<List<SellerResponseDTO>> response= adminService.getAllSellers(email);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/applications")
    public  ResponseEntity<APIResponse<List<SellerApplicationResponseDTO>>> getAllSellerApplications(Authentication authentication){
        APIResponse<List<SellerApplicationResponseDTO>> response =adminService.getAllSellersApplications(authentication.getName());

        return ResponseEntity.ok(response);
    }
    @GetMapping("/users")
    public  ResponseEntity<APIResponse<List<UserResponseDTO>>> getAllUsers(Authentication authentication){

        APIResponse<List<UserResponseDTO>> response =adminService.getAllUsers(authentication.getName());
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{userId}/update")
    public ResponseEntity<APIResponse<UserResponseDTO>> updateUserStatus(
            @PathVariable Long userId,
            @RequestBody  UserUpdateStatusRequestDTO requestDTO,

            Authentication authentication)
    {

        String email = authentication.getName();
        APIResponse<UserResponseDTO> response =adminService.changeUserStatus(email,requestDTO,userId);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{sellerId}/update-seller")
    public ResponseEntity<APIResponse<SellerResponseDTO>> updateSellerStatus(
            @PathVariable Long sellerId,
            @RequestBody SellerUpdateStatusRequestDTO
                    requestDTO,

            Authentication authentication)
    {

        String email = authentication.getName();
        APIResponse<SellerResponseDTO> response =adminService.changeSellerStatus(email,requestDTO,sellerId);

        return ResponseEntity.ok(response);
    }
}
