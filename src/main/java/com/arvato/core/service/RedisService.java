package com.arvato.core.service;

public interface RedisService {

    /**
     * 根据 key 获取缓存中的字符串
     * @param key
     * @return
     */
    Object getKeys(String key);

    /**
     * 向redis中插入字符串
     * @param key
     * @param value
     * @return
     */
    Boolean setKeys(String key, Object value);

    /**
     * 指定缓存失效时间,键存在的情况下使用才能生效。所以要先存值,再设置过期时间
     * @param key 键
     * @param time 时间(秒)
     * @return
     */
    Boolean expire(String key, Long time);

    /**
     * 执行SET操作，并且设置生存时间，单位为秒
     * @param key 键
     * @param value 值
     * @param time 时间(秒)
     * @return
     */
    Boolean setkeyTimes(String key,Object value,Long time);

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key);

    /**
     * 删除缓存
     * @param key 可以传一个值 或多个
     * **/
    @SuppressWarnings("unchecked")
    public void del(String... key);

    /**
     * 查询key值的过期时间
     * @param  key  键
     ** 从redis中获取key对应的过期时间;
     * * 如果该值有过期时间，就返回相应的过期时间;
     * * 如果该值没有设置过期时间，就返回-1;
     * * 如果没有该值，就返回-2;
     * **/
    public Object findKeytoTimes(String key);
}
