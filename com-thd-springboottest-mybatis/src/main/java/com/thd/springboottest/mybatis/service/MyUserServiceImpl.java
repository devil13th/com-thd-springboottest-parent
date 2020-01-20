package com.thd.springboottest.mybatis.service;

import com.thd.springboottest.mybatis.dao.MyUserMapper;
import com.thd.springboottest.mybatis.entity.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author devil13th
 **/
@Service
public class MyUserServiceImpl implements  MyUserService {
    @Autowired
    private MyUserMapper myUserMapper;
    @Override
    public int save(MyUser user) {return myUserMapper.save(user);}
    @Override
    public int update(MyUser user) {
        return myUserMapper.update(user);
    }
    @Override
    public MyUser queryById(String id) {return myUserMapper.queryById(id);}
    @Override
    public List queryList(MyUser user) {return myUserMapper.queryList(user);}
    @Override
    public List queryListByLike(MyUser user){return myUserMapper.queryListByLike(user);};
    @Override
    public int deleteById(String id){return myUserMapper.deleteById(id);}
    //一个联合查询和Map类型的参数
    public List queryJoin(Map m){return myUserMapper.queryJoinExample(m);};


}
