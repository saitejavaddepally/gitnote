package com.example.patterns.vo.auth;

import com.example.patterns.model.User;
import com.example.patterns.utils.ApplicationConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.HashSet;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomUserDetails implements UserDetails {

    @Getter
    @JsonIgnore
    User user;
    String errorCode;
    String errorDescription;
    String token;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String roles = user.getRoles();

        System.out.println("roles are .. " + roles);
        String[] rolesGranted = roles.split(ApplicationConstants.UNDERSCORE_DELIMITER);
        Collection<SimpleGrantedAuthority> simpleGrantedAuthorities = new HashSet<>();

        for (String role : rolesGranted) {
            simpleGrantedAuthorities.add(new SimpleGrantedAuthority(role));
        }
        System.out.println("Simple Grant authorities are " + simpleGrantedAuthorities);
        return simpleGrantedAuthorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

}
