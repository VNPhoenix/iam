package com.vht.client.controller.user.impl;

import com.vht.client.controller.user.UserController;
import com.vht.client.definition.URIs;
import com.vht.client.dto.request.user.UserSignInRequest;
import com.vht.client.dto.request.user.UserSignUpRequest;
import com.vht.client.dto.response.TokenDetails;
import com.vht.client.dto.user.UserDto;
import com.vht.client.service.identity.UserService;
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
