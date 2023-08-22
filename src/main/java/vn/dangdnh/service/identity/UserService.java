package vn.dangdnh.service.identity;

import org.springframework.validation.annotation.Validated;
import vn.dangdnh.dto.request.user.UserSignInRequest;
import vn.dangdnh.dto.request.user.UserSignUpRequest;
import vn.dangdnh.dto.response.TokenDetails;
import vn.dangdnh.dto.user.UserInfoDto;

import javax.validation.Valid;

@Validated
public interface UserService {

    UserInfoDto signUp(@Valid UserSignUpRequest request);

    TokenDetails signIn(@Valid UserSignInRequest request);
}
