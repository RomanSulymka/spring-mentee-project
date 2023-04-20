package com.sombra.edu.springmenteeproject.auth.dto;

import com.sombra.edu.springmenteeproject.entity.Balance;
import lombok.*;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String firstname;
    private String lastname;
    private Set<Balance> balance;
    private String email;
    private String password;

}
