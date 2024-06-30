package com.example.patterns.vo.auth;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class TbUsmUserAccessVo {
    String userName;
    String password;
    String defaultTemplateCode;
    String email;
    String phoneNumber;
    String roles;
    String profilePictureLink;
}
