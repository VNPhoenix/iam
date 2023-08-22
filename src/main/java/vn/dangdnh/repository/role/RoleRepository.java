package vn.dangdnh.repository.role;

import org.springframework.data.mongodb.repository.MongoRepository;
import vn.dangdnh.model.role.Role;

public interface RoleRepository extends MongoRepository<Role, String>, RoleRepositoryCustom {

}
