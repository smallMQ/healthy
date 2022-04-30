package com.smallmq.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.smallmq.dao.CheckGroupDao;
import com.smallmq.entity.PageResult;
import com.smallmq.entity.QueryPageBean;
import com.smallmq.pojo.CheckGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import smallmq.service.CheckGroupService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupDao checkGroupDao;

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {

        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        PageHelper pageHelper = new PageHelper();
        pageHelper.startPage(currentPage,pageSize);
        Page<CheckGroup> page = checkGroupDao.findPage(queryString);
        return new PageResult(page.getTotal(),page.getResult());


    }

    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }

    @Override
    public void edit(CheckGroup checkGroup, Integer[] checkitemIds) {
        // 更新检查组
        checkGroupDao.edit(checkGroup);
        // 删除检查组与检查项的关系
        checkGroupDao.deleteCheckGroupAndCheckItemByCheckGroupId(checkGroup.getId());
        // 新增检查组与检查项的关系
        if(checkitemIds != null && checkitemIds.length > 0) {

            for (Integer checkitemId : checkitemIds) {
                Map<String,Integer> map = new HashMap();
                map.put("checkGroupId",checkGroup.getId());
                map.put("checkitemId",checkitemId);
                checkGroupDao.addCheckGroupAndCheckItem(map);
            }
        }
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id) {
        List<Integer> list = new ArrayList<>();
        list = checkGroupDao.findCheckItemIdsByCheckGroupId(id);
        return list;
    }

    @Override
    public CheckGroup findById(Integer id) {
        return checkGroupDao.findById(id);
    }

    @Override
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        // 新增检查组
        checkGroupDao.add(checkGroup);
        Integer id = checkGroup.getId();
        // 新增检查组与检查项的关系
        if(checkitemIds != null && checkitemIds.length > 0) {

            for (Integer checkitemId : checkitemIds) {
                Map<String,Integer> map = new HashMap();
                map.put("checkGroupId",id);
                map.put("checkitemId",checkitemId);
                checkGroupDao.addCheckGroupAndCheckItem(map);
            }
        }
    }
}
