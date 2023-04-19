package com.vht.client.service.identity;

import com.vht.client.dto.request.user.UserSignInRequest;
import com.vht.client.dto.request.user.UserSignUpRequest;
import com.vht.client.dto.response.TokenDetails;
import com.vht.client.dto.user.UserDto;

public interface UserService {

    UserDto signUp(UserSignUpRequest request);

    TokenDetails signIn(UserSignInRequest request);
}
