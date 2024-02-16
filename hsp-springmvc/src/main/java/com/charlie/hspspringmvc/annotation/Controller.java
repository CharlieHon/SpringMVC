package com.charlie.hspspringmvc.annotation;

import java.lang.annotation.*;

/*
该注解用于标识一个控制器组件
1. @Target(ElementType.TYPE)：可以修饰类..
2. @Retention(RetentionPolicy.RUNTIME)：作用的时间范围
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Controller {
    String value() default "";
}
