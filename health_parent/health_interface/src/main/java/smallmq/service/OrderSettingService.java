package smallmq.service;

import com.smallmq.pojo.OrderSetting;
import org.aspectj.weaver.ast.Or;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderSettingService {

    public void add(List<OrderSetting> orderSettingList);

    public List<Map> getOrderSettingByMonth(String date);

    public void editNumberByDate(OrderSetting orderSetting);
}
