package ecommerce.backend.payload.dtoResponse.category;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponseDTO {

    private Long id;

    private String name;

    private String description;

    private String image;

    private Boolean status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
