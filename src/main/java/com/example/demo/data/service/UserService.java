package com.example.demo.data.service;

import com.example.demo.data.repository.UserRepository;
import com.example.demo.domain.node.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Endre Czirbesz <endre@czirbesz.hu>
 */
@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Optional<User> getUserByEmail(final String email) {
        return Optional.ofNullable(userRepository.findByEmail(email));
    }

}
