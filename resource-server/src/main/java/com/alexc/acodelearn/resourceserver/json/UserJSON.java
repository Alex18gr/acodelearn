package com.alexc.acodelearn.resourceserver.json;

import com.alexc.acodelearn.resourceserver.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserJSON {

    private int id;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;

    public UserJSON(User user) {
        this.id = user.getId();
        this.userName = user.getUserName();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
    }

}
