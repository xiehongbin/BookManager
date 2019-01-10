package com.langlang.book.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.langlang.book.model.entity.Book;
import com.langlang.book.model.entity.RequestContext;
import com.langlang.book.service.BookService;
import com.langlang.book.util.ExcelUtil;
import com.langlang.book.util.PageBean;
import com.langlang.book.util.ResultData;
import com.langlang.book.util.TransitionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * @Author: xiehongbin
 * @Date: 2018/12/24
 */
@Api("图书管理")
@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @ApiOperation("添加图书")
    @RequestMapping(value = "/save_book", method = RequestMethod.POST)
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

    @RequestMapping("/export")
    public void export(HttpServletResponse response) throws IOException {
        OutputStream out = null;
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=book.xls");
        List<Book> data = bookService.queryBook(1, 999999999, null).getData();
        String [] headers = {"ID", "图书名", "创作者", "价格", "状态", "创建时间"};
        String [] keys = {"id", "name", "author", "price", "status", "gmtCreate"};
        Collection<Map<String, Object>> result = new LinkedList<>();
        for (Book book : data) {
            Map<String, Object> map = TransitionUtil.EntityToMap(book);
            result.add(map);
        }
        HSSFWorkbook workBook = ExcelUtil.exportMapExcel("图书列表", headers, keys, result, out);
        try {
            out = response.getOutputStream();
            if (out != null) {
                workBook.write(out);
            }
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
