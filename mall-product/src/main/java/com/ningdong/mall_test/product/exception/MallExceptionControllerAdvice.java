package com.ningdong.mall_test.product.exception;

import com.ningdong.common.exception.BizCodeEnume;
import com.ningdong.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice(basePackages = "com.ningdong.mall_test.product.controller")
public class MallExceptionControllerAdvice {
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public R handleVaildException(MethodArgumentNotValidException e){
        log.error("数据校验出现问题{}，异常类型{}",e.getMessage(),e.getClass());
        BindingResult bindingResult = e.getBindingResult();

        Map<String,String> errorMap = new HashMap<>();
        bindingResult.getFieldErrors().forEach((fieldError -> {
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        }));
        return R.error(BizCodeEnume.VAILD_EXCEPTION.getCode(),BizCodeEnume.VAILD_EXCEPTION.getMsg()).put("data",errorMap);
    }

    @ExceptionHandler(value = Throwable.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public R handleException(Throwable throwable){
        log.error("错误：",throwable);
        return R.error(BizCodeEnume.UNKNOW_EXCEPTION.getCode(),BizCodeEnume.UNKNOW_EXCEPTION.getMsg());
    }
}
