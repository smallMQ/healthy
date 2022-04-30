package com.smallmq.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.smallmq.constant.MessageConstant;
import com.smallmq.entity.PageResult;
import com.smallmq.entity.QueryPageBean;
import com.smallmq.entity.Result;
import com.smallmq.pojo.CheckItem;


import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import smallmq.service.CheckItemService;


@RestController
@RequestMapping("/checkitem")
public class CheckItemController {

    @Reference
    private CheckItemService checkItemService;

    //新增
    @RequestMapping("/add")
    public Result add(@RequestBody CheckItem checkItem){
        try {
            checkItemService.add(checkItem);
        }catch (Exception e){
            return new Result(false,MessageConstant.ADD_CHECKITEM_FAIL);
        }
        return new Result(true,MessageConstant.ADD_CHECKITEM_SUCCESS);
    }

    // 查询所有
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean  queryPageBean){
        try {
            PageResult pageResult = checkItemService.pageQuery(queryPageBean);
            return pageResult;
        }catch (Exception e){
            e.printStackTrace();
            return new PageResult(0l,null);
//            return new PageResult(false,MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }
    // 通过id删除数据
    @RequestMapping("/delete")
    public Result delete(Integer id){
        try {
            checkItemService.deleteById(id);
        }catch (RuntimeException e){
            System.out.println("过来了吗?");
            return new Result(false,MessageConstant.DELETE_CHECKITEM_FAIL);
        }catch (Exception e){
            return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
        }
        return new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }

    // 通过id查询数据
    @RequestMapping("/findById")
    public Result findById(Integer id){
        try {
            CheckItem checkItem = checkItemService.findById(id);
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItem);
        }catch (Exception e){
            return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

    // 修改数据
    @RequestMapping("/update")
    public Result update(@RequestBody CheckItem checkItem){
        try {
            checkItemService.update(checkItem);
        }catch (Exception e){
            return new Result(false,MessageConstant.EDIT_CHECKITEM_FAIL);
        }
        return new Result(true,MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }
    // 查询所有
    @RequestMapping("/findAll")
    public Result findAll(){
        try {
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItemService.findALl());
        }catch (Exception e){
            return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }
}


