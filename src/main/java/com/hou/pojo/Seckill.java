package com.hou.pojo;

import java.util.Date;

/**
 * @Author: edison
 * @Date: 2020/1/31 21时08分
 * @Description:  实体类 秒杀表单
 */
public class Seckill {
    private Long seckillId;
    private String nameHou; //此处故意改名，测试下不用resultMap映射
    private Integer number;
    private Date   startTime;
    private Date endTime;


    public Long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(Long seckillId) {
        this.seckillId = seckillId;
    }

    public String getNameHou() {
        return nameHou;
    }

    public void setNameHou(String nameHou) {
        this.nameHou = nameHou;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Seckill{" +
                "seckillId=" + seckillId +
                ", nameHou='" + nameHou + '\'' +
                ", number=" + number +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
