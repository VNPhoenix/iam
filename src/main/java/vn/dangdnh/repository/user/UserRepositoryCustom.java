package vn.dangdnh.repository.user;

import vn.dangdnh.model.user.UserInfo;

import java.util.Optional;

public interface UserRepositoryCustom {

    Optional<UserInfo> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByRole(String role);
}
