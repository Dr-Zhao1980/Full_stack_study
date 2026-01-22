package com.drzhao.portal.modules.game.snake.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "snake_scores")
@Data
public class SnakeScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username; // 记录是谁玩的
    private Integer score;   // 分数
    private Integer durationSeconds; // 坚持了多少秒

    @Column(name = "played_at")
    private LocalDateTime playedAt = LocalDateTime.now();
}