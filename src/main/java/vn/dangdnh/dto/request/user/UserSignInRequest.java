package vn.dangdnh.dto.request.user;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSignInRequest {

    @NotNull(message = "username must not be null")
    private String username;

    @NotNull(message = "password must not be null")
    private String password;
}
