package com.hou.exception;

/**
 * @Author: edison
 * @Date: 2020/2/1 10时10分
 * @Description:
 */
public class SeckillCloseException extends MiaoShaException {
    public SeckillCloseException() {
        super();
    }

    public SeckillCloseException(String message) {
        super(message);
    }

    public SeckillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
