package vn.dangdnh.dto.request.token;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class JwtTokenRenewRequest {

    @JsonProperty("refresh_token")
    @NotNull(message = "refresh_token must not be null")
    private String refreshToken;
}
