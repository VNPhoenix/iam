package vn.dangdnh.dto.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoleCreateCommand {

    @JsonProperty("role_name")
    @NotNull(message = "role_name should not be null")
    private String roleName;
}
