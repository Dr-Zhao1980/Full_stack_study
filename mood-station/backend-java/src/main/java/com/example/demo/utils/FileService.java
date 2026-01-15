package com.example.demo.utils;

import com.example.demo.model.MoodRecord;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service // 标记这是一个服务
public class FileService {

    private final String DATA_DIR = "data";
    private final ObjectMapper mapper = new ObjectMapper(); // JSON 转换工具

    public FileService() {
        // 启动时检查 data 文件夹是否存在，没有就创建
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    private File getUserFile(String username) {
        return new File(DATA_DIR + "/" + username + ".json");
    }

    // 保存记录
    public void saveRecord(String username, MoodRecord record) {
        List<MoodRecord> history = readRecords(username);
        history.add(record);
        try {
            // 写入文件 (PrettyPrinter 让 JSON 格式化好看)
            mapper.writerWithDefaultPrettyPrinter().writeValue(getUserFile(username), history);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 读取记录
    public List<MoodRecord> readRecords(String username) {
        File file = getUserFile(username);
        if (!file.exists()) return new ArrayList<>();
        try {
            return mapper.readValue(file, new TypeReference<List<MoodRecord>>() {});
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
}