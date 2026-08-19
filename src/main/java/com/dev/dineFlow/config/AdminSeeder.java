package com.dev.dineFlow.config;

import com.dev.dineFlow.entity.User;
import com.dev.dineFlow.entity.enums.UserRoleEnums;
import com.dev.dineFlow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminSeeder implements CommandLineRunner
{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.email}")
    private String adminEmail;

    @Value("${app.admin.password}")
    private String adminPassword;

    @Override
    public void run(String... args) throws Exception
    {
        boolean adminExists = userRepository.existsByUserRole(UserRoleEnums.ADMIN);


        if (!adminExists)
        {
            User admin = new User();
            admin.setFullName("Default Admin");
            admin.setEmail(adminEmail);
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setUserRole(UserRoleEnums.ADMIN);
            admin.setEnabled(true);

            userRepository.save(admin);
        }
    }
}
