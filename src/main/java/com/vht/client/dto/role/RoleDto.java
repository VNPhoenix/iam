package com.vht.client.dto.role;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleDto {

    private String id;

    @NotNull(message = "role should not be null!")
    private String name;
}
