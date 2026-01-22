// package com.example.demo.controller;

// import com.example.demo.model.MoodRecord;
// import com.example.demo.utils.FileService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.*;
// import org.springframework.web.client.RestTemplate;

// import java.time.LocalDateTime;
// import java.time.format.DateTimeFormatter;
// import java.util.Map;

// @RestController
// @RequestMapping("/api")
// @CrossOrigin(origins = "*") // 允许 Vue (localhost:5173) 访问 Java (localhost:8080)
// public class MoodController {

//     @Autowired
//     private FileService fileService;

//     @PostMapping("/mood")
//     public MoodRecord submitMood(@RequestBody Map<String, String> payload) {
//         String username = payload.get("username");
//         String text = payload.get("text");

//         System.out.println("收到前端请求: " + text);

//         // 1. 呼叫 Python 小弟 (FastAPI)
//         RestTemplate restTemplate = new RestTemplate();
//         String pythonUrl = "http://localhost:8000/analyze";
        
//         // 准备发给 Python 的数据
//         Map<String, String> pythonPayload = Map.of("text", text);
        
//         // 拿到 Python 的结果 (这里是下划线命名 snake_case)
//         Map aiResult = restTemplate.postForObject(pythonUrl, pythonPayload, Map.class);

//         // 2. 数据转换与封装 (关键步骤！)
//         // Python给的是 lucky_color, 我们要存成 luckyColor
//         String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
//         MoodRecord newRecord = new MoodRecord(
//             now,
//             text,
//             (Integer) aiResult.get("score"),           // 拿 Python 的 score
//             (String) aiResult.get("lucky_color"),      // 拿 Python 的 lucky_color
//             (String) aiResult.get("ai_comment")        // 拿 Python 的 ai_comment
//         );

//         // 3. 保存到硬盘
//         fileService.saveRecord(username, newRecord);

//         // 4. 返回给 Vue (Vue 会收到驼峰命名的 JSON)
//         return newRecord;
//     }
// }

package com.example.demo.controller;

import com.example.demo.model.MoodRecord;
import com.example.demo.utils.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List; // <--- 必须导入 List
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") 
public class MoodController {

    @Autowired
    private FileService fileService;

    // 👇👇👇 之前缺失的登录接口 👇👇👇
    @GetMapping("/login")
    public List<MoodRecord> login(@RequestParam String username) {
        System.out.println("👤 用户登录: " + username);
        // 调用 FileService 去读硬盘上的 json 文件
        return fileService.readRecords(username);
    }
    // 👆👆👆 补上这一段 👆👆👆

    @PostMapping("/mood")
    public MoodRecord submitMood(@RequestBody Map<String, String> payload) {
        String username = payload.get("username");
        String text = payload.get("text");

        System.out.println("收到前端请求: " + text);

        // 1. 呼叫 Python 小弟 (FastAPI)
        RestTemplate restTemplate = new RestTemplate();
        String pythonUrl = "http://localhost:8000/analyze";
        
        // 准备发给 Python 的数据
        Map<String, String> pythonPayload = Map.of("text", text);
        
        // 拿到 Python 的结果
        Map aiResult = restTemplate.postForObject(pythonUrl, pythonPayload, Map.class);

        // 2. 数据转换与封装
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        MoodRecord newRecord = new MoodRecord(
            now,
            text,
            (Integer) aiResult.get("score"),           
            (String) aiResult.get("lucky_color"),      
            (String) aiResult.get("ai_comment")        
        );

        // 3. 保存到硬盘
        fileService.saveRecord(username, newRecord);

        // 4. 返回给 Vue
        return newRecord;
    }
}