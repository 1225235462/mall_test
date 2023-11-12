package com.ningdong.mall_test.member.exception;

public class UsernameExistException extends RuntimeException{
    public UsernameExistException(){
        super("用户名已经存在异常");
    }
}
