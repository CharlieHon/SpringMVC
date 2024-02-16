package com.charlie.hspspringmvc.annotation;

import java.lang.annotation.*;

// Service注解，用于标识一个Service对象，并注入到spring容器
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Service {
    String value() default "";
}
