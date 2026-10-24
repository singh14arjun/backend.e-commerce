package ecommerce.backend.controller.category;
import ecommerce.backend.payload.dtoRequest.category.SubCategoryRequestDTO;
import ecommerce.backend.payload.dtoRequest.category.SubCategoryStatusUpdateRequestDTO;
import ecommerce.backend.payload.dtoResponse.APIResponse;
import ecommerce.backend.payload.dtoResponse.category.SubCategoryResponseDTO;
import ecommerce.backend.service.category.SubCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/subcategorie")
public class SubCategoryController {

    private final SubCategoryService subCategoryService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<APIResponse<SubCategoryResponseDTO>> createSubCategory(
            @Valid @ModelAttribute SubCategoryRequestDTO requestDTO,
            Authentication authentication,
            @RequestParam(value = "image",required = false) MultipartFile image
            )throws IOException {
        String loggedInEmail=authentication.getName();

        APIResponse<SubCategoryResponseDTO> response=subCategoryService.addSubCategory(requestDTO,loggedInEmail,image);

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{subCategoryId}/updateStatus")
    public ResponseEntity<APIResponse<SubCategoryResponseDTO>> updateSubCategoryStatus(
            @PathVariable Long subCategoryId,
            @RequestBody SubCategoryStatusUpdateRequestDTO requestDTO,
            Authentication authentication
            ){
        String loggedInEmail=authentication.getName();

        APIResponse<SubCategoryResponseDTO> response=subCategoryService.updateSubCategoryStatus(subCategoryId,requestDTO,loggedInEmail);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}


