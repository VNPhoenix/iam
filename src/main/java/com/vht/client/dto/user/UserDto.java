package com.vht.client.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.Set;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    private String username;

    private Set<String> authorities;

    @JsonProperty("is_enabled")
    private Boolean isEnabled;

    @JsonProperty("last_login")
    private ZonedDateTime lastLogin;

    @JsonProperty("created_at")
    private ZonedDateTime createAt;

    @JsonProperty("updated_at")
    private ZonedDateTime updatedAt;
}
