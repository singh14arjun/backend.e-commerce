package ecommerce.backend.service;

import ecommerce.backend.payload.dtoRequest.SellerUpdateStatusRequestDTO;
import ecommerce.backend.payload.dtoRequest.UserUpdateStatusRequestDTO;
import ecommerce.backend.payload.dtoResponse.APIResponse;
import ecommerce.backend.payload.dtoResponse.UserResponseDTO;
import ecommerce.backend.payload.dtoResponse.seller.SellerApplicationResponseDTO;
import ecommerce.backend.payload.dtoResponse.seller.SellerResponseDTO;

import java.util.List;

public interface AdminService {
    APIResponse<List<SellerResponseDTO>> getAllSellers(String loggedInEmail);
    APIResponse<List<SellerApplicationResponseDTO>> getAllSellersApplications(String loggedInEmail);
    APIResponse<List<SellerResponseDTO>> getSellersApplicationByUserId(String loggedInEmail);
    APIResponse<List<UserResponseDTO>> getAllUsers(String loggedInEmail);
    APIResponse<UserResponseDTO> changeUserStatus(String loggedInEmail, UserUpdateStatusRequestDTO request,Long usedId);
    APIResponse<SellerResponseDTO> changeSellerStatus(String loggedInEmail, SellerUpdateStatusRequestDTO request, Long usedId);
}
