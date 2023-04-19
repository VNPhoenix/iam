package com.vht.client.model.role;

import com.vht.client.definition.CollectionNames;
import com.vht.client.dto.role.RoleDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = CollectionNames.ROLES)
public class Role {

    @Id
    private String id;

    @Indexed(unique = true)
    private String name;

    public RoleDto toDto() {
        return RoleDto.builder()
                .id(this.id)
                .name(this.name)
                .build();
    }
}
