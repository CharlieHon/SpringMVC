package com.charlie.test;

import com.charlie.entity.Monster;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

// 测试jackson包下ObjectMapper类处理json数据
public class JacksonTest {
    public static void main(String[] args) {
        List<Monster> monsters = new ArrayList<>();
        monsters.add(new Monster(100, "黄风怪", "沙尘暴", 500));
        monsters.add(new Monster(200, "黄袍怪", "奎木狼", 600));
        // 把monsters，转成json
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String monsterJson = objectMapper.writeValueAsString(monsters);
            System.out.println("monsterJson=" + monsterJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
