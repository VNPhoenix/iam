package vn.dangdnh.controller.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vn.dangdnh.controller.user.UserController;
import vn.dangdnh.dto.request.user.UserSignIn;
import vn.dangdnh.dto.request.user.UserSignUp;
import vn.dangdnh.dto.response.TokenDetails;
import vn.dangdnh.dto.user.UserInfoDto;
import vn.dangdnh.service.UserService;

@RestController
public class UserControllerImpl implements UserController {

    private final UserService service;

    @Autowired
    public UserControllerImpl(final UserService service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<UserInfoDto> signUp(@RequestBody UserSignUp request) {
        UserInfoDto response = service.signUp(request);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<TokenDetails> signIn(@RequestBody UserSignIn request) {
        TokenDetails response = service.signIn(request);
        return ResponseEntity.ok(response);
    }
}
