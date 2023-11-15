package vn.dangdnh.service;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import vn.dangdnh.dto.request.user.UserSignIn;
import vn.dangdnh.dto.request.user.UserSignUp;
import vn.dangdnh.dto.response.TokenDetails;
import vn.dangdnh.dto.user.UserInfoDto;

@Validated
public interface UserService {

    UserInfoDto signUp(@Valid UserSignUp request);

    TokenDetails signIn(@Valid UserSignIn request);
}
