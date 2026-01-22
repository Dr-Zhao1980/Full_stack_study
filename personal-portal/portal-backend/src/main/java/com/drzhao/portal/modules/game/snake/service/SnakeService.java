package com.drzhao.portal.modules.game.snake.service;

import com.drzhao.portal.modules.game.snake.model.SnakeScore;
import com.drzhao.portal.modules.game.snake.repository.ScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SnakeService {

    private final ScoreRepository scoreRepository;

    // 提交分数
    public void submitScore(String username, Integer score, Integer duration) {
        // 这里可以加防作弊逻辑，比如检查 score 和 duration 的比例是否合理
        SnakeScore record = new SnakeScore();
        record.setUsername(username);
        record.setScore(score);
        record.setDurationSeconds(duration);
        scoreRepository.save(record);
    }

    // 获取排行榜
    public List<SnakeScore> getLeaderboard() {
        return scoreRepository.findTop10ByOrderByScoreDesc();
    }
}