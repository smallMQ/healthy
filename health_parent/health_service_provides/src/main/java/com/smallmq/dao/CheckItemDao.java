package com.smallmq.dao;

import com.github.pagehelper.Page;
import com.smallmq.entity.PageResult;
import com.smallmq.entity.QueryPageBean;
import com.smallmq.pojo.CheckItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


public interface CheckItemDao {
    public void add(CheckItem checkItem);
    // 定义查询所有数据
    public Page<CheckItem> selectByCondition(String queryString);
    public long findCountByCheckItemId(Integer id);
    public void deleteById(Integer id);
    public void update(CheckItem checkItem);
    public CheckItem findById(Integer id);
    public List<CheckItem> findALl();
}
