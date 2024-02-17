package com.charlie.service.impl;

import com.charlie.entity.Monster;
import com.charlie.hspspringmvc.annotation.Service;
import com.charlie.service.MonsterService;

import java.util.ArrayList;
import java.util.List;

// MonsterServiceImpl 对象会作为service注入到spring容器
@Service
public class MonsterServiceImpl implements MonsterService {
    @Override
    public List<Monster> listMonster() {
        List<Monster> monsters = new ArrayList<>();
        monsters.add(new Monster(100, "黄风怪", "沙尘暴", 500));
        monsters.add(new Monster(200, "黄袍怪", "奎木狼", 600));
        return monsters;
    }

    // 根据关键字返回monster列表
    @Override
    public List<Monster> findMonsterByName(String name) {
        List<Monster> monsters = new ArrayList<>();
        monsters.add(new Monster(100, "黄风怪", "沙尘暴", 500));
        monsters.add(new Monster(200, "黄袍怪", "奎木狼", 600));
        monsters.add(new Monster(300, "黑熊精", "偷袈裟", 36));
        monsters.add(new Monster(400, "白骨精", "白骨迷惑", 168));
        monsters.add(new Monster(500, "老鼠精", "钻心爪", 136));

        // 创建集合返回查询到的monster集合
        List<Monster> findMonsters = new ArrayList<>();
        for (Monster monster : monsters) {
            if (monster.getName().contains(name)) {
                findMonsters.add(monster);
            }
        }
        return findMonsters;
    }

    @Override
    public boolean login(String name) {
        return "白骨精".equals(name);
    }
}
