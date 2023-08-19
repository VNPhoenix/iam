package vn.dangdnh.controller.user.impl;

import vn.dangdnh.controller.user.UserController;
import vn.dangdnh.definition.URIs;
import vn.dangdnh.dto.request.user.UserSignInRequest;
import vn.dangdnh.dto.request.user.UserSignUpRequest;
import vn.dangdnh.dto.response.TokenDetails;
import vn.dangdnh.dto.user.UserDto;
import vn.dangdnh.service.identity.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping(URIs.AUTH)
public class UserControllerImpl implements UserController {

    private final UserService service;

    @Autowired
    public UserControllerImpl(UserService service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<UserDto> signUp(@RequestBody @Valid UserSignUpRequest request) {
        UserDto response = service.signUp(request);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<TokenDetails> signIn(@RequestBody @Valid UserSignInRequest request) {
        TokenDetails response = service.signIn(request);
        return ResponseEntity.ok(response);
    }
}
