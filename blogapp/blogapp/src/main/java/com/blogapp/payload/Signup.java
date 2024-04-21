package com.blogapp.payload;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Signup {
    private String name;
    private String email;
    private String username;
    private String password;
    private String userRole;
}
