package com.arvato.core.service.impl;

import com.arvato.core.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    RedisTemplate<String, Object> redisTemplates;

    private Logger logger = LoggerFactory.getLogger(RedisServiceImpl.class);

    @Override
    public Object getKeys(String key) {
        return key==null?null:redisTemplates.opsForValue().get(key);
    }

    @Override
    public Boolean setKeys(String key, Object value) {
        try {
            redisTemplates.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            logger.error("redis存值失败,【key:" + key + "】" + "【value" + value + "】");
            return false;
        }
    }

    @Override
    public Boolean expire(String key, Long time) {
        try {
            if(time>0){
                redisTemplates.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            logger.error("redis存值以及设置缓存超时时间失败,【key:" + key + "】" + "【time" + time + "】");
            return false;
        }
    }

    @Override
    public Boolean setkeyTimes(String key, Object value, Long time) {
        try {
            if(time>0){
                redisTemplates.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            }else{
                setKeys(key, value);
            }
            return true;
        } catch (Exception e) {
            logger.error("redis存值以及设置缓存超时时间失败,【key:" + key + "】" + "【value" + value + "】" + "【time" + time + "】");
            return false;
        }
    }

    @Override
    public boolean hasKey(String key) {
        try {
            return redisTemplates.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplates.delete(key[0]);
            } else {
                redisTemplates.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    @Override
    public Object findKeytoTimes(String key) {
        try {
           return redisTemplates.opsForValue().getOperations().getExpire(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
