package ecommerce.backend.payload.dtoRequest.product;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class ProductCreateRequestDTO {

    @NotNull
    private Long subCategoryId;

    @NotNull
    private Long brandId;

    @NotBlank(message = "Product title is required")
    @Size(min = 2, max = 200)
    private String productTitle;

    @NotBlank(message = "Product slug is required")
    @Size(max = 250)
    private String slug;

    @Size(max = 500)
    private String shortDescription;

    private String description;
    private Boolean isWishlisted =false;


    private Boolean featured = false;

    private List<ProductAttributeRequestDTO> attributes;

    private List<ProductOptionRequestDTO> options;


    private List<ProductVariantRequestDTO> variants;
}
