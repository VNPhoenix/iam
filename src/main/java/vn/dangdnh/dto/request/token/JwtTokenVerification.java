package vn.dangdnh.dto.request.token;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtTokenVerification {

    @JsonProperty("access_token")
    @NotNull(message = "access_token must not be null")
    private String accessToken;
}
