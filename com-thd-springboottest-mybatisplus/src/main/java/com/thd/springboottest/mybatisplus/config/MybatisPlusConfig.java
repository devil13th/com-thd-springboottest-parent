package com.thd.springboottest.mybatisplus.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * com.thd.springboottest.mybatisplus.config.MybatisPlusConfig
 * mybatis plus 插件配置类
 * @author: wanglei62
 * @DATE: 2019/9/20 17:09
 **/

@EnableTransactionManagement
@Configuration
@MapperScan("com.thd.springboottest.mybatisplus.mapper*")
public class MybatisPlusConfig {
    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // paginationInterceptor.setLimit(你的最大单页限制数量，默认 500 条，小于 0 如 -1 不受限制);
        return paginationInterceptor;
    }
}
