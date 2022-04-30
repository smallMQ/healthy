package com.smallmq.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.smallmq.constant.RedisConstant;
import com.smallmq.dao.SetMealDao;
import com.smallmq.entity.PageResult;
import com.smallmq.entity.QueryPageBean;
import com.smallmq.pojo.Setmeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;
import smallmq.service.SetMealService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = SetMealService.class)
@Transactional
public class SetMealServiceImpl implements SetMealService {
    // 自动注入SetMealDao
    @Autowired
    private SetMealDao setMealDao;

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        PageHelper pageHelper = new PageHelper();
        pageHelper.startPage(currentPage,pageSize);
        Page<Setmeal> page = setMealDao.findPage(queryString);
        return new PageResult(page.getTotal(),page.getResult());


    }

    @Override
    public List<Setmeal> findAll() {
        return setMealDao.findAll();
    }

    @Override
    public Setmeal findById(Integer id) {
        return setMealDao.findById(id);


    }

    @Autowired
    private JedisPool jedisPool;

    @Override
    public void add(Setmeal setmeal, Integer[] checkGroupids) {
        setMealDao.add(setmeal);
        Integer setmealId = setmeal.getId();
        if(checkGroupids != null && checkGroupids.length > 0){
            for (Integer checkGroupid : checkGroupids) {
                Map<String,Integer> map = new HashMap<>();
                map.put("setmealId",setmealId);
                map.put("checkGroupId",checkGroupid);
                setMealDao.addSetMealAndCheckGroup(map);
            }
        }
        String img = setmeal.getImg();
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,img);

    }
}
