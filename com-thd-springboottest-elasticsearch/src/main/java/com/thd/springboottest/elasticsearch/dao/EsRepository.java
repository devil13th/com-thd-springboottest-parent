package com.thd.springboottest.elasticsearch.dao;

import com.thd.springboottest.elasticsearch.vo.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * com.thd.springboottest.elasticsearch.dao.EsRepository
 *
 * @author: wanglei62
 * @DATE: 2020/5/25 10:38
 **/
@Repository
public interface EsRepository  extends ElasticsearchRepository<Student, String> {
    /**
     * 根据学生姓名或信息模糊查询
     */
    Page<Student> findByNameAndInfoLike(String name, String info, Pageable pageable);
}
