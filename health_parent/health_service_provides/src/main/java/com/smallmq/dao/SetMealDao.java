package com.smallmq.dao;

import com.github.pagehelper.Page;
import com.smallmq.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetMealDao {
    public void add(Setmeal setmeal);

    public void addSetMealAndCheckGroup(Map<String,Integer> map);

    public Page<Setmeal> findPage(String queryString);

    public List<Setmeal> findAll();

    public Setmeal findById(Integer id);
 }


