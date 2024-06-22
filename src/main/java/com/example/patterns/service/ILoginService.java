package com.example.patterns.service;

import com.example.patterns.vo.auth.TbUsmUserAccessVo;
import com.example.patterns.vo.auth.CustomUserDetails;
import org.springframework.stereotype.Component;

@Component
public interface ILoginService {

    public CustomUserDetails authenticateUser(TbUsmUserAccessVo tbUsmUserAccessVo);
}
