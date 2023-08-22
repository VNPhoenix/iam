package vn.dangdnh.controller.token.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import vn.dangdnh.controller.token.TokenController;
import vn.dangdnh.dto.request.token.JwtTokenRenewRequest;
import vn.dangdnh.dto.request.token.JwtTokenValidationRequest;
import vn.dangdnh.dto.response.JwtToken;
import vn.dangdnh.dto.response.TokenValidationResult;
import vn.dangdnh.service.identity.TokenService;

import javax.validation.Valid;

@RestController
public class TokenControllerImpl implements TokenController {

    private final TokenService service;

    @Autowired
    public TokenControllerImpl(TokenService service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<TokenValidationResult> validateJwtToken(@Valid JwtTokenValidationRequest request) {
        TokenValidationResult response = service.validateJwtToken(request);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<JwtToken> renewJwtToken(@Valid JwtTokenRenewRequest request) {
        JwtToken response = service.renewJwtToken(request);
        return ResponseEntity.ok(response);
    }
}
