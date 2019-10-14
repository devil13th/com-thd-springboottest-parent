package com.thd.springboottest.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.thd.springboottest.mybatisplus.entity.SysUser;
import org.springframework.stereotype.Repository;

/**
 * com.thd.springboottest.mybatisplus.mapper.SysUserMapper
 *
 * @author: wanglei62
 * @DATE: 2019/9/20 16:12
 **/
@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {
}
