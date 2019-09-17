package com.thd.springboottest.jdbc.service;

import com.thd.springboottest.jdbc.bean.User;
import com.thd.springboottest.jdbc.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author devil13th
 *
 *
 验证相关SQL：
delete from sys_user where user_id in ('1','2','3');
select  *  from sys_user where user_id in ('1','2','3');
 **/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private User2Service user2Service;

    @Override
    @Transactional(propagation= Propagation.REQUIRES_NEW)
    public int saveUser(User user) {
        return userDao.saveUser(user);
    }

    @Override

    @Transactional
    public int deleteUser(String id) {
        return userDao.deleteUser(id);
    }

    @Override
    @Transactional
    public int updateUser(User user) {
        return userDao.updateUser(user);
    }

    @Override
    public List queryUser(String userName) {
        return userDao.queryUser(userName);
    }

    @Override
    public User queryUserById(String id){
        return this.userDao.queryUserById(id);
    };

    @Override
    @Transactional
    public void saveMoreUser(User u1,User u2){
        this.saveUser(u1);
        int a = 1/0;
        this.saveUser(u2);
    };


    public void testTransaction1(){
        /**
         * 该方法没有加@Transaction ，没有建立事务机制
         * 报错之前保存的内容已经保存成功,报错之后的代码根本不执行,所以没有报错
         *
         */
        User u1 = new User();
        u1.setId("1");
        u1.setName("a");

        User u2 = new User();
        u2.setId("2");
        u2.setName("b");

        User u3 = new User();
        u3.setId("3");
        u3.setName("c");
        this.saveUser(u3);
        this.saveUser(u1);
        int a = 1/0;
        this.saveUser(u2);
    };

    @Transactional
    public void testTransaction2(){
        /**
         * 该方法加了@Transaction ，所以要成功都成功,要失败都失败，一致性得到了验证
         *
         * 方法内调用自身类的其他方法,虽然saveUser传播机制使用的是Propagation.REQUIRES_NEW  但还是回滚了
         */
        User u1 = new User();
        u1.setId("1");
        u1.setName("a");

        User u2 = new User();
        u2.setId("2");
        u2.setName("b");

        User u3 = new User();
        u3.setId("3");
        u3.setName("c");
        this.saveUser(u3);
        this.saveUser(u1);
        int a = 1/0;
        this.saveUser(u2);
    };

    @Transactional
    public void testTransaction3(){
        /**
         * 该方法加了@Transaction ，所以该类的方法要成功都成功,要失败都失败，一致性得到了验证
         *
         * 方法内调用其他类的其他方法,而且user2Service.saveUserWithNewTx方法传播机制使用的是Propagation.REQUIRES_NEW 所以他自己执行成功了
         */
        User u1 = new User();
        u1.setId("1");
        u1.setName("a");

        User u2 = new User();
        u2.setId("2");
        u2.setName("b");

        User u3 = new User();
        u3.setId("3");
        u3.setName("c");
        user2Service.saveUserWithNewTx(u3);
        this.saveUser(u1);
        int a = 1/0;
        this.saveUser(u2);
    };

    @Transactional
    public void testTransaction4(){
        /**
         * 该方法加了@Transaction ，所以该类的方法要成功都成功,要失败都失败，一致性得到了验证
         * 方法内调用其他类的其他方法,而且user2Service.saveUserWithNoTx方法没有加@Transactional 但回滚了
         */
        User u1 = new User();
        u1.setId("1");
        u1.setName("a");

        User u2 = new User();
        u2.setId("2");
        u2.setName("b");

        User u3 = new User();
        u3.setId("3");
        u3.setName("c");
        user2Service.saveUserWithNoTx(u3);
        this.saveUser(u1);
        int a = 1/0;
        this.saveUser(u2);
    };
}
