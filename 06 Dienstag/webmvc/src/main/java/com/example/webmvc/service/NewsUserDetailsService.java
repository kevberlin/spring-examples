package com.example.webmvc.service;

import com.example.webmvc.model.NewsUser;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class NewsUserDetailsService implements UserDetailsService {

    private NewsUserRepository userRepository;

    public NewsUserDetailsService(NewsUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        NewsUser newsUser =
                userRepository.findByUsername(username);
        return new User(
                newsUser.getUsername(),
                newsUser.getPassword(),
                new ArrayList<>());
    }
}
