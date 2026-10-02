package ecommerce.backend.controller;

import com.cloudinary.api.ApiResponse;
import ecommerce.backend.model.seller.SellerApplication;
import ecommerce.backend.payload.dtoRequest.seller.SellerApplicationRequestDTO;
import ecommerce.backend.payload.dtoResponse.APIResponse;
import ecommerce.backend.payload.dtoResponse.seller.SellerApplicationResponseDTO;
import ecommerce.backend.payload.dtoResponse.seller.SellerProfileResponseDTO;
import ecommerce.backend.service.SellerApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/seller")
public class SellerApplicationController {

    private final SellerApplicationService sellerApplicationService;

    @PostMapping("/apply")
    public ResponseEntity<APIResponse<SellerApplicationResponseDTO>> getAllSellerApplications(
            @Valid @RequestBody SellerApplicationRequestDTO requestDTO, Authentication authentication
            ) {
        String loggedInEmail = authentication.getName();

        APIResponse<SellerApplicationResponseDTO> response=sellerApplicationService.applyAsSeller(requestDTO, loggedInEmail);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/me")
    public ResponseEntity<APIResponse<SellerProfileResponseDTO>> getMySellerProfile(Authentication authentication) {
        String loggedInEmail = authentication.getName();

        APIResponse<SellerProfileResponseDTO> response=sellerApplicationService.getMySellerProfile(loggedInEmail);

        return ResponseEntity.ok(response);
    }
}
