package vn.dangdnh.controller.token;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.dangdnh.dto.request.token.JwtTokenRenewRequest;
import vn.dangdnh.dto.request.token.JwtTokenValidationRequest;
import vn.dangdnh.dto.response.JwtToken;
import vn.dangdnh.dto.response.TokenValidationResult;

import javax.validation.Valid;

@Validated
@RequestMapping("${app.base-url}")
public interface TokenController {

    @PostMapping(value = "/auth/users/tokens/validate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<TokenValidationResult> validateJwtToken(@RequestBody @Valid JwtTokenValidationRequest request);

    @PostMapping(value = "/auth/users/tokens/renew", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<JwtToken> renewJwtToken(@RequestBody @Valid JwtTokenRenewRequest request);
}
