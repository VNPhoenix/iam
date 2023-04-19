package com.vht.client.controller.token.impl;

import com.vht.client.controller.token.TokenController;
import com.vht.client.definition.URIs;
import com.vht.client.dto.request.token.JwtTokenRenewRequestCommand;
import com.vht.client.dto.request.token.JwtTokenValidationRequestCommand;
import com.vht.client.dto.response.JwtToken;
import com.vht.client.dto.response.TokenValidationResult;
import com.vht.client.service.identity.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping(URIs.TOKEN)
public class TokenControllerImpl implements TokenController {

    private final TokenService service;

    @Autowired
    public TokenControllerImpl(TokenService service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<TokenValidationResult> validate(@Valid JwtTokenValidationRequestCommand requestCommand) {
        TokenValidationResult response = service.validateJwtToken(requestCommand);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<JwtToken> renewJwtToken(@Valid JwtTokenRenewRequestCommand requestCommand) {
        JwtToken response = service.renewJwtToken(requestCommand);
        return ResponseEntity.ok(response);
    }
}
