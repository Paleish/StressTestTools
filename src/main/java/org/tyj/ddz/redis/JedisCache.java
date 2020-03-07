/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: JedisCacheHelper
 * Author:   tyj
 * Date:     2019/3/27 14:17
 * Description: jedis缓存处理类
 * History:
 * <author>          <time>          <version>          <desc>
 * TanYujie       修改时间           版本号              描述
 */
package org.tyj.ddz.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * 〈一句话功能简述〉<br>
 * 〈jedis缓存处理类〉
 *
 * @author tyj
 * @create 2019/3/27
 * @since 1.0.0
 */
@Component
public class JedisCache {

    private static final Logger logger = LoggerFactory.getLogger(JedisCache.class.getName());

    private static HostAndPort hnp = HostAndPortUtil.getRedisServers().get(0);

    private static JedisPool pool = null;

    private static ResourceBundle redisCfg;

    private static void loadConfiguration() {
        try {
            redisCfg = ResourceBundle.getBundle("redis");
        } catch (Exception cex) {
            System.out.print(cex);
        }
    }

    private static JedisPoolConfig buildJedisConfig() {
        //读取配置
        loadConfiguration();
        //加载配置
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(Integer.parseInt(redisCfg.getString("redis.pool.maxIdle")));
        config.setMinIdle(Integer.parseInt(redisCfg.getString("redis.pool.minIdle")));
        config.setMaxTotal(Integer.parseInt(redisCfg.getString("redis.pool.maxTotal")));
        config.setMaxWaitMillis(Integer.parseInt(redisCfg.getString("redis.pool.maxWait")));
        config.setTestOnBorrow(true);
        if (redisCfg.containsKey("redis.timeBetweenEvictionRunsMillis")) {
            config.setTimeBetweenEvictionRunsMillis(Integer.parseInt(redisCfg.getString("redis.timeBetweenEvictionRunsMillis")));
        }
        if (redisCfg.containsKey("redis.minEvictableIdleTimeMillis")) {
            config.setMinEvictableIdleTimeMillis(Integer.parseInt(redisCfg.getString("redis.minEvictableIdleTimeMillis")));
        }
        return config;
    }

    private static void initJedisPool() {
        JedisPoolConfig config = buildJedisConfig();
        pool = new JedisPool(config, hnp.getHost(), hnp.getPort(), Integer.parseInt(redisCfg.getString("redis.timeOut")), null, Integer.parseInt(redisCfg.getString("redis.database")));
    }

    @Bean("Jedis")
    @Scope(value = "prototype")
    public static Jedis getCacheInstance() {
        if (null == pool) {
            initJedisPool();
        }
        Jedis jedis = pool.getResource();
        //logger.debug("缓存-redis-状态:活跃数->{},空闲数->{}", pool.getNumActive(), pool.getNumIdle());
        return jedis;
    }

    /*关闭Jedis*/
    public static void close(Jedis jedis) {
        try {
            if (jedis != null) {
                jedis.close();
            }
        } catch (Exception e) {
            logger.error("返还jedis线程失败!");
        }
    }

    public static void put(String key, String value) {
        Jedis s = null;
        try {
            s = getCacheInstance();
            s.set(key, value);
        } finally {
            close(s);
        }
    }

    public static void put(byte[] key, byte[] value) {
        Jedis s = null;
        try {
            s = getCacheInstance();
            s.set(key, value);
        } finally {
            close(s);
        }
    }

    public static void putEx(String key, String value, int expireTime) {
        Jedis s = null;
        try {
            s = getCacheInstance();
            s.setex(key, expireTime, value);
        } finally {
            close(s);
        }
    }

    public static void putEx(byte[] key, byte[] value, int expireTime) {
        Jedis s = null;
        try {
            s = getCacheInstance();
            s.setex(key, expireTime, value);
        } finally {
            close(s);
        }
    }

    public static String get(String key) {
        Jedis s = null;
        try {
            s = getCacheInstance();
            return s.get(key);
        } finally {
            close(s);
        }
    }

    public static byte[] get(byte[] key) {
        Jedis s = null;
        try {
            s = getCacheInstance();
            return s.get(key);
        } finally {
            close(s);
        }
    }

    public static boolean isExist(String key) {
        Jedis s = null;
        try {
            s = getCacheInstance();
            return s.exists(key);
        } finally {
            close(s);
        }
    }

    public static boolean isExist(byte[] key) {
        Jedis s = null;
        try {
            s = getCacheInstance();
            return s.exists(key);
        } finally {
            close(s);
        }
    }

    public static void remove(byte[] key) {
        Jedis s = null;
        try {
            s = getCacheInstance();
            s.del(key);
        } finally {
            close(s);
        }
    }

    public static void remove(String key) {
        Jedis s = null;
        try {
            s = getCacheInstance();
            s.del(key);
        } finally {
            close(s);
        }
    }

    /**
     * @param key
     * @param field
     * @param num
     * @return
     */
    public static long hincrby(String key, String field, long num) {
        Jedis s = null;
        try {
            s = getCacheInstance();
            Long hincrBy = s.hincrBy(key, field, num);
            return hincrBy;
        } finally {
            close(s);
        }
    }

    public static String hget(String key, String field) {
        Jedis s = null;
        try {
            s = getCacheInstance();
            return s.hget(key, field);
        } finally {
            close(s);
        }
    }

    public static boolean hexist(String key, String field) {
        Jedis s = null;
        try {
            s = getCacheInstance();
            Boolean hexists = s.hexists(key, field);
            return hexists;
        } finally {
            close(s);
        }
    }

    public static void hset(String key, String field, String value) {
        Jedis s = null;
        try {
            s = getCacheInstance();
            s.hset(key, field, value);
        } finally {
            close(s);
        }
    }

    public static void hdel(String key, String field) {
        Jedis s = null;
        try {
            s = getCacheInstance();
            s.hdel(key, field);
        } finally {
            close(s);
        }
    }

    public static Set<String> hkeys(String key) {
        Jedis s = null;
        try {
            s = getCacheInstance();
            Set<String> set = s.hkeys(key);
            return set;
        } finally {
            close(s);
        }
    }

    /**
     * 设置键过期时间
     *
     * @param key
     * @param time
     * @return
     */
    public static long setKeyExpireTime(String key, int time) {
        Jedis s = null;
        try {
            s = getCacheInstance();
            Long expire = s.expire(key, time);
            return expire;
        } finally {
            close(s);
        }
    }

    /**
     * 模糊查询
     *
     * @param key
     * @return
     */
    public static Set<byte[]> fuzzySearch(String key) {
        Jedis s = null;
        try {
            s = getCacheInstance();
            Set<byte[]> keys = s.keys(key.getBytes());
            return keys;
        } finally {
            close(s);
        }
    }

    public static Map<String, String> hgetAll(String key) {
        Jedis s = null;
        try {
            s = getCacheInstance();
            Map<String, String> map = s.hgetAll(key);
            return map;
        } finally {
            close(s);
        }
    }
}