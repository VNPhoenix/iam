package vn.dangdnh.model.role;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import vn.dangdnh.definition.MongoCollections;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@Document(collection = MongoCollections.ROLES)
public class Role {

    @Id
    private String name = "";

    private Boolean deletable = true;
}
