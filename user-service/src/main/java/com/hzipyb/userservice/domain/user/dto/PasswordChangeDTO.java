package com.hzipyb.userservice.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordChangeDTO {
    private String oldPassword;
    private String newPassword;
}
