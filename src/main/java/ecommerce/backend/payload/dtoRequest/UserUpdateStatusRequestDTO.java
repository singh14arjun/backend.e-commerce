package ecommerce.backend.payload.dtoRequest;

import ecommerce.backend.constant.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateStatusRequestDTO {

    private UserStatus status;
}
