package com.thd.springboottest.elasticsearch.service;

import com.thd.springboottest.elasticsearch.vo.QueryPage;
import com.thd.springboottest.elasticsearch.vo.Student;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * com.thd.springboottest.elasticsearch.service.ESService
 *
 * @author: wanglei62
 * @DATE: 2020/5/25 10:33
 **/
public interface EsService {
    /**
     * 插入
     */
    void add(Student student);

    /**
     * 批量插入
     */
    void addAll(List<Student> student);
    /**
     * 模糊查询
     */
    Page<Student> search(String keyword, QueryPage queryPage);
}
