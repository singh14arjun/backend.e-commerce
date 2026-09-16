package ecommerce.backend.payload.dtoResponse;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@NotBlank
public class APIResponse<T> {

    private  boolean success;
    private String message;
    private T data;
}
