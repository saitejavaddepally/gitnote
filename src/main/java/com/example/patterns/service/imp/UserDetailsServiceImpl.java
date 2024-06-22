package com.example.patterns.service.imp;

import com.example.patterns.model.User;
import com.example.patterns.repository.UserRepository;
import com.example.patterns.vo.auth.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByIdentifier(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username"));

        return new CustomUserDetails(user, "", "", "" );
    }
}
