package com.darknash.tickets.configs;

import com.darknash.tickets.entities.User;
import com.darknash.tickets.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserConfig implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public void run(String... args) throws Exception {
        createDefaultUser();
    }

    private void createDefaultUser() {
        User user = new User();
        user.setName("John Doe");
        user.setEmail("john.doe@gmail.com");
        user.setPassword(passwordEncoder.encode("password"));
        userRepository.save(user);
    }
}
