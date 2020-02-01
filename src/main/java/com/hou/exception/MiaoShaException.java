package com.hou.exception;

/**
 * @Author: edison
 * @Date: 2020/2/1 09时55分
 * @Description:
 */
public class MiaoShaException extends RuntimeException{

    public MiaoShaException() {
        super();
    }

    public MiaoShaException(String message) {
        super(message);
    }

    public MiaoShaException(String message, Throwable cause) {
        super(message, cause);
    }
}
