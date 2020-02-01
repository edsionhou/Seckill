package com.hou.dao;

import com.hou.pojo.Seckill;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @Author: edison
 * @Date: 2020/1/31 21时19分
 * @Description:
 */

public interface SeckillDAO {
    //减库存 成功后，number -1
    //java没有保存形参的记录 queryAll(int a, int b) >> queryAll(arg0,arg1)
    int reduceNumber(@Param("seckillId") long seckillId, @Param("killTime") Date killTime);

    //查询商品 byId
    Seckill queryById(long seckillId);

    //查询所有商品, 分页查询一下
    List<Seckill> queryAll(@Param("offset") int offset, @Param("limit") int limit);

}
