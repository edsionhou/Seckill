package com.hou.dao;

import com.hou.exception.MiaoShaException;

/**
 * @Author: edison
 * @Date: 2020/2/1 10时16分
 * @Description:
 */
public class TestException {
    public static void main(String[] args) {
        try {
            throw new MiaoShaException("秒杀失败");
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
            System.out.println("=====================");
            System.out.println(e.getStackTrace());
            e.printStackTrace();
        }
    }
}
