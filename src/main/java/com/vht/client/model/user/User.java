package com.vht.client.model.user;

import com.vht.client.definition.CollectionNames;
import com.vht.client.definition.CryptoAlgorithm;
import com.vht.client.dto.user.UserDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
@Document(collection = CollectionNames.USERS)
public class User {

    @Id
    private String id;

    @Indexed(unique = true)
    private String username = StringUtils.EMPTY;

    private String password = StringUtils.EMPTY;

    private Set<String> authorities = new HashSet<>();

    @Field("is_account_non_expired")
    private Boolean isAccountNonExpired = false;

    @Field("is_account_non_locked")
    private Boolean isAccountNonLocked = false;

    @Field("is_credentials_non_expired")
    private Boolean isCredentialsNonExpired = false;

    @Field("is_enabled")
    private Boolean isEnabled = false;

    @Field("crypto_algorithm")
    private String cryptoAlgorithm = CryptoAlgorithm.BCRYPT;

    @Field("last_login")
    private ZonedDateTime lastLogin;

    @Field("created_at")
    private ZonedDateTime createdAt;

    @Field("updated_at")
    private ZonedDateTime updatedAt;

    public static UserDto mapToDto(User o) {
        if (Objects.isNull(o)) {
            return null;
        }
        return o.toDto();
    }

    public UserDto toDto() {
        return UserDto.builder()
                .username(this.username)
                .authorities(this.authorities)
                .isEnabled(this.isEnabled)
                .lastLogin(this.lastLogin)
                .createAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }
}
