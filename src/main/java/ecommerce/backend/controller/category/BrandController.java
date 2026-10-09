package ecommerce.backend.controller.category;

import ecommerce.backend.payload.dtoRequest.brand.BrandRequestDTO;
import ecommerce.backend.payload.dtoResponse.APIResponse;
import ecommerce.backend.payload.dtoResponse.brand.BrandResponseDTO;
import ecommerce.backend.service.brand.BrandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/brand")
public class BrandController {

    private final BrandService brandService;


    @PostMapping (consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<APIResponse<BrandResponseDTO>> createBrand(
            @Valid @ModelAttribute BrandRequestDTO brandRequestDTO,
            Authentication authentication, @RequestParam(
                    value = "logoUrl"
                    , required = false
            ) MultipartFile logoUrl) throws IOException {

        String loggedInEmail = authentication.getName();

        APIResponse<BrandResponseDTO> response=brandService.addNewBrand(brandRequestDTO, loggedInEmail, logoUrl);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }
}
