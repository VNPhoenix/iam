package vn.dangdnh.controller.user;

import vn.dangdnh.definition.URIs;
import vn.dangdnh.dto.request.user.UserSignInRequest;
import vn.dangdnh.dto.request.user.UserSignUpRequest;
import vn.dangdnh.dto.response.TokenDetails;
import vn.dangdnh.dto.user.UserDto;
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
