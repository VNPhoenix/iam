package vn.dangdnh.controller.token.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import vn.dangdnh.controller.token.TokenController;
import vn.dangdnh.dto.request.token.JwtTokenRenew;
import vn.dangdnh.dto.request.token.JwtTokenVerification;
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
    public ResponseEntity<TokenVerification> verifyToken(JwtTokenVerification request) {
        TokenVerification response = service.verifyToken(request);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<JwtToken> renewToken(JwtTokenRenew request) {
        JwtToken response = service.renewJwtToken(request);
        return ResponseEntity.ok(response);
    }
}
