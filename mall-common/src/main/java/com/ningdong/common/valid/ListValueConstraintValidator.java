package com.ningdong.common.valid;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

//@Slf4j
public class ListValueConstraintValidator implements ConstraintValidator<ListValue,Integer> {
    private Set<Integer> set = new HashSet<>();

    @Override
    public void initialize(ListValue constraintAnnotation){
        int[] vals = constraintAnnotation.vals();
        for(int val: vals){
            set.add(val);
        }
    }

    /**
     *
     * @param value 需要校验的值
     * @param context
     * @return
     */
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context){

//        log.error("==========================:{}",value);
        if(value == null){
            return true;
        }else {
            return set.contains(value);
        }
    }
}
