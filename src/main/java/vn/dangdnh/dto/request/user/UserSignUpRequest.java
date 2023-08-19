package vn.dangdnh.dto.request.user;

import vn.dangdnh.validation.useraccount.ValidPassword;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class UserSignUpRequest {

    @NotNull(message = "username should not be null!")
    private String username;

    @NotNull(message = "password should not be null!")
    @ValidPassword
    private String password;
}
