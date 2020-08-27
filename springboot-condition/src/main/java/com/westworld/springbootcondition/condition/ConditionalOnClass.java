package com.westworld.springbootcondition.condition;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})       //注解作用的地方
@Retention(RetentionPolicy.RUNTIME)                   //注解生效的时机
@Documented
@Conditional(MyCondition.class)
public @interface ConditionalOnClass {
    String[] value();
}
