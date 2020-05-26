package com.thd.springboottest.elasticsearch.service;

import com.thd.springboottest.elasticsearch.dao.EsRepository;
import com.thd.springboottest.elasticsearch.vo.QueryPage;
import com.thd.springboottest.elasticsearch.vo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * com.thd.springboottest.elasticsearch.service.EsServiceImpl
 *
 * @author: wanglei62
 * @DATE: 2020/5/25 10:39
 **/
@Service
public class EsServiceImpl  implements EsService {
    @Autowired
    private EsRepository esRepository;
    @Override
    public void add(Student student) {
        esRepository.save(student);
    }

    @Override
    public void addAll(List<Student> student) {
        esRepository.saveAll(student);
    }
    @Override
    public Page<Student> search(String keyword, QueryPage queryPage) {
        // es默认索引从0开始,mp默认从1开始
        PageRequest pageRequest = PageRequest.of(queryPage.getCurrent() - 1, queryPage.getSize());
        return esRepository.findByNameAndInfoLike(keyword, keyword, pageRequest);
    }
}
