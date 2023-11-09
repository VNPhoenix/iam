package vn.dangdnh.model.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import vn.dangdnh.definition.CollectionNames;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Accessors(chain = true, fluent = true)
@Document(collection = CollectionNames.USERS)
public class UserInfo {

    @Id
    private String id;

    @Indexed(unique = true)
    private String username;

    private String password;

    private List<String> authorities;

    @Field("is_account_non_expired")
    private Boolean isAccountNonExpired;

    @Field("is_account_non_locked")
    private Boolean isAccountNonLocked;

    @Field("is_credentials_non_expired")
    private Boolean isCredentialsNonExpired;

    @Field("is_enabled")
    private Boolean isEnabled;

    @Field("crypto_algorithm")
    private String cryptoAlgorithm;

    @Field("last_login")
    private Date lastLogin;

    @Field("created_at")
    private Date createdAt;

    @Field("updated_at")
    private Date updatedAt;
}
