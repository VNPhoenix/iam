package vn.dangdnh.service;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import vn.dangdnh.dto.request.token.JwtTokenRenewCommand;
import vn.dangdnh.dto.request.token.JwtTokenVerificationCommand;
import vn.dangdnh.dto.response.JwtToken;
import vn.dangdnh.dto.response.TokenVerification;

@Validated
public interface TokenService {

    TokenVerification verifyToken(@Valid JwtTokenVerificationCommand requestCommand);

    JwtToken renewJwtToken(@Valid JwtTokenRenewCommand requestCommand);
}
