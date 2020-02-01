package com.hou.service.impl;

import com.hou.dao.RedisDao;
import com.hou.dao.SeckillDAO;
import com.hou.dao.SuccessKilledDAO;
import com.hou.dto.Exposer;
import com.hou.dto.SeckillExcution;
import com.hou.dto.SeckillStateEnum;
import com.hou.exception.ChongFuException;
import com.hou.exception.MiaoShaException;
import com.hou.exception.SeckillCloseException;
import com.hou.pojo.Seckill;
import com.hou.pojo.SuccessKilled;
import com.hou.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * @Author: edison
 * @Date: 2020/2/1 09时01分
 * @Description:
 */
@Service
@Transactional   //如果有问题抛出异常，就整体回滚
public class SeckillServiceImpl implements SeckillService {
    @Autowired //byType  SeckilDAO接口的 实现类都能被注入
    private SeckillDAO seckillDAO;
    @Autowired
    private SuccessKilledDAO successKilledDAO;
    @Autowired
    RedisDao redisDao;

    private final String randomString = "gherogbriebgjregerg";

    public String getMD5(long seckillId) { //构建一个加密方法
        String md5 = seckillId + "/" + randomString;
        String s = DigestUtils.md5DigestAsHex(md5.getBytes());
        return s;
    }

    public List<Seckill> getSeckillList() {
        List<Seckill> seckills = seckillDAO.queryAll(0, 5);

        return seckills;
    }

    public Seckill getSeckillById(long seckillId) {
        Seckill seckill = seckillDAO.queryById(seckillId);
        return seckill;
    }

    public Exposer exportSeckillUrl(long seckillId){
        Date nowTime = new Date();
        try {
//            Seckill seckill = seckillDAO.queryById(seckillId); //查询要秒杀产品
            Seckill seckill = redisDao.getSeckill(seckillId);
            if(seckill==null){
                 seckill = seckillDAO.queryById(seckillId);
                 if(seckill==null){
                     return new Exposer(false,"查无此商品");
                 }
                String s = redisDao.putSeckill(seckill);
            }
            System.out.println("service+ + :  "+seckill);
            Date startTime = seckill.getStartTime();
            Date endTime = seckill.getEndTime();
            //判断秒杀时间是否开启 比较long值
            if (nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime()) {
                //false 未开启
                return new Exposer(false, nowTime.getTime(), startTime.getTime(), endTime.getTime());
            } else {
                //true 设置加密字符串
                String md5 = getMD5(seckillId);
                return new Exposer(true, md5, seckillId);
            }
        }catch (Exception e){
            //如果抛出异常，转换为运行期异常，回滚
            e.printStackTrace();
            throw new MiaoShaException("exposer运行期异常");
        }

    }

    /**
     * 执行秒杀方法  exposer返回了true后，才可以秒杀此地址，
     * 传入 ID MD5 userphone 继续校验
     * 我不想让Controller来处理业务，大量的if else 处理数据，他应该只是负责控制转发，但是他怎么判断呢？
     * 我们通过 抛出异常的方式来让他间接地判断
     */
    public SeckillExcution executeSeckill(long seckillId, long userPhone, String md5) {
        Date killTime = new Date();
        //校验MD5
        if (md5 == null || !md5.equals(getMD5(seckillId))) {
            //md5被篡改  -3 数据篡改
//            throw new MiaoShaException("MD5被篡改");
            SeckillExcution DATA_REWRITE = new SeckillExcution(false, SeckillStateEnum.DATA_REWRITE);
            return DATA_REWRITE;
        }
        try {


            //加密正确，开始秒杀
            int inNumer = successKilledDAO.insetSuccessKilled(seckillId, userPhone);

            if (inNumer < 1) { //没插入成功
//            throw new ChongFuException("重复秒杀"); REPEAT_KILL(-1,"重复秒杀")
                SeckillExcution REPEAT_KILL = new SeckillExcution(false, SeckillStateEnum.REPEAT_KILL);
                return REPEAT_KILL;
            } else {
                //减少库存
                int reduceNumber = seckillDAO.reduceNumber(seckillId, killTime);
                if (reduceNumber < 1) {
                    //没库存了
//                throw new SeckillCloseException("秒杀结束了"); END(0,"秒杀结束")
                    SeckillExcution skEND = new SeckillExcution(false, SeckillStateEnum.END);
                    return skEND;
                } else {
                    //秒杀成功，返回结果
                    SuccessKilled successKilled = successKilledDAO.queryByIdWithSeckill(seckillId, userPhone);
                    SeckillExcution skSucc = new SeckillExcution(true,SeckillStateEnum.SUCCESS, successKilled);

                    return skSucc;
                }
            }

        } catch (Exception e) {
            e.printStackTrace(); //异常直接捕获掉，抛出runtime异常，回滚。
            //transactional 默认只捕获runtime异常，其他异常需要指定。
            throw new MiaoShaException("service内部出错" + e.getMessage());
        }
    }
}
