package com.example.patterns.vo.auth;

import lombok.*;

@Data
public class TbUsmUserAccessVo {
    String userName;
    String password;
    String defaultTemplateCode;
    String email;
    String phoneNumber;
    String roles;
    String profilePictureLink;
}
