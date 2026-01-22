package com.drzhao.portal.infrastructure.config;

import com.drzhao.portal.modules.auth.model.User;
import com.drzhao.portal.modules.auth.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // 如果用户表是空的，就创建一个 admin
            if (userRepository.count() == 0) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setNickname("Dr.Zhao");
                admin.setPassword(passwordEncoder.encode("123456")); // 密码: 123456
                admin.setRole("ADMIN");
                userRepository.save(admin);
                System.out.println(">>> 🟢 初始化完成: 已创建默认用户 admin / 123456");
            }
        };
    }
}