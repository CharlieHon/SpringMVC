package com.charlie.service;

import com.charlie.entity.Monster;

import java.util.List;

public interface MonsterService {
    // 增加方法，可以返回monster列表
    public List<Monster> listMonster();

    // 通过传入的name，返回monster
    public List<Monster> findMonsterByName(String name);
}
