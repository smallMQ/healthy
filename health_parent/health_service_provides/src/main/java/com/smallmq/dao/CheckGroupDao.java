package com.smallmq.dao;

import com.github.pagehelper.Page;
import com.smallmq.pojo.CheckGroup;

import java.util.List;
import java.util.Map;

public interface CheckGroupDao {
    // 增加一个检查组
    public void add(CheckGroup checkGroup);
    // 增加IDS
    public void addCheckGroupAndCheckItem(Map<String, Integer> map);
    // 查询分页数据
    public Page<CheckGroup> findPage(String queryString);

    // 根据id查询
    public CheckGroup findById(Integer id);

    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    public void deleteCheckGroupAndCheckItemByCheckGroupId(Integer id);

    public void edit(CheckGroup checkGroup);

    // 查询所有
    public List<CheckGroup> findAll();
}
