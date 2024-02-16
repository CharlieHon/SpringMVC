package com.charlie.hspspringmvc.annotation;

import java.lang.annotation.*;

// 用于指定控制器-方法的映射路径
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {
    String value() default "";
}
