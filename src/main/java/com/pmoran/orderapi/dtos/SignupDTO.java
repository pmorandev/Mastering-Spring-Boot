package com.pmoran.orderapi.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignupDTO {
    private String username;
    private String password;
}
