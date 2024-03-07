package com.sangeng.exception;

import com.sangeng.enums.AppHttpCodeEnum;

/**
 * @author: 甘廿甘廿
 * @create: 2023-10-21 19:26
 * @Description:
 */
public class SystemException extends RuntimeException{

    private int code;

    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public SystemException(AppHttpCodeEnum httpCodeEnum) {
        super(httpCodeEnum.getMsg());
        this.code = httpCodeEnum.getCode();
        this.msg = httpCodeEnum.getMsg();
    }

}