package vn.dangdnh.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JwtToken {

    @JsonProperty("jwt_token")
    private String jwtToken;
}
