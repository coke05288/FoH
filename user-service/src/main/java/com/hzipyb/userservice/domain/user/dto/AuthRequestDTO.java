package com.hzipyb.userservice.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequestDTO {
    private String email;
    private String password;
}
