package com.smallmq.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.smallmq.constant.MessageConstant;
import com.smallmq.constant.RedisConstant;
import com.smallmq.entity.QueryPageBean;
import com.smallmq.entity.Result;
import com.smallmq.pojo.Setmeal;
import com.smallmq.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;
import smallmq.service.SetMealService;

import java.io.IOException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/setmeal")

public class SetMealController {

    @Autowired
    private JedisPool jedisPool;

    @Reference
    private SetMealService setMealService;

    // 上传功能
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Result upload(@RequestParam("imgFile") MultipartFile file) {
//        System.out.println(file);
        String fileName = file.getOriginalFilename();
        int index = fileName.lastIndexOf(".");
        String ext = fileName.substring(index - 1);
        long currentTimeMillis = System.currentTimeMillis();
        String fileNameNew = currentTimeMillis + ext;
        try {
            QiniuUtils.upload2Qiniu(file.getBytes(),fileNameNew);
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,fileNameNew);
        } catch (IOException e) {
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
        return new Result(true, MessageConstant.UPLOAD_SUCCESS, fileNameNew);
        // ...
    }

    // 增加功能
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result add(@RequestBody Setmeal setmeal,Integer[] checkgroupIds) {
        try {
            setMealService.add(setmeal,checkgroupIds);
        }catch (Exception e){
            return new Result(false,MessageConstant.ADD_SETMEAL_FAIL);
        }
        return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    // 分页查询
    @RequestMapping(value = "/findPage", method = RequestMethod.POST)
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        try {
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setMealService.findPage(queryPageBean));

        }catch (Exception e){
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }
}
