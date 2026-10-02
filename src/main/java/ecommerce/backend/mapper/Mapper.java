package ecommerce.backend.mapper;

import ecommerce.backend.model.category.Category;
import ecommerce.backend.model.category.SubCategory;
import ecommerce.backend.model.seller.Seller;
import ecommerce.backend.model.seller.SellerAddress;
import ecommerce.backend.model.seller.SellerApplication;
import ecommerce.backend.model.user.User;
import ecommerce.backend.payload.dtoResponse.UserResponseDTO;
import ecommerce.backend.payload.dtoResponse.category.CategoryResponseDTO;
import ecommerce.backend.payload.dtoResponse.category.SubCategoryResponseDTO;
import ecommerce.backend.payload.dtoResponse.seller.SellerAddressResponseDTO;
import ecommerce.backend.payload.dtoResponse.seller.SellerApplicationResponseDTO;
import ecommerce.backend.payload.dtoResponse.seller.SellerResponseDTO;

public class Mapper {
    public static UserResponseDTO mapToResponseDTO(User user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setUsername(user.getUsername());
        userResponseDTO.setFirstName(user.getFirstName());
        userResponseDTO.setLastName(user.getLastName());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setProfilePicture(user.getProfilePicture());
//        userResponseDTO.setPassword(user.getPassword());
        userResponseDTO.setRole(user.getRole());
        userResponseDTO.setPhoneNumber(user.getPhoneNumber());
        userResponseDTO.setAccountStatus(user.getAccountStatus());
        return userResponseDTO;
    }

    public static SellerApplicationResponseDTO createSellerApplicationResponseDTO(SellerApplication sellerApplication){

        SellerApplicationResponseDTO sellerApplicationResponseDTO=new SellerApplicationResponseDTO();

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

    public static SellerAddressResponseDTO createSellerAddressResponseDTO(
            SellerAddress address
    ) {

        SellerAddressResponseDTO dto =
                new SellerAddressResponseDTO();

        dto.setId(address.getId());
        dto.setSellerId(address.getSeller().getId());
        dto.setAddressLine1(address.getAddressLine1());
        dto.setAddressLine2(address.getAddressLine2());
        dto.setLandmark(address.getLandmark());
        dto.setCity(address.getCity());
        dto.setState(address.getState());
        dto.setCountry(address.getCountry());
        dto.setPostalCode(address.getPostalCode());
        dto.setAddressType(address.getAddressType());
        dto.setIsDefault(address.getIsDefault());
        dto.setCreatedAt(address.getCreatedAt());
        dto.setUpdatedAt(address.getUpdatedAt());

        return dto;
    }

    public static SellerResponseDTO createSellerResponseDTO(Seller seller) {

        SellerResponseDTO dto = new SellerResponseDTO();

        dto.setId(seller.getId());
        dto.setUserId(seller.getUser().getId());
        dto.setStoreName(seller.getStoreName());
        dto.setStoreEmail(seller.getStoreEmail());
        dto.setStorePhoneNumber(seller.getStorePhoneNumber());
        dto.setGstNumber(seller.getGstNumber());
        dto.setPanNumber(seller.getPanNumber());
        dto.setIsActive(seller.getIsActive());
        dto.setCreatedAt(seller.getCreatedAt());
        dto.setUpdatedAt(seller.getUpdatedAt());

        return dto;
    }

    public static CategoryResponseDTO createCategoryResponseDTO(Category category) {
        CategoryResponseDTO response =
                new CategoryResponseDTO();

        response.setId(category.getId());
        response.setName(category.getName());
        response.setDescription(category.getDescription());
        response.setImage(category.getImage());
        response.setStatus(category.getStatus());
        response.setCreatedAt(category.getCreatedAt());
        response.setUpdatedAt(category.getUpdatedAt());

        return response;
    }

    public static SubCategoryResponseDTO createSubCategoryResponseDTO(SubCategory subCategory) {
        SubCategoryResponseDTO response =
                new SubCategoryResponseDTO();

        response.setId(subCategory.getId());
        response.setName(subCategory.getName());
        response.setDescription(subCategory.getDescription());
        response.setImage(subCategory.getImage());
        response.setStatus(subCategory.getStatus());
        response.setCategoryName(subCategory.getCategory().getName());
        response.setCategoryId(subCategory.getCategory().getId());

        return  response;
    }

}
