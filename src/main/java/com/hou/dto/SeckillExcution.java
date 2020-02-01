package com.hou.dto;

import com.hou.pojo.SuccessKilled;

/**
 * @Author: edison
 * @Date: 2020/2/1 08时59分
 * @Description:
 */
public class SeckillExcution {
    private boolean result; //秒杀结果是否成功
    private int state; // 秒杀状态 0成功  其他：（失败的各种原因）
    private String stateInfo;
    private SuccessKilled successKilled; //成功的话，携带成功对象

    public SeckillExcution(boolean result, SeckillStateEnum stateEnum) {
        this.result = result;
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    public SeckillExcution(boolean result, SuccessKilled successKilled) {
        this.result = result;
        this.successKilled = successKilled;
    }

    public SeckillExcution(boolean result, SeckillStateEnum stateEnum, SuccessKilled successKilled) {
        this.result = result;
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.successKilled = successKilled;
    }

    @Override
    public String toString() {
        return "SeckillExcution{" +
                "result=" + result +
                ", state=" + state +
                ", stateInfo='" + stateInfo + '\'' +
                ", successKilled=" + successKilled +
                '}';
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public SuccessKilled getSuccessKilled() {
        return successKilled;
    }

    public void setSuccessKilled(SuccessKilled successKilled) {
        this.successKilled = successKilled;
    }
}
