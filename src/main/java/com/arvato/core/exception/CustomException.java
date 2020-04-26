package com.arvato.core.exception;

/**
 *  自定义异常类
 */
public class CustomException extends RuntimeException{

    private Integer code;//自定义异常码

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public CustomException(String message, Integer code) {
        super(message);// 父类的构造函数；调用底层的Throwable的构造函数，将参数message赋值到detailMessage (Throwable的属性)
        this.code = code;//赋值code码
    }
}
