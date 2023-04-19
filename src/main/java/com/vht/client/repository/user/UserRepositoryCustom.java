package com.vht.client.repository.user;

import com.vht.client.model.user.User;

import java.util.Optional;

public interface UserRepositoryCustom {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByRole(String role);
}
