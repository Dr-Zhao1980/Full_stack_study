package com.drzhao.portal.modules.game.snake.controller;

import com.drzhao.portal.common.Result;
import com.drzhao.portal.modules.game.snake.model.SnakeScore;
import com.drzhao.portal.modules.game.snake.service.SnakeService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/game/snake")
@RequiredArgsConstructor
public class SnakeController {

    private final SnakeService snakeService;

    // 提交分数
    // POST /api/game/snake/score
    @PostMapping("/score")
    public Result<String> submitScore(@RequestBody ScoreRequest request) {
        // 从 SecurityContext 获取当前登录的用户名 (JWT 解析出来的)
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); 

        snakeService.submitScore(username, request.getScore(), request.getDuration());
        return Result.success("分数上传成功");
    }

    // 获取排行榜
    // GET /api/game/snake/leaderboard
    @GetMapping("/leaderboard")
    public Result<List<SnakeScore>> getLeaderboard() {
        return Result.success(snakeService.getLeaderboard());
    }

    @Data
    static class ScoreRequest {
        private Integer score;
        private Integer duration;
    }
}