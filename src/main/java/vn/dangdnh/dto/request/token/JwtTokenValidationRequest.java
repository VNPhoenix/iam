package vn.dangdnh.dto.request.token;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@Builder
public class JwtTokenValidationRequest {

    @JsonProperty("access_token")
    @NotNull(message = "access_token must not be null")
    private String accessToken;
}
