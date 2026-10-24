package ecommerce.backend.service.impl;

import ecommerce.backend.constant.Role;
import ecommerce.backend.constant.SellerApplicationStatus;
import ecommerce.backend.constant.SellerStatus;
import ecommerce.backend.model.seller.Seller;
import ecommerce.backend.model.seller.SellerApplication;
import ecommerce.backend.model.user.User;
import ecommerce.backend.payload.dtoResponse.APIResponse;
import ecommerce.backend.payload.dtoResponse.seller.SellerApplicationResponseDTO;
import ecommerce.backend.repository.SellerApplicationRepository;
import ecommerce.backend.repository.SellerRepository;
import ecommerce.backend.service.SellerApplicationAdminService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SellerApplicationAdminServiceImpl implements SellerApplicationAdminService {

    private final SellerApplicationRepository sellerApplicationRepository;
    private final SellerRepository sellerRepository;

    @Override
    @Transactional
    public APIResponse<SellerApplicationResponseDTO> approveApplication(Long applicationId) {

        SellerApplication sellerApplication = sellerApplicationRepository.findById(applicationId)
                .orElseThrow(()->
                        new RuntimeException("Seller application not found with id: "+applicationId));

        if(sellerApplication.getStatus() != SellerApplicationStatus.PENDING){
            throw  new RuntimeException("Seller application has be already processed");
        }



        User user=sellerApplication.getUser();

        if(sellerRepository.existsByUser(user)){
             throw  new RuntimeException("Seller already exists for this user");
        }

        Seller seller= new Seller();

        seller.setUser(user);
        seller.setStoreName(sellerApplication.getStoreName());
        seller.setStoreEmail(sellerApplication.getStoreEmail());
        seller.setStorePhoneNumber(sellerApplication.getStorePhoneNumber());
        seller.setGstNumber(sellerApplication.getGstNumber());
        seller.setPanNumber(sellerApplication.getPanNumber());
        seller.setStatus(SellerStatus.ACTIVE);

        LocalDateTime now = LocalDateTime.now();

        seller.setCreatedAt(now);
        seller.setUpdatedAt(now);

        sellerRepository.save(seller);

        user.setRole(Role.SELLER);
        sellerApplication.setStatus(SellerApplicationStatus.APPROVED);
        sellerApplication.setUpdatedAt(now);

        sellerApplicationRepository.save(sellerApplication);



        return new APIResponse<>(
                true,
                "Seller application approved successfully",
                createSellerApplicationResponseDTO(sellerApplication)

        ) ;
    }

    @Override
    @Transactional
    public APIResponse<SellerApplicationResponseDTO> rejectApplication(Long applicationId) {

        SellerApplication sellerApplication=sellerApplicationRepository.findById(applicationId)
                .orElseThrow(()-> new RuntimeException("Seller application not found with id: "+applicationId));

        if(sellerApplication.getStatus() != SellerApplicationStatus.PENDING){
            throw  new RuntimeException("Seller application has be already processed");
        }

        sellerApplication.setStatus(SellerApplicationStatus.REJECTED);
        sellerApplication.setUpdatedAt(LocalDateTime.now());

        SellerApplication rejectedSellerApplication=sellerApplicationRepository.save(sellerApplication);



        return new APIResponse<>(
                true,
                "Seller application reject successfully"
                ,createSellerApplicationResponseDTO(rejectedSellerApplication)
        );
    }

    private static SellerApplicationResponseDTO createSellerApplicationResponseDTO(SellerApplication sellerApplication) {
        SellerApplicationResponseDTO sellerApplicationResponseDTO = new SellerApplicationResponseDTO();

        sellerApplicationResponseDTO.setId(sellerApplication.getId());
        sellerApplicationResponseDTO.setUserId(sellerApplication.getUser().getId());
        sellerApplicationResponseDTO.setStoreName(sellerApplication.getStoreName());
        sellerApplicationResponseDTO.setStoreEmail(sellerApplication.getStoreEmail());
        sellerApplicationResponseDTO.setStorePhoneNumber(sellerApplication.getStorePhoneNumber());
        sellerApplicationResponseDTO.setGstNumber(sellerApplication.getGstNumber());
        sellerApplicationResponseDTO.setPanNumber(sellerApplication.getPanNumber());
        sellerApplicationResponseDTO.setStatus(sellerApplication.getStatus());
        sellerApplicationResponseDTO.setCreatedAt(sellerApplication.getCreatedAt());
        sellerApplicationResponseDTO.setUpdatedAt(sellerApplication.getUpdatedAt());

        return sellerApplicationResponseDTO;
    }
}
