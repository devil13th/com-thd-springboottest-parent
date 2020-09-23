package com.thd.springboottest.shiro.sessiondao;

import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * 使用redis存储session信息以达到分布式服务session共享的效果
 */
public class MySessionDao extends AbstractSessionDAO {
    private static Logger logger = LoggerFactory.getLogger(MySessionDao.class);

    @Autowired
    private RedisTemplate myShiroRedisTemplate;
    @Value("${shiro.redis-key-prefix}")
    private String redisPrefixKey;

    /**
     * The Redis key prefix for the sessions
     */
    private String keyPrefix = "shiro_redis_session:";


    private String getKey(String originalKey) {
        return redisPrefixKey + keyPrefix+originalKey;
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        logger.debug("创建seesion,id=[{}]", session.getId().toString());
        try {
            String sessionJsonStr = JSONObject.toJSONString(session);
            System.out.println(sessionJsonStr);
            myShiroRedisTemplate.opsForValue().set(getKey(session.getId().toString()), session,30, TimeUnit.MINUTES);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return sessionId;

    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        logger.debug("获取seesion,id=[{}]", sessionId.toString());
        Session readSession = null;
        try {
            readSession=(Session) myShiroRedisTemplate.opsForValue().get(getKey(sessionId.toString()));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return readSession;
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        logger.debug("更新seesion,id=[{}]", session.getId().toString());
        try {

            String sessionJsonStr = JSONObject.toJSONString(session);
            System.out.println(sessionJsonStr);


            myShiroRedisTemplate.opsForValue().set(getKey(session.getId().toString()), session,30,TimeUnit.MINUTES);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }

    @Override
    public void delete(Session session) {
        logger.debug("删除seesion,id=[{}]", session.getId().toString());
        try {
            String key=getKey(session.getId().toString());
            myShiroRedisTemplate.delete(key);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }

    @Override
    public Collection<Session> getActiveSessions() {
        logger.info("获取存活的session");
        return Collections.emptySet();
    }
}
