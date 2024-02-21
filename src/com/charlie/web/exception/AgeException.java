package com.charlie.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 注解信息是交由tomcat默认异常页面进行处理的
@ResponseStatus(reason = "年龄需要在1~120之间", value = HttpStatus.BAD_REQUEST)
public class AgeException extends RuntimeException {
    public AgeException() {}

    public AgeException(String message) {
        super(message);
    }
}
