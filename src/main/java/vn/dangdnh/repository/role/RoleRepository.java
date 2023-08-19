package vn.dangdnh.repository.role;

import vn.dangdnh.model.role.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String>, RoleRepositoryCustom {

}
