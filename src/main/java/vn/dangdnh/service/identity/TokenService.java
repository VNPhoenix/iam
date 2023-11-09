package vn.dangdnh.service.identity;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import vn.dangdnh.dto.request.token.JwtTokenRenewRequest;
import vn.dangdnh.dto.request.token.JwtTokenValidationRequest;
import vn.dangdnh.dto.response.JwtToken;
import vn.dangdnh.dto.response.TokenValidationResult;

@Validated
public interface TokenService {

    TokenValidationResult validateJwtToken(@Valid JwtTokenValidationRequest requestCommand);

    JwtToken renewJwtToken(@Valid JwtTokenRenewRequest requestCommand);
}
