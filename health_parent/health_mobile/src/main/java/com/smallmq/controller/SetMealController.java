package com.smallmq.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.smallmq.constant.MessageConstant;
import com.smallmq.entity.Result;
import com.smallmq.pojo.Setmeal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import smallmq.service.SetMealService;

import java.util.List;

@RestController
@RequestMapping("/setmeal")
public class SetMealController {

    @Reference
    private SetMealService setMealService;

    @RequestMapping("getSetmeal")
    public Result getSetmeal(){
        try {
            List<Setmeal> setmealList = setMealService.findAll();
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,setmealList);
        } catch (Exception e) {
        e.printStackTrace();
        return new Result(false, MessageConstant.GET_SETMEAL_LIST_FAIL);
        }
    }

    // 根据id查询
    @RequestMapping("findById")
    public Result findById(Integer id) {
        try {
            Setmeal setmeal = setMealService.findById(id);
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }
}
