package com.hou.service;

import com.hou.dto.Exposer;
import com.hou.dto.SeckillExcution;
import com.hou.exception.MiaoShaException;
import com.hou.pojo.Seckill;

import java.util.List;

/**
 * @Author: edison
 * @Date: 2020/2/1 08时53分
 * @Description:
 */
public interface SeckillService {
    //查询 秒杀列表
    List<Seckill> getSeckillList();

    //查询秒杀列表的 单个记录
    Seckill getSeckillById(long seckillId);

    /**
     * 是否暴漏秒杀地址
     * yes 暴漏 什么 id md5 地址,
     * no  什么  false 时间
     * 设计一个Exposer来装
     */
    Exposer exportSeckillUrl(long seckillId) ;

    /**
     * 暴漏地址后，执行秒杀操作
     * 成功返回？  yes，success_seckilled 结果
     * 失败返回？
     * 设计一个SeckillExcution来装
     */
    SeckillExcution executeSeckill(long seckillId,long userPhone,String md5);
    String getMD5(long userphone);




}
