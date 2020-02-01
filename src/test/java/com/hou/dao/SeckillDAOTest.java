package com.hou.dao;

import com.hou.pojo.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * @Author: edison
 * @Date: 2020/1/31 21时36分
 * @Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"classpath:conf/spring-dao.xml"})
public class SeckillDAOTest {

    @Resource
    private SeckillDAO seckillDAO;

    @Test
    public void reduceNumber() {
        seckillDAO.reduceNumber(1001,new Date());
    }

    @Test
    public void queryById() {
        Seckill seckill = seckillDAO.queryById(1001);
        System.out.println(seckill);
    }

    @Test
    public void queryAll() {
    }
}