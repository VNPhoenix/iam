package vn.dangdnh.controller.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vn.dangdnh.controller.user.UserController;
import vn.dangdnh.dto.request.user.UserSignInRequest;
import vn.dangdnh.dto.request.user.UserSignUpRequest;
import vn.dangdnh.dto.response.TokenDetails;
import vn.dangdnh.dto.user.UserInfoDto;
import vn.dangdnh.service.identity.UserService;

import javax.validation.Valid;

@RestController
public class UserControllerImpl implements UserController {

    private final UserService service;

    @Autowired
    public UserControllerImpl(UserService service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<UserInfoDto> signUp(@RequestBody @Valid UserSignUpRequest request) {
        UserInfoDto response = service.signUp(request);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<TokenDetails> signIn(@RequestBody @Valid UserSignInRequest request) {
        TokenDetails response = service.signIn(request);
        return ResponseEntity.ok(response);
    }
}
