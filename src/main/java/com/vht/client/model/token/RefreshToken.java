package com.vht.client.model.token;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
public class RefreshToken {

    @Id
    private String id;

    private String username;

    private String token;
}
