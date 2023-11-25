package vn.dangdnh.controller.token;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.dangdnh.dto.request.token.JwtTokenRenew;
import vn.dangdnh.dto.request.token.JwtTokenVerification;
import vn.dangdnh.dto.response.JwtToken;
import vn.dangdnh.dto.response.TokenVerification;

@RequestMapping("${app.context-path}/auth/users/tokens")
public interface TokenController {

    @PostMapping(value = "/verify", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<TokenVerification> verifyToken(@RequestBody JwtTokenVerification request);

    @PostMapping(value = "/renew", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<JwtToken> renewToken(@RequestBody JwtTokenRenew request);
}
