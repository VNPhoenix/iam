package com.vht.client.repository.role.impl;

import com.vht.client.model.role.Role;
import com.vht.client.repository.role.RoleRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public class RoleRepositoryCustomImpl implements RoleRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public RoleRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public boolean existsByAuthority(String name) {
        Criteria criteria = Criteria.where("role").is(name);
        Query query = new Query(criteria);
        return mongoTemplate.exists(query, Role.class);
    }
}
