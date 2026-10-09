package ecommerce.backend.service.brand;

import ecommerce.backend.payload.dtoRequest.brand.BrandRequestDTO;
import ecommerce.backend.payload.dtoResponse.APIResponse;
import ecommerce.backend.payload.dtoResponse.brand.BrandResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface BrandService {

    APIResponse<BrandResponseDTO> addNewBrand(
            BrandRequestDTO brandRequestDTO,
            String loggedInEmail,
            MultipartFile image) throws IOException;
}
