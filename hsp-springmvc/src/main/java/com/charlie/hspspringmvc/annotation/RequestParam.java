package com.charlie.hspspringmvc.annotation;

import java.lang.annotation.*;

// @RequestParam注解标注在目标方法的参数上，表示对应http请求参数
@Target(ElementType.PARAMETER)  // 作用在目标方法的参数上
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestParam {
    String value() default "";
}
