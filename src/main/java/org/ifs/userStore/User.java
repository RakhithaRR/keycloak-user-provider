package org.ifs.userStore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class User {
    private String id;
    private String username;

    public User(String id, String username) {
        this.id = id;
        this.username = username;

    }


    public String getId() {
        return id;
    }


    public String getUsername() {
        return username;
    }
}
