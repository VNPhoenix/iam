package vn.dangdnh.service.identity;

import vn.dangdnh.dto.request.user.UserSignInRequest;
import vn.dangdnh.dto.request.user.UserSignUpRequest;
import vn.dangdnh.dto.response.TokenDetails;
import vn.dangdnh.dto.user.UserDto;

public interface UserService {

    UserDto signUp(UserSignUpRequest request);

    TokenDetails signIn(UserSignInRequest request);
}
