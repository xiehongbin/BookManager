package com.langlang.book.util;

import java.util.List;

/**
 * @Author: xiehongbin
 * @Date: 2018/12/24
 */
public class PageBean<T> {
    /**
     * 页码
     */
    private Integer page;

    /**
     * 页码大小
     */
    private Integer size;

    /**
     * 总记录数
     */
    private Integer totle;

    /**
     * 分页数据
     */
    private List<T> data;

    public PageBean(){};

    public PageBean(Integer page, Integer size, Integer totle, List<T> data) {
        this.page = page;
        this.size = size;
        this.totle = totle;
        this.data = data;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getTotle() {
        return totle;
    }

    public void setTotle(Integer totle) {
        this.totle = totle;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "page=" + page +
                ", size=" + size +
                ", totle=" + totle +
                ", data=" + data +
                '}';
    }
}
