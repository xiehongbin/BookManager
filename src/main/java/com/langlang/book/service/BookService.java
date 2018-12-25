package com.langlang.book.service;

import com.langlang.book.model.entity.Book;
import com.langlang.book.util.PageBean;

import java.util.List; /**
 * @Author: xiehongbin
 * @Date: 2018/12/24
 */
public interface BookService {

    /**
     *  添加图书
     * @param userId 用户
     * @param book 图书对象
     */
    public void saveBook(Long userId, Book book);

    /**
     * 修改图书
     * @param book
     */
    void updateBook(Book book);

    /**
     * 批量删除图书
     * @param listBookId 图书ID
     */
    void batchRemoveBook(List<Long> listBookId);

    /**
     * 分页查询图书
     * @param page 页码
     * @param size 页码大小
     * @param name 图书名
     * @return
     */
    PageBean<Book> queryBook(Integer page, Integer size, String name);

    /**
     * 批量修改图书状态
     * @param listBookId 图书ID
     * @param status 图书状态
     */
    void batchUpdateBookStatus(List<Long> listBookId, Integer status);
}
