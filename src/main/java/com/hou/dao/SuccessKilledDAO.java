package com.hou.dao;

import com.hou.pojo.SuccessKilled;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: edison
 * @Date: 2020/1/31 22时23分
 * @Description:
 */
public interface SuccessKilledDAO {

    //插入 成功秒杀 明细
    int insetSuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);

    // 查询 秒杀成功结果，携带一个Seckill产品对象
    SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);
}
