package vn.dangdnh.repository.role.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import vn.dangdnh.repository.role.RoleRepositoryCustom;

@Repository
public class RoleRepositoryCustomImpl implements RoleRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public RoleRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
}
