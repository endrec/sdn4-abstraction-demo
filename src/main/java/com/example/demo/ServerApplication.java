package com.example.demo;

import com.example.demo.data.repository.UserRepository;
import com.example.demo.domain.UserRole;
import com.example.demo.domain.node.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.EnumSet;

@SpringBootApplication
@RestController
@Slf4j
public class ServerApplication {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    public static void main(final String[] args) {
        new SpringApplicationBuilder(ServerApplication.class).run(args);
    }

    @RequestMapping("/test")
    public void test(final Principal principal) {
        log.debug("Principal: {}", principal);
    }

    @Transactional
    @RequestMapping("/create-user")
    public void createUser() {
        User user = new User()
                .setEmail("test@example.com")
                .setPassword(encoder.encode("1234"))
                .setRoles(EnumSet.allOf(UserRole.class));
        userRepository.save(user);
    }


}
