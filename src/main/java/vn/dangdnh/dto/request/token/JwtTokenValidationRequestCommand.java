package vn.dangdnh.dto.request.token;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@Builder
public class JwtTokenValidationRequestCommand {

    @NotNull(message = "access_token must not be null")
    @JsonProperty("access_token")
    private String accessToken;
}
