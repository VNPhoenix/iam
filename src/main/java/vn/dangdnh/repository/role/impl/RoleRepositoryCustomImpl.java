package vn.dangdnh.repository.role.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import vn.dangdnh.model.role.Role;
import vn.dangdnh.repository.role.RoleRepositoryCustom;

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
