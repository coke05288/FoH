package com.hzipyb.userservice.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private String name;
    private String email;
    private Integer age;
    private String gender;
    private String password;
}
