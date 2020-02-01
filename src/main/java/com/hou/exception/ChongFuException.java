package com.hou.exception;

/**
 * @Author: edison
 * @Date: 2020/2/1 10时06分
 * @Description:
 */
public class ChongFuException extends MiaoShaException {
    public ChongFuException() {
        super();
    }

    public ChongFuException(String message) {
        super(message);
    }

    public ChongFuException(String message, Throwable cause) {
        super(message, cause);
    }
}
