package com.umaxcode.spring_boot_essentials_with_crud.config;

import com.umaxcode.spring_boot_essentials_with_crud.domain.entity.Role;
import com.umaxcode.spring_boot_essentials_with_crud.domain.entity.User;
import com.umaxcode.spring_boot_essentials_with_crud.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Slf4j
public class ApplicationConfig {

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository,
                                        PasswordEncoder passwordEncoder
    ) {
        return args -> {

            User admin = User.builder()
                    .username("admin")
                    .role(Role.ADMIN)
                    .password(passwordEncoder.encode("admin"))
                    .build();

            User savedAdminUser = userRepository.save(admin);
            log.info("Admin user created: {}", savedAdminUser);
        };
    }
}
