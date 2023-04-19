package com.vht.client.controller.token;

import com.vht.client.definition.URIs;
import com.vht.client.dto.request.token.JwtTokenRenewRequestCommand;
import com.vht.client.dto.request.token.JwtTokenValidationRequestCommand;
import com.vht.client.dto.response.JwtToken;
import com.vht.client.dto.response.TokenValidationResult;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping(URIs.TOKEN)
public interface TokenController {

    @PostMapping(value = "/validate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<TokenValidationResult> validate(@RequestBody @Valid JwtTokenValidationRequestCommand requestCommand);

    @PostMapping(value = "/renew", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<JwtToken> renewJwtToken(@RequestBody @Valid JwtTokenRenewRequestCommand requestCommand);
}
