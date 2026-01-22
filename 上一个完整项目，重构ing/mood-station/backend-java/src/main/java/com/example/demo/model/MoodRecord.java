package com.example.demo.model;

// 这是一个数据模具，对应 Vue 里的 aiResult
public class MoodRecord {
    public String date;       // 记录时间
    public String text;       // 你的心情文本
    public int score;         // 心情得分
    
    // 注意：这里必须用驼峰命名，为了配合 Vue 的 {{ aiResult.luckyColor }}
    public String luckyColor; 
    public String aiComment;  

    // 空构造函数 (必须有，不然报错)
    public MoodRecord() {}

    public MoodRecord(String date, String text, int score, String luckyColor, String aiComment) {
        this.date = date;
        this.text = text;
        this.score = score;
        this.luckyColor = luckyColor;
        this.aiComment = aiComment;
    }
}