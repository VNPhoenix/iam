package com.vht.client.repository.role;

import com.vht.client.model.role.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String>, RoleRepositoryCustom {

}
