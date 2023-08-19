package vn.dangdnh.controller.token;

import vn.dangdnh.definition.URIs;
import vn.dangdnh.dto.request.token.JwtTokenRenewRequestCommand;
import vn.dangdnh.dto.request.token.JwtTokenValidationRequestCommand;
import vn.dangdnh.dto.response.JwtToken;
import vn.dangdnh.dto.response.TokenValidationResult;
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
