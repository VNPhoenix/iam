package vn.dangdnh.dto.request.user;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import vn.dangdnh.validation.useraccount.ValidPassword;

@Getter
@NoArgsConstructor
public class UserSignUpRequest {

    @NotNull(message = "username should not be null")
    private String username;

    @ValidPassword
    @NotNull(message = "password should not be null")
    private String password;
}
