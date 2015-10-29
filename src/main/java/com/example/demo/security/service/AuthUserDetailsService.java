package com.example.demo.security.service;

import com.example.demo.data.service.UserService;
import com.example.demo.domain.node.User;
import com.example.demo.security.domain.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Endre Czirbesz <endre@czirbesz.hu>
 */
@Service
public class AuthUserDetailsService implements UserDetailsService {
    private final UserService userService;

    @Autowired
    public AuthUserDetailsService(final UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        final User user = userService.getUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with email=%s was not found", email)));
        return new AuthUser(user);
    }
}
