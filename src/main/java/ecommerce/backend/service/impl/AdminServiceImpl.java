package ecommerce.backend.service.impl;

import ecommerce.backend.constant.Role;
import ecommerce.backend.exception.UserNotFoundException;
import ecommerce.backend.mapper.Mapper;
import ecommerce.backend.model.seller.Seller;
import ecommerce.backend.model.seller.SellerApplication;
import ecommerce.backend.model.user.User;
import ecommerce.backend.payload.dtoRequest.SellerUpdateStatusRequestDTO;
import ecommerce.backend.payload.dtoRequest.UserUpdateStatusRequestDTO;
import ecommerce.backend.payload.dtoResponse.APIResponse;
import ecommerce.backend.payload.dtoResponse.UserResponseDTO;
import ecommerce.backend.payload.dtoResponse.seller.SellerApplicationResponseDTO;
import ecommerce.backend.payload.dtoResponse.seller.SellerResponseDTO;
import ecommerce.backend.repository.SellerApplicationRepository;
import ecommerce.backend.repository.SellerRepository;
import ecommerce.backend.repository.UserRepository;
import ecommerce.backend.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private  final UserRepository userRepository;
    private final SellerRepository sellerRepository;
    private final SellerApplicationRepository sellerApplicationRepository;
    @Override
    public APIResponse<List<SellerResponseDTO>> getAllSellers(String loggedInEmail) {

        User user = userRepository.findByEmail(loggedInEmail).orElseThrow(
                ()-> new UserNotFoundException("User not found with email: " + loggedInEmail)
        );
       checkAdmin(user);

        List<SellerResponseDTO> sellerList=sellerRepository.findAll()
                .stream()
                .map(Mapper::createSellerResponseDTO)
                .toList();




        return new APIResponse<>(
                true,
                sellerList.isEmpty()
                        ?"Seller list is empty"
                        :"Seller list",
                sellerList
        );
    }

    @Override
    public APIResponse<List<SellerApplicationResponseDTO>> getAllSellersApplications(String loggedInEmail) {

               User user = userRepository.findByEmail(loggedInEmail)
                       .orElseThrow(()->
                               new UserNotFoundException("User not found with email: " + loggedInEmail));

               checkAdmin(user);

        List<SellerApplicationResponseDTO> sellerApplicationList = sellerApplicationRepository.findAll()
                .stream()
                .map(Mapper::createSellerApplicationResponseDTO)
                .toList();


        return new APIResponse<>(
                true,
                sellerApplicationList.isEmpty()
                ?"Seller application list is empty"
                :"Sellers Application list",
                sellerApplicationList
        );

    }

    @Override
    public APIResponse<List<SellerResponseDTO>> getSellersApplicationByUserId(String loggedInEmail) {
        return null;
    }

    @Override
    public APIResponse<List<UserResponseDTO>> getAllUsers(String loggedInEmail) {

        User findUser = userRepository.findByEmail(loggedInEmail).orElseThrow(
                ()-> new UserNotFoundException("User not found with email: " + loggedInEmail));
        checkAdmin(findUser);

        List<UserResponseDTO> dtoList=userRepository.findAllByRole(Role.CUSTOMER)
                .stream()
                .map(Mapper::mapToResponseDTO)
                .toList();

        return new APIResponse<>(
                true,
                dtoList.isEmpty()
                ?"Users list is empty"
                        :"Users list",
                dtoList
        );
    }

    @Override
    public APIResponse<UserResponseDTO> changeUserStatus(String loggedInEmail, UserUpdateStatusRequestDTO request,Long userId) {

        User user=userRepository.findById(userId).orElseThrow(
                ()-> new UserNotFoundException("User not found with id: " + userId)
        );

        User admin=userRepository.findByEmail(loggedInEmail).orElseThrow(
                ()-> new UserNotFoundException("User not found with email: " + loggedInEmail)
        );

        checkAdmin(admin);

        user.setStatus(request.getStatus());
        userRepository.save(user);



        return new APIResponse<>(
                true,
                "User status updated succesfully",
                Mapper.mapToResponseDTO(user)
        );
    }

    @Override
    public APIResponse<SellerResponseDTO> changeSellerStatus(String loggedInEmail, SellerUpdateStatusRequestDTO request, Long sellerId) {

        Seller seller =sellerRepository.findById(sellerId).orElseThrow(
                ()-> new RuntimeException("Seller not found with id: " + sellerId)

        );

        User admin=userRepository.findByEmail(loggedInEmail).orElseThrow(
                ()-> new UserNotFoundException("User not found with email: " + loggedInEmail)
        );

        checkAdmin(admin);
        seller.setStatus(request.getStatus());
        sellerRepository.save(seller);
        return new APIResponse<>(
                true,
                "Seller status updated successfully",
                Mapper.createSellerResponseDTO(seller)
        );
    }


    private void checkAdmin(User user) {

        if (user.getRole() != Role.ADMIN) {
            throw new AccessDeniedException(
                    "Only admin can perform this action"
            );
        }
    }
}
