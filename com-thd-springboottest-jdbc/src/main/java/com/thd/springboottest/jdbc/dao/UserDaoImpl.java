package com.thd.springboottest.jdbc.dao;

import com.thd.springboottest.jdbc.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author devil13th
 **/
@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int saveUser(User user) {
        String sql = "insert sys_user(user_id,user_name,user_sex,user_mail,user_tel,user_birthday,user_status,org_id) values (?,?,?,?,?,?,?,?)";
        return jdbcTemplate.update(sql,user.getId(),user.getName(),user.getSex(),user.getEmail(),user.getTel(),user.getBirthday(),user.getStatus(),user.getOrgId());
    }

    @Override
    public int deleteUser(String id) {
        String sql = "delete from  sys_user where user_id= ?";
        return jdbcTemplate.update(sql,id);

    }

    @Override
    public int updateUser(User user) {
        String sql = "update sys_user set user_name =?,user_sex =?,user_mail =?,user_tel =?,user_birthday =?,user_status =?,org_id =? where user_id = ?";
        return jdbcTemplate.update(sql,user.getName(),user.getSex(),user.getEmail(),user.getTel(),user.getBirthday(),user.getStatus(),user.getOrgId(),user.getId());

    }

    @Override
    public List queryUser(String userName) {
        String sql  = "select user_id,user_name,user_sex,user_mail,user_tel,user_birthday,user_status,org_id from sys_user where user_name like ? ";
        return jdbcTemplate.queryForList(sql,"%" + userName + "%");
    }

    @Override
    public User queryUserById(String id){
        User u = new User();
        String sql = "select user_id,user_name,user_sex,user_mail,user_tel,user_birthday,user_status,org_id from sys_user where user_id = '" + id + "'";
        Map<String,Object> r = jdbcTemplate.queryForMap(sql);
        u.setId(r.get("user_id") == null ? null : r.get("user_id").toString());
        u.setName(r.get("user_name") == null ? null : r.get("user_name").toString());
        u.setSex(r.get("user_sex") == null ? null : r.get("user_sex").toString());
        u.setEmail(r.get("user_mail") == null ? null : r.get("user_mail").toString());
        u.setBirthday(r.get("user_birthday") == null ? null : r.get("user_birthday").toString());
        u.setTel(r.get("user_tel") == null ? null : r.get("user_tel").toString());
        u.setStatus(r.get("user_status") == null ? null : r.get("user_status").toString());
        u.setOrgId(r.get("org_id") == null ? null : r.get("org_id").toString());
        return u;
    };
}
