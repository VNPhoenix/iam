package com.vht.client.service.identity;

import com.vht.client.dto.request.token.JwtTokenRenewRequestCommand;
import com.vht.client.dto.request.token.JwtTokenValidationRequestCommand;
import com.vht.client.dto.response.JwtToken;
import com.vht.client.dto.response.TokenValidationResult;

import javax.validation.Valid;

public interface TokenService {

    TokenValidationResult validateJwtToken(@Valid JwtTokenValidationRequestCommand requestCommand);

    JwtToken renewJwtToken(@Valid JwtTokenRenewRequestCommand requestCommand);
}
