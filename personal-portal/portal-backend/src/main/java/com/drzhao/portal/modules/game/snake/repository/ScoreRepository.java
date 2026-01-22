package com.drzhao.portal.modules.game.snake.repository;

import com.drzhao.portal.modules.game.snake.model.SnakeScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ScoreRepository extends JpaRepository<SnakeScore, Long> {
    
    // 查询某个用户的最高分
    // SQL: SELECT * FROM snake_scores WHERE username = ? ORDER BY score DESC LIMIT 1
    SnakeScore findFirstByUsernameOrderByScoreDesc(String username);

    // 查询全服排行榜 (前10名)
    List<SnakeScore> findTop10ByOrderByScoreDesc();
}