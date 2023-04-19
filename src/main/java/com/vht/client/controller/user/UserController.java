package com.vht.client.controller.user;

import com.vht.client.definition.URIs;
import com.vht.client.dto.request.user.UserSignInRequest;
import com.vht.client.dto.request.user.UserSignUpRequest;
import com.vht.client.dto.response.TokenDetails;
import com.vht.client.dto.user.UserDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping(URIs.AUTH)
public interface UserController {

    @PostMapping(value = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UserDto> signUp(@RequestBody @Valid UserSignUpRequest request);

    @PostMapping(value = "/signin", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<TokenDetails> signIn(@RequestBody @Valid UserSignInRequest request);
}
