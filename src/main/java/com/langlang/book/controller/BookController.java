package com.langlang.book.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.langlang.book.model.entity.Book;
import com.langlang.book.model.entity.RequestContext;
import com.langlang.book.service.BookService;
import com.langlang.book.util.PageBean;
import com.langlang.book.util.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Author: xiehongbin
 * @Date: 2018/12/24
 */
@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @RequestMapping("/save_book")
    public Map saveBook(@RequestBody Book book){
        if (StringUtils.isEmpty(book.getName())){
            throw new RuntimeException("图书名不能为空!");
        }
        if (StringUtils.isEmpty(book.getPrice())){
            throw new RuntimeException("图书价格不能为空!");
        }
        Long userId = RequestContext.getCurrentContext().getUserId();
        bookService.saveBook(userId, book);
        return ResultData.success(true, "添加成功！");
    }

    @RequestMapping("/update_book")
    public Map updateBook(@RequestBody Book book){
        if (StringUtils.isEmpty(book.getId())){
            throw new RuntimeException("图书ID不能为空!");
        }
        if (StringUtils.isEmpty(book.getName())){
            throw new RuntimeException("图书名不能为空!");
        }
        if (StringUtils.isEmpty(book.getPrice())){
            throw new RuntimeException("图书价格不能为空!");
        }
        bookService.updateBook(book);
        return ResultData.success(true, "修改成功！");
    }
    @RequestMapping("/batch_remove_book")
    public Map batchRemoveBook(@RequestBody JSONObject params){
        String ids = params.getString("ids");
        if (StringUtils.isEmpty(ids)){
            throw new RuntimeException("图书ID不能为空");
        }
        List<Long> listBookId = JSONArray.parseArray(ids, Long.class);
        if (CollectionUtils.isEmpty(listBookId) || listBookId.size()<1){
            throw new RuntimeException("图书ID不能为空");
        }
        bookService.batchRemoveBook(listBookId);
        return ResultData.success(true, "删除成功！");
    }

    @RequestMapping("/query_book")
    public Map queryBook(@RequestBody JSONObject params){
        Integer page = params.getInteger("page");
        Integer size = params.getInteger("size");
        String name = params.getString("name");
        PageBean<Book> result = bookService.queryBook(page, size, name);
        return ResultData.success(true,result);
    }

    @RequestMapping("/batch_update_book_status")
    public Map batchUpdateBookStatus(@RequestBody JSONObject params){
        String ids = params.getString("ids");
        Integer status = params.getInteger("status");
        if (StringUtils.isEmpty(status)){
            throw new RuntimeException("状态不能为空");
        }
        if (StringUtils.isEmpty(ids)){
            throw new RuntimeException("图书ID不能为空");
        }
        List<Long> listBookId = JSONArray.parseArray(ids, Long.class);
        if (CollectionUtils.isEmpty(listBookId) || listBookId.size()<1){
            throw new RuntimeException("图书ID不能为空");
        }
        bookService.batchUpdateBookStatus(listBookId, status);
        return ResultData.success(true,"修改成功");
    }

}
