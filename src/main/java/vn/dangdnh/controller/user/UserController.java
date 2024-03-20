package vn.dangdnh.controller.user;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.dangdnh.dto.request.user.UserSignIn;
import vn.dangdnh.dto.request.user.UserSignUp;
import vn.dangdnh.dto.response.TokenDetails;
import vn.dangdnh.dto.user.UserInfoDto;

@RequestMapping("${app.context-path}")
public interface UserController {

    @PostMapping(value = "/auth/signup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UserInfoDto> signUp(@RequestBody UserSignUp request);

    @PostMapping(value = "/auth/signin", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<TokenDetails> signIn(@RequestBody UserSignIn request);
}
