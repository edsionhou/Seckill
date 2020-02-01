package com.hou.dao;

import com.hou.pojo.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * @Author: edison
 * @Date: 2020/2/1 15时35分
 * @Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:conf/spring-dao.xml")
public class RedisDaoTest {

    @Autowired
    RedisDao redisDao;
    @Test
    public void getSeckill() {
        Seckill seckill = redisDao.getSeckill(1000L);
        System.out.println(seckill);
    }

    @Test
    public void putSeckill() {
        Seckill seckill = new Seckill();
        seckill.setSeckillId(1000L);
        seckill.setNameHou("张三");
        seckill.setNumber(10);
        String s = redisDao.putSeckill(seckill);
    }
}