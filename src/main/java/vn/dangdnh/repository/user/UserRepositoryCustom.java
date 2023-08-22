package vn.dangdnh.repository.user;

import com.mongodb.client.result.UpdateResult;
import vn.dangdnh.model.user.UserInfo;

import java.util.Date;
import java.util.Optional;

public interface UserRepositoryCustom {

    Optional<UserInfo> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByRole(String role);

    UpdateResult updateLastLoginByUsername(String username, Date date);
}
