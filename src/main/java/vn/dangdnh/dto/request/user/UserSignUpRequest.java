package vn.dangdnh.dto.request.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import vn.dangdnh.validation.useraccount.ValidPassword;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class UserSignUpRequest {

    @NotNull(message = "username should not be null")
    private String username;

    @ValidPassword
    @NotNull(message = "password should not be null")
    private String password;
}
