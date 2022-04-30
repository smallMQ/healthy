package smallmq.service;

import com.smallmq.entity.PageResult;
import com.smallmq.entity.QueryPageBean;
import com.smallmq.pojo.CheckItem;

import java.util.List;

public interface CheckItemService {
    public void add(CheckItem checkItem);
    public PageResult pageQuery(QueryPageBean queryPageBean);
    public void deleteById(Integer id);
    public CheckItem findById(Integer id);
    public void update(CheckItem checkItem);
    public List<CheckItem> findALl();
}