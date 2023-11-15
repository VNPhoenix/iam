package vn.dangdnh.repository.user.impl;

import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import vn.dangdnh.model.user.UserInfo;
import vn.dangdnh.repository.user.UserRepositoryCustom;

import java.util.Date;
import java.util.Optional;

@Repository
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public UserRepositoryCustomImpl(final MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Optional<UserInfo> findByUsername(String username) {
        Criteria criteria = Criteria.where("username").is(username);
        Query query = new Query(criteria);
        UserInfo item = mongoTemplate.findOne(query, UserInfo.class);
        return Optional.ofNullable(item);
    }

    @Override
    public boolean existsByUsername(String username) {
        Criteria criteria = Criteria.where("username").is(username);
        Query query = new Query(criteria);
        return mongoTemplate.exists(query, UserInfo.class);
    }

    @Override
    public boolean existsByRole(String role) {
        Criteria criteria = Criteria.where("authorities.role").is(role);
        Query query = new Query(criteria);
        return mongoTemplate.exists(query, UserInfo.class);
    }

    @Override
    public UpdateResult findAndUpdateLastLoginByUsername(String username, Date date) {
        Criteria criteria = Criteria.where("username").is(username);
        Query query = new Query(criteria);
        Update update = new Update()
                .set("last_login", date);
        return mongoTemplate.updateFirst(query, update, UserInfo.class);
    }
}
