package com.drzhao.portal.modules.auth;

import com.drzhao.portal.common.Result;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // 登录接口
    @PostMapping("/login")
    public Result<Map<String, String>> login(@RequestBody LoginRequest request) {
        // 简单参数校验
        if (request.getUsername() == null || request.getPassword() == null) {
            return Result.error("账号密码不能为空");
        }
        try {
            String token = authService.login(request.getUsername(), request.getPassword());
            
            Map<String, String> data = new HashMap<>();
            data.put("token", token);
            data.put("username", request.getUsername());
            
            return Result.success(data);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    // [新增] 注册接口
    @PostMapping("/register")
    public Result<String> register(@RequestBody RegisterRequest request) {
        if (request.getUsername() == null || request.getPassword() == null) {
            return Result.error("账号密码不能为空");
        }
        try {
            authService.register(request.getUsername(), request.getPassword(), request.getNickname());
            return Result.success("注册成功，请登录");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @Data
    static class LoginRequest {
        private String username;
        private String password;
    }

    // [新增] 注册请求体
    @Data
    static class RegisterRequest {
        private String username;
        private String password;
        private String nickname;
    }
}