package vn.dangdnh.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenVerification {

    @JsonProperty("is_valid")
    private Boolean valid;

    private String username;

    private List<String> authorities;

    public TokenVerification(boolean valid) {
        this.valid = valid;
    }
}
