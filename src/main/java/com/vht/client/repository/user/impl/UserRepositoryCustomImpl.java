package com.vht.client.repository.user.impl;

import com.vht.client.model.user.User;
import com.vht.client.repository.user.UserRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public UserRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        Criteria criteria = Criteria.where("username").is(username);
        Query query = new Query(criteria);
        User item = mongoTemplate.findOne(query, User.class);
        return Optional.ofNullable(item);
    }

    @Override
    public boolean existsByUsername(String username) {
        Criteria criteria = Criteria.where("username").is(username);
        Query query = new Query(criteria);
        return mongoTemplate.exists(query, User.class);
    }

    @Override
    public boolean existsByRole(String role) {
        Criteria criteria = Criteria.where("authorities.role").is(role);
        Query query = new Query(criteria);
        return mongoTemplate.exists(query, User.class);
    }
}
