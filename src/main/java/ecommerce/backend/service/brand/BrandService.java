package ecommerce.backend.service.brand;

import ecommerce.backend.constant.BrandStatus;
import ecommerce.backend.payload.dtoRequest.brand.BrandRequestDTO;
import ecommerce.backend.payload.dtoRequest.brand.BrandStatusUpdatedRequestDTO;
import ecommerce.backend.payload.dtoRequest.brand.BrandUpdateRequestDTO;
import ecommerce.backend.payload.dtoResponse.APIResponse;
import ecommerce.backend.payload.dtoResponse.brand.BrandResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BrandService {

    APIResponse<BrandResponseDTO> addNewBrand(
            BrandRequestDTO brandRequestDTO,
            String loggedInEmail,
            MultipartFile image) throws IOException;

    APIResponse<List<BrandResponseDTO>> getAllBrands() ;
    APIResponse<List<BrandResponseDTO>> getAllActiveBrands() ;
    APIResponse<BrandResponseDTO> getBrandById(Long brandId) ;
    APIResponse<BrandResponseDTO> updateBrandStatusByBrandId(Long brandId, BrandStatusUpdatedRequestDTO status,String loggedInEmail) ;
    APIResponse<BrandResponseDTO> updateBrandByBrandId(Long brandId, BrandUpdateRequestDTO brandUpdateRequestDTO,String loggedInEmail) ;
    APIResponse<String> deleteBrandById(Long brandId,String loggedInEmail) ;

}
