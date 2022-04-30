package smallmq.service;

import com.smallmq.entity.PageResult;
import com.smallmq.entity.QueryPageBean;
import com.smallmq.pojo.Setmeal;

import java.util.List;

public interface SetMealService {
    public void add(Setmeal setmeal,Integer[] ids);

    public PageResult findPage(QueryPageBean queryPageBean);

    public List<Setmeal> findAll();

    public Setmeal findById(Integer id);
}
