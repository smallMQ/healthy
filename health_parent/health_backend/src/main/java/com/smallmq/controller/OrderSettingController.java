package com.smallmq.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.smallmq.constant.MessageConstant;
import com.smallmq.entity.Result;
import com.smallmq.pojo.OrderSetting;
import com.smallmq.utils.POIUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import smallmq.service.OrderSettingService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {

    @Reference
    private OrderSettingService orderSettingService;

    // 实现文件上传方法
    @RequestMapping("/upload")
    public Result upload(@RequestParam("excelFile") MultipartFile file) {

        try {
            List<String[]> list = POIUtils.readExcel(file);
            // 调用service实现导入
            List<OrderSetting> data = new ArrayList<>();
            for (String[] strings : list) {
                String orderDate = strings[0];
                Integer number = Integer.parseInt(strings[1]);
                OrderSetting orderSetting = new OrderSetting(new Date(orderDate), number);
                data.add(orderSetting);
            }
            orderSettingService.add(data);

        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }


        return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);

    }

    @RequestMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String date) {
        try {
            List<Map> list = orderSettingService.getOrderSettingByMonth(date);
            return new Result(true, MessageConstant.GET_ORDERSETTING_SUCCESS, list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_ORDERSETTING_FAIL);
        }
    }

    @RequestMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting) {
        try {

            orderSettingService.editNumberByDate(orderSetting);
            return new Result(true, MessageConstant.ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ORDERSETTING_FAIL);
        }
    }
}

