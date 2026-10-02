package ecommerce.backend.service;

import ecommerce.backend.payload.dtoRequest.seller.SellerApplicationRequestDTO;
import ecommerce.backend.payload.dtoResponse.APIResponse;
import ecommerce.backend.payload.dtoResponse.seller.SellerApplicationResponseDTO;
import ecommerce.backend.payload.dtoResponse.seller.SellerProfileResponseDTO;

public interface SellerApplicationService {

    public APIResponse<SellerApplicationResponseDTO> applyAsSeller(SellerApplicationRequestDTO sellerApplicationRequestDTO , String loggedInEmail);

    APIResponse<SellerProfileResponseDTO> getMySellerProfile(String email);
}
