package com.hou.dao;

import com.hou.pojo.SuccessKilled;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * @Author: edison
 * @Date: 2020/1/31 22时45分
 * @Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:conf/spring-dao.xml"})
public class SuccessKilledDAOTest {

    @Resource
    private SuccessKilledDAO successKilledDAO;

    @Test
    public void insetSuccessKilled() {
        successKilledDAO.insetSuccessKilled(1001, 13054269093L);
    }

    @Test
    public void queryByIdWithSeckill() {
        SuccessKilled successKilled = successKilledDAO.queryByIdWithSeckill(1001, 13054269093L);
        System.out.println(successKilled);
    }
}