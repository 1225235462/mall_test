package com.ningdong.mall_test.member.exception;

public class PhoneExistException extends RuntimeException {
    public PhoneExistException(){
        super("手机号已经存在异常");
    }
}
