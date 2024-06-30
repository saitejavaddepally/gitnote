package com.example.patterns.controlller;

import com.example.patterns.vo.auth.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/")
public class SecurityTestController {


    @GetMapping("test/request")
    public String get(@AuthenticationPrincipal CustomUserDetails principal){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getPrincipal());
        System.out.println("Authorities are " + authentication.getAuthorities());
        System.out.println("Principal Object is " + principal.getAuthorities());

        return "HELLO this request is secured";
    }

}
