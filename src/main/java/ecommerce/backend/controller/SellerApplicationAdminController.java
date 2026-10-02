package ecommerce.backend.controller;

import ecommerce.backend.payload.dtoRequest.seller.SellerApplicationRequestDTO;
import ecommerce.backend.payload.dtoResponse.APIResponse;
import ecommerce.backend.payload.dtoResponse.seller.SellerApplicationResponseDTO;
import ecommerce.backend.service.SellerApplicationAdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/seller-applications")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class SellerApplicationAdminController {

    private final SellerApplicationAdminService sellerApplicationAdminService;

    @PutMapping("/{applicationId}/approve")
    public ResponseEntity<APIResponse<SellerApplicationResponseDTO>>  approveApplication(
            @PathVariable Long applicationId
    ){
        APIResponse<SellerApplicationResponseDTO> response=sellerApplicationAdminService.approveApplication(applicationId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{applicationId}/reject")
    public ResponseEntity<APIResponse<SellerApplicationResponseDTO>> rejectApplication(
            @PathVariable Long applicationId
    ) {
        APIResponse<SellerApplicationResponseDTO> response =
                sellerApplicationAdminService.rejectApplication(applicationId);

        return ResponseEntity.ok(response);
    }
}
