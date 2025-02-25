package com.project.shop.config.security.payload;

import com.project.shop.entity.Role;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {

    private String username;
    private String firstname;
    private String email;
    private String password;
    private Role role;

}
