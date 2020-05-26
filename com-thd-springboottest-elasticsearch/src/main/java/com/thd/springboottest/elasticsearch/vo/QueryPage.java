package com.thd.springboottest.elasticsearch.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * com.thd.springboottest.elasticsearch.vo.QueryPage
 *
 * @author: wanglei62
 * @DATE: 2020/5/25 10:34
 **/
@Data
@Accessors(chain = true)
public class QueryPage {
    /**
     * 当前页
     */
    private Integer current;
    /**
     * 每页记录数
     */
    private Integer size;
}
