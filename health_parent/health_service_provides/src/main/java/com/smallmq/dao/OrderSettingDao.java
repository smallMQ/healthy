package com.smallmq.dao;

import com.smallmq.pojo.OrderSetting;
import com.smallmq.pojo.Setmeal;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderSettingDao {
    void add(OrderSetting orderSetting);
    long getCountByDate(Date orderDate);
    void update(OrderSetting orderSetting);

    List<OrderSetting> getOrderSettingByMonth(Map map);

    void editNumberByDate(OrderSetting orderSetting);

    public Setmeal findById(int id);
}
