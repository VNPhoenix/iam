package vn.dangdnh.service.identity;

import vn.dangdnh.dto.request.token.JwtTokenRenewRequestCommand;
import vn.dangdnh.dto.request.token.JwtTokenValidationRequestCommand;
import vn.dangdnh.dto.response.JwtToken;
import vn.dangdnh.dto.response.TokenValidationResult;

import javax.validation.Valid;

public interface TokenService {

    TokenValidationResult validateJwtToken(@Valid JwtTokenValidationRequestCommand requestCommand);

    JwtToken renewJwtToken(@Valid JwtTokenRenewRequestCommand requestCommand);
}
