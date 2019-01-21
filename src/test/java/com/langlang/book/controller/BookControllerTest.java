package com.langlang.book.controller;

import com.langlang.book.model.entity.Book;
import com.langlang.book.service.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @Author: xiehongbin
 * @Date: 2019/1/19 0019 17:30
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BookControllerTest {

    @Autowired
    private  BookService bookService;

    @Test
    public void saveBook() {
        long l = System.currentTimeMillis();
        System.out.println(l);
        Book book = new Book();
        for (int i = 1; i <= 100000; i++){
            book.setName("图书:" + i);
            double p = i;
            book.setPrice(p);
            bookService.saveBook(119L,book);
        }
        long l1 = System.currentTimeMillis();
        System.out.println(l1);
        System.out.println(l - l1);
    }
}