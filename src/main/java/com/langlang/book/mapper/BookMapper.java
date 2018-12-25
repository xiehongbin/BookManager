package com.langlang.book.mapper;

import com.langlang.book.model.entity.Book;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: xiehongbin
 * @Date: 2018/12/25
 */
@Repository
public interface BookMapper {
    /**
     * 添加
     * @param book 图书数据
     * @return
     */
    int insert(Book book);

    /**
     * 修改
     * @param book 图书数据
     * @return
     */
    int update(Book book);

    /**
     * 根据主键ID查询图书
     * @param bookId 图书ID
     * @return
     */
    Book get(Long bookId);

    /**
     * 删除图书
     * @param id
     * @return
     */
    int remove(Long id);

    /**
     * 查询图书
     * @param name
     * @return
     */
    List<Book> queryBook(String name);

    /**
     * 查询总记录数
     * @return
     */
    Integer findTotalCount();
}