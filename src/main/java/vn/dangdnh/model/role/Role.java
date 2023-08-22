package vn.dangdnh.model.role;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import vn.dangdnh.definition.CollectionNames;

@Data
@NoArgsConstructor
@Document(collection = CollectionNames.ROLES)
public class Role {

    @Id
    private String name;
}
