package vn.dangdnh.service;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import vn.dangdnh.dto.request.token.JwtTokenRenew;
import vn.dangdnh.dto.request.token.JwtTokenVerification;
import vn.dangdnh.dto.response.JwtToken;
import vn.dangdnh.dto.response.TokenVerification;

@Validated
public interface TokenService {

    TokenVerification verifyToken(@Valid JwtTokenVerification requestCommand);

    JwtToken renewJwtToken(@Valid JwtTokenRenew requestCommand);
}