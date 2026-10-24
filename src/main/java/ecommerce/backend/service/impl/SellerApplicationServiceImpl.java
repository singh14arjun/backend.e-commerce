package ecommerce.backend.service.impl;

import com.cloudinary.api.ApiResponse;
import ecommerce.backend.constant.Role;
import ecommerce.backend.constant.SellerApplicationStatus;
import ecommerce.backend.exception.EmailAlreadyExistsException;
import ecommerce.backend.exception.UserNotFoundException;
import ecommerce.backend.mapper.Mapper;
import ecommerce.backend.model.seller.Seller;
import ecommerce.backend.model.seller.SellerApplication;
import ecommerce.backend.model.user.User;
import ecommerce.backend.payload.dtoRequest.seller.SellerApplicationRequestDTO;
import ecommerce.backend.payload.dtoResponse.APIResponse;
import ecommerce.backend.payload.dtoResponse.UserResponseDTO;
import ecommerce.backend.payload.dtoResponse.seller.SellerAddressResponseDTO;
import ecommerce.backend.payload.dtoResponse.seller.SellerApplicationResponseDTO;
import ecommerce.backend.payload.dtoResponse.seller.SellerProfileResponseDTO;
import ecommerce.backend.payload.dtoResponse.seller.SellerResponseDTO;
import ecommerce.backend.repository.SellerApplicationRepository;
import ecommerce.backend.repository.SellerRepository;
import ecommerce.backend.repository.UserRepository;
import ecommerce.backend.service.SellerApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SellerApplicationServiceImpl implements SellerApplicationService {

    private  final SellerApplicationRepository sellerApplicationRepository;
    private final SellerRepository sellerRepository;
    private final UserRepository userRepository;

    @Override
    public APIResponse<SellerApplicationResponseDTO> applyAsSeller(SellerApplicationRequestDTO sellerApplicationRequestDTO, String loggedInEmail) {

        User user = userRepository.findByEmail(loggedInEmail).orElseThrow(
                ()-> new UserNotFoundException("User not found with email: " + loggedInEmail)
        );

        if(user.getRole() != Role.CUSTOMER){
            throw new RuntimeException("Only customers can apply to become sellers ");
        }

        if(sellerApplicationRepository.existsByUser(user)){
            throw new RuntimeException("Seller Application already exists");
        }

        if(sellerApplicationRepository.existsByStoreEmail(sellerApplicationRequestDTO.getStoreEmail())){
            throw new EmailAlreadyExistsException("Store email is already registered");
        }

        SellerApplication sellerApplication=new SellerApplication();

        sellerApplication.setUser(user);
        sellerApplication.setStoreEmail(sellerApplicationRequestDTO.getStoreEmail());
        sellerApplication.setStoreName(sellerApplicationRequestDTO.getStoreName());
        sellerApplication.setStorePhoneNumber(sellerApplicationRequestDTO.getStorePhoneNumber());
        sellerApplication.setGstNumber(sellerApplicationRequestDTO.getGstNumber());
        sellerApplication.setPanNumber(sellerApplicationRequestDTO.getPanNumber());
        sellerApplication.setStatus(SellerApplicationStatus.PENDING);

       SellerApplication savedApplication= sellerApplicationRepository.save(sellerApplication);

       return new APIResponse<>(
               true,
               "Store application applied succesfully"
               ,Mapper.createSellerApplicationResponseDTO(savedApplication)
       );
}


    @Override
    public APIResponse<SellerProfileResponseDTO> getMySellerProfile(String email) {

        User user=userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException("User not found with email: " + email));

        Seller seller=sellerRepository.findByUser(user).orElseThrow(()-> new RuntimeException("Seller profile not founded with this is : " + email));

        UserResponseDTO userResponseDTO= Mapper.mapToResponseDTO(user);

        SellerResponseDTO sellerResponseDTO =
                Mapper.createSellerResponseDTO(seller);

        List<SellerAddressResponseDTO> addresses =
                seller.getAddresses()
                        .stream()
                        .map(Mapper::createSellerAddressResponseDTO)
                        .toList();

        SellerProfileResponseDTO profileResponseDTO=new SellerProfileResponseDTO(userResponseDTO,sellerResponseDTO,addresses);

        return new APIResponse<>(
                true,
                "Seller profile fetched successfully",
                profileResponseDTO

        );
    }

//    private static SellerApplicationResponseDTO createSellerApplicationResponseDTO(SellerApplication sellerApplication){
//
//        SellerApplicationResponseDTO sellerApplicationResponseDTO=new SellerApplicationResponseDTO();
//
//        sellerApplicationResponseDTO.setId(sellerApplication.getId());
//        sellerApplicationResponseDTO.setUserId(sellerApplication.getUser().getId());
//        sellerApplicationResponseDTO.setStoreName(sellerApplication.getStoreName());
//        sellerApplicationResponseDTO.setStoreEmail(sellerApplication.getStoreEmail());
//
//        sellerApplicationResponseDTO.setStorePhoneNumber(sellerApplication.getStorePhoneNumber());
//        sellerApplicationResponseDTO.setGstNumber(sellerApplication.getGstNumber());
//        sellerApplicationResponseDTO.setPanNumber(sellerApplication.getPanNumber());
//        sellerApplicationResponseDTO.setStatus(sellerApplication.getStatus());
//        sellerApplicationResponseDTO.setCreatedAt(sellerApplication.getCreatedAt());
//        sellerApplicationResponseDTO.setUpdatedAt(sellerApplication.getUpdatedAt());
//
//        return sellerApplicationResponseDTO;
//
//
//    }
}
