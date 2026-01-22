package com.drzhao.portal.modules.auth;

import com.drzhao.portal.modules.auth.model.User;
import com.drzhao.portal.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService { // [修复] 实现此接口

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // [新增] 注册逻辑
    public void register(String username, String password, String nickname) {
        // 1. 检查是否重名
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("用户名已存在");
        }
        
        // 2. 创建新用户
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password)); // 必须加密存储
        user.setNickname(nickname != null ? nickname : "New User");
        user.setRole("USER"); // 默认为普通用户
        
        userRepository.save(user);
    }

    // 登录逻辑
    public String login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("用户不存在"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("密码错误");
        }

        return jwtUtil.generateToken(user.getUsername());
    }

    // [修复] 必须实现这个方法，Spring Security 才会停止生成默认密码
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("用户不存在"));
        
        // 将我们的 User 对象转换为 Spring Security 认识的 UserDetails 对象
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                new ArrayList<>() // 暂时不处理复杂权限
        );
    }
}