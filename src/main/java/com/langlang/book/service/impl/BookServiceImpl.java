package com.langlang.book.service.impl;

import com.github.pagehelper.PageHelper;
import com.langlang.book.mapper.BookMapper;
import com.langlang.book.model.entity.Book;
import com.langlang.book.model.entity.BookStatus;
import com.langlang.book.service.BookService;
import com.langlang.book.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.List;

/**
 * @Author: xiehongbin
 * @Date: 2018/12/24
 */
@Service
public class BookServiceImpl implements BookService{

    @Autowired
    private BookMapper bookMapper;

    @Override
    public void saveBook(Long userId, Book book) {
        if (StringUtils.isEmpty(userId)){
            throw new RuntimeException("用户ID不能为空");
        }
        // 书名重复校验
        List<Book> books = bookMapper.queryBook(book.getName());
        if (!CollectionUtils.isEmpty(books) || books.size()>0){
            throw new RuntimeException("图书已经存在!");
        }
        if (StringUtils.isEmpty(book.getStatus())){
            book.setStatus(1);
        }
        book.setId(userId);
        book.setGmtCreate(new Timestamp(System.currentTimeMillis()));
        bookMapper.insert(book);
    }

    @Override
    public void updateBook(Book book) {
        book.setGmtModify(new Timestamp(System.currentTimeMillis()));
        bookMapper.update(book);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void     batchRemoveBook(List<Long> listBookId) {
        for (Long bookId : listBookId ) {
                Book book = bookMapper.get(bookId);
                if (ObjectUtils.isEmpty(book)){
                    continue;
                }
                if (BookStatus.getPUTAWAY().equals(book.getStatus())){
                    throw new RuntimeException("请先将该图书:'" + book.getName() + "'下架,在进行删除!");
                }
            bookMapper.remove(book.getId());
        }
    }

    @Override
    public PageBean<Book> queryBook(Integer page, Integer size, String name) {
        if(StringUtils.isEmpty(page)){
            page = 1;
        }
        if(StringUtils.isEmpty(size)){
            size = 25;
        }
        PageBean pageBean = new PageBean();
        pageBean.setPage(page);
        pageBean.setSize(size);
        // 开始分页
        PageHelper.startPage(page,size);
        List<Book> listBook = bookMapper.queryBook(name);
        pageBean.setTotle(listBook.size());
        pageBean.setData(listBook);
        return pageBean;
    }


    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void batchUpdateBookStatus(List<Long> listBookId, Integer status) {
        for (Long bookId: listBookId) {
            Book book = bookMapper.get(bookId);
            if (ObjectUtils.isEmpty(book) || status.equals(book.getStatus())){
                continue;
            }
            book.setStatus(status);
            book.setGmtModify(new Timestamp(System.currentTimeMillis()));
            bookMapper.update(book);
        }
    }
}
