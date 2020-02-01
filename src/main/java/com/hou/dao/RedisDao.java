package com.hou.dao;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.hou.pojo.Seckill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.PostConstruct;

/**
 * @Author: edison
 * @Date: 2020/2/1 14时54分
 * @Description: 持久层技术
 */

public class RedisDao {
    private final JedisPool jedisPool;

    public RedisDao(String ip,int port) {
        jedisPool = new JedisPool(ip, port);
    }

    //protostuff序列化和反序列效率最高
    private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);


    //从redis中取数据
    public Seckill getSeckill(Long seckillId) {


        Jedis jedis = jedisPool.getResource();
        try {
            String key = "seckill" + seckillId;
            //Seckill不去实现序列化接口，采用protostuff(要求对象是pojo)
            byte[] keyBytes = key.getBytes();
            //去jedis缓存中获取
            byte[] bytes = jedis.get(keyBytes);
            System.out.println("jedis将key返回结果：" + bytes);
            //反序列化
            if (bytes != null) {
                Seckill seckill = schema.newMessage();
                ProtobufIOUtil.mergeFrom(bytes, seckill, schema);
                // Seckill被反序列化
                return seckill;

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return null; //
    }


    public String putSeckill(Seckill seckill) {
        try {

            Jedis jedis = jedisPool.getResource();
            try {
                String key = "seckill" + seckill.getSeckillId();
                byte[] bytes = ProtobufIOUtil.toByteArray(seckill, schema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                int timeOut = 60 * 60; //s
                String setex = jedis.setex(key.getBytes(), timeOut, bytes);
                System.out.println("存放secill结果： " + setex);
                return setex;


            } finally {
                jedis.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
