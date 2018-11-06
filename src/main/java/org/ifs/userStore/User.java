package org.ifs.userStore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class User {
    private String id;
    private String username;
    private String email;
    private String fname;
    private String lname;

    public User(String id, String username, String fname, String lname) {
        this.id = id;
        this.username = username;
        this.email = username.toLowerCase() + "ifser@gmail.com";
        this.fname = fname;
        this.lname = lname;

    }


    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() { return email; }

    public String getFirstName() { return fname; }

    public String getLastName() { return lname; }
}
