package com.hou.service.impl;

import com.hou.dao.SeckillDAO;
import com.hou.dto.Exposer;
import com.hou.dto.SeckillExcution;
import com.hou.pojo.Seckill;
import com.hou.service.SeckillService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author: edison
 * @Date: 2020/2/1 13时06分
 * @Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:conf/spring-service.xml","classpath:/conf/spring-dao.xml"})
public class SeckillServiceImplTest {

    @Autowired
    SeckillService seckillService;
    @Test
    public void getMD5() {


    }

    @Test
    public void getSeckillList() {
        List<Seckill> seckillList = seckillService.getSeckillList();
        System.out.println(seckillList);
    }

    @Test
    public void getSeckillById() {
        Seckill seckillById = seckillService.getSeckillById(1000);
        System.out.println(seckillById);

    }

    @Test
    public void exportSeckillUrl() {
        Exposer exposer = seckillService.exportSeckillUrl(1001);
        System.out.println(exposer);
        System.out.println(exposer.getMd5());


    }

    @Test
    public void executeSeckill() {
        String md5 = "98a155a592721a82da72cc41e7371f0a";
        SeckillExcution seckillExcution = seckillService.executeSeckill(1001, 11114269093L, md5);
        System.out.println(seckillExcution);

    }
}