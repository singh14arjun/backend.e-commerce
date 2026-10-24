package ecommerce.backend.controller.category;

import ecommerce.backend.payload.dtoRequest.brand.BrandRequestDTO;
import ecommerce.backend.payload.dtoRequest.brand.BrandStatusUpdatedRequestDTO;
import ecommerce.backend.payload.dtoRequest.brand.BrandUpdateRequestDTO;
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
import java.util.List;

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

    @GetMapping()
    public ResponseEntity<APIResponse<List<BrandResponseDTO>>> getAllBrands(){

        APIResponse<List<BrandResponseDTO>> response=brandService.getAllBrands();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/active")
    public ResponseEntity<APIResponse<List<BrandResponseDTO>>> getAllActiveBrands(){

        APIResponse<List<BrandResponseDTO>> response=brandService.getAllActiveBrands();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{brandId}")
    public ResponseEntity<APIResponse<BrandResponseDTO>> getBrandById(
            @PathVariable Long brandId
    ){

        APIResponse<BrandResponseDTO> response=brandService.getBrandById(brandId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{brandId}/updateStatus")
    public ResponseEntity<APIResponse<BrandResponseDTO>> updateBrandStatus(
            @PathVariable Long brandId,
            @RequestBody BrandStatusUpdatedRequestDTO statusUpdatedRequestDTO,
            Authentication authentication
            )
    {

        APIResponse<BrandResponseDTO> response =brandService.updateBrandStatusByBrandId(brandId,statusUpdatedRequestDTO,authentication.getName());

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{brandId}/update")
    public ResponseEntity<APIResponse<BrandResponseDTO>> updateBrand(
            @PathVariable Long brandId,
            @RequestBody BrandUpdateRequestDTO brandUpdatedRequestDTO,
            Authentication authentication
    )
    {

        APIResponse<BrandResponseDTO> response =brandService.updateBrandByBrandId(brandId,brandUpdatedRequestDTO,authentication.getName());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{brandId}/delete")
    public ResponseEntity<APIResponse<String>> deleteBrand(
            @PathVariable Long brandId,
            Authentication authentication
    ){

        APIResponse<String> response=brandService.deleteBrandById(brandId,authentication.getName());
        return ResponseEntity.ok(response);
    }
}
