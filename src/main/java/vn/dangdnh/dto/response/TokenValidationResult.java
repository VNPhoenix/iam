package vn.dangdnh.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenValidationResult {

    @JsonProperty("is_valid")
    private boolean valid;

    private String username;

    private Set<String> authorities;
}
