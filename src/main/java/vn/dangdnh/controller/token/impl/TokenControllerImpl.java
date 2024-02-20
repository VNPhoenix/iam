package vn.dangdnh.controller.token.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import vn.dangdnh.controller.token.TokenController;
import vn.dangdnh.dto.request.token.JwtTokenRenewCommand;
import vn.dangdnh.dto.request.token.JwtTokenVerificationCommand;
import vn.dangdnh.dto.response.JwtToken;
import vn.dangdnh.dto.response.TokenVerification;
import vn.dangdnh.service.TokenService;

@RestController
public class TokenControllerImpl implements TokenController {

    private final TokenService service;

    @Autowired
    public TokenControllerImpl(final TokenService service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<TokenVerification> verifyToken(JwtTokenVerificationCommand request) {
        var response = service.verifyToken(request);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<JwtToken> renewToken(JwtTokenRenewCommand request) {
        var response = service.renewJwtToken(request);
        return ResponseEntity.ok(response);
    }
}
