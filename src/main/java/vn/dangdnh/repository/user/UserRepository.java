package vn.dangdnh.repository.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import vn.dangdnh.model.user.UserInfo;

public interface UserRepository extends MongoRepository<UserInfo, String>, UserRepositoryCustom {

}
