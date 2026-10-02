package ecommerce.backend.service;

import ecommerce.backend.payload.dtoResponse.APIResponse;
import ecommerce.backend.payload.dtoResponse.seller.SellerApplicationResponseDTO;

public interface SellerApplicationAdminService {

    public APIResponse<SellerApplicationResponseDTO> approveApplication(Long applicationId);
    public APIResponse<SellerApplicationResponseDTO> rejectApplication(Long applicationId);
}
