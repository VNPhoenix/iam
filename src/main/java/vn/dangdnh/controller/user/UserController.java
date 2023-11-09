package vn.dangdnh.controller.user;

import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.dangdnh.dto.request.user.UserSignInRequest;
import vn.dangdnh.dto.request.user.UserSignUpRequest;
import vn.dangdnh.dto.response.TokenDetails;
import vn.dangdnh.dto.user.UserInfoDto;

@Validated
@RequestMapping("${app.context-path}")
public interface UserController {

    @PostMapping(value = "/auth/signup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UserInfoDto> signUp(@RequestBody @Valid UserSignUpRequest request);

    @PostMapping(value = "/auth/signin", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<TokenDetails> signIn(@RequestBody @Valid UserSignInRequest request);
}
