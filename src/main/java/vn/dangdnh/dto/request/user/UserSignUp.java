package vn.dangdnh.dto.request.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserSignUp {

    @NotNull(message = "username should not be null")
    private String username;

    @NotNull(message = "password should not be null")
    @Size(min = 8, message = "length of password should be greater than equal to 8")
    private String password;
}
