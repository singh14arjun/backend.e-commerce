package ecommerce.backend.payload.dtoResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdNameResponseDTO {

    private Long id;
    private String name;
}
