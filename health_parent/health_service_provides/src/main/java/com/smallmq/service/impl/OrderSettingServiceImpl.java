package com.smallmq.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.smallmq.dao.OrderSettingDao;
import com.smallmq.pojo.OrderSetting;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import smallmq.service.OrderSettingService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    @Override
    public void add(List<OrderSetting> orderSettingList) {
        if (orderSettingList != null && orderSettingList.size() > 0) {
            for (OrderSetting orderSetting : orderSettingList) {
                long count = orderSettingDao.getCountByDate(orderSetting.getOrderDate());
                if (count > 0) {
                    orderSettingDao.update(orderSetting);
                } else {
                    orderSettingDao.add(orderSetting);
                }

            }
        }
    }

    @Override
    public List<Map> getOrderSettingByMonth(String date) { // 2022-4
        String dateBegin = date + "-01";
        String dateEnd = date + "-30";

        List<Map> list = new ArrayList<>();
        Map<String,String> map1 = new HashMap<>();
        map1.put("dateBegin",dateBegin);
        map1.put("dateEnd",dateEnd);
        List<OrderSetting> orderSettings = orderSettingDao.getOrderSettingByMonth(map1);
        for (OrderSetting orderSetting : orderSettings) {
            Map<String,Integer> map = new HashMap<>();
            map.put("date",orderSetting.getOrderDate().getDate());
            map.put("number",orderSetting.getNumber());
            map.put("reservations",orderSetting.getReservations());
            list.add(map);
        }
        return list;
    }

    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        try {

            orderSettingDao.editNumberByDate(orderSetting);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
