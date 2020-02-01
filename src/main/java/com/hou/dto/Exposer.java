package com.hou.dto;

import java.util.Date;

/**
 * @Author: edison
 * @Date: 2020/2/1 08时57分
 * @Description:
 */
public class Exposer {
    private boolean exposed; // 是否开启了秒杀
    private String md5;
    private long seckillId;
    private long nowTime;
    private long startTime;
    private long endTime;
    private String errorInfo;

    //未开启，返回 false 时间
    public Exposer(boolean exposed, long nowTime, long startTime, long endTime) {
        this.exposed = exposed;
        this.nowTime = nowTime;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    //开启， true  seckillid  md5
    public Exposer(boolean exposed, String md5, long seckillId) {
        this.exposed = exposed;
        this.md5 = md5;
        this.seckillId = seckillId;
    }

    public Exposer(boolean exposed, String errorInfo) {
        this.exposed = exposed;
        this.errorInfo = errorInfo;
    }

    @Override
    public String toString() {
        return "Exposer{" +
                "exposed=" + exposed +
                ", md5='" + md5 + '\'' +
                ", seckillId=" + seckillId +
                ", nowTime=" + nowTime +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", errorInfo='" + errorInfo + '\'' +
                '}';
    }

    public boolean isExposed() {
        return exposed;
    }

    public void setExposed(boolean exposed) {
        this.exposed = exposed;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public long getNowTime() {
        return nowTime;
    }

    public void setNowTime(long nowTime) {
        this.nowTime = nowTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }
}
