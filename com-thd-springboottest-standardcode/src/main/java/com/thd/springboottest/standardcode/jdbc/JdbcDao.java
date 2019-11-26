package com.thd.springboottest.standardcode.jdbc;

import com.thd.springboottest.standardcode.bean.Page;

import java.util.List;
import java.util.Map;

/**
 * com.thd.springboottest.standardcode.jdbc.JdbcDao
 *
 * @author: wanglei62
 * @DATE: 2019/11/25 23:30
 **/
public interface JdbcDao {
    /**
     * SQL查询
     * @param sql sql语句  推荐使用prepareStatement,可以缓存
     * @param params prepareStatement中的参数
     * @param currentPage 当前页
     * @param pageSize 每页条目数
     * @return
     */
    public List<Map<String,Object>> query(String sql, Object[] params, Integer currentPage , Integer pageSize);

    /**
     * SQL 查询
     * @param sql sql语句  推荐使用prepareStatement,可以缓存
     * @param params prepareStatement中的参数
     * @param p 分页信息
     * @return
     */
    public List<Map<String,Object>> query(String sql, Object[] params, Page p);

    /**
     * 统计条目数
     * @param sql sql语句  推荐使用prepareStatement,可以缓存
     * @param params prepareStatement中的参数
     * @return
     */
    public int queryCount(String sql,Object[] params);
}
