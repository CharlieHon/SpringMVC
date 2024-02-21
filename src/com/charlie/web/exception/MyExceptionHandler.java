package com.charlie.web.exception;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MyExceptionHandler {
    /**
     * 1. localException方法用于处理局部异常
     * 2. 需要加上 @ExceptionHandler 注解，这里指定处理 算术异常和空指针异常
     * 3. Exception ex：生成的异常对象会传递给ex，通过ex可以得到相关的信息，这里可以加入自己的业务逻辑
     *
     * 当Handler中某方法中出现异常时，会首先在本类中寻找是否有@ExceptionHandler标注的异常处理方法，
     *  然后匹配注解中配置的异常类型，如果符合处理类型，则会将异常传给参数Exception ex并通过该方法进行异常如理；
     * 如果本类中无匹配的异常处理方法，就会查询@ControllerAdvice标注的异常类，进行全局异常处理
     */
    @ExceptionHandler({ArithmeticException.class, NullPointerException.class})
    public String localException(Exception ex, HttpServletRequest req) {
        System.out.println("局部异常信息：" + ex.getMessage());
        // 可以将异常信息带到下一个页面
        req.setAttribute("reason", ex.getMessage());
        return "exception_mes";
    }

    /**
     * 1. 编写方法，模拟异常(算术异常)
     * 2. 如果不做异常处理，则由tomcat默认页面显示，不太友好
     */
    @RequestMapping(value = "/testException01")
    public String test01(Integer num) {
        int i = 9 / num;
        return "success";
    }

    // 模拟抛出自定义异常AgeException
    @RequestMapping(value = "/testException02")
    public String test02() {
        throw new AgeException("年龄必须在1~120之间~~~");
    }

    // 模拟抛出数组越界异常
    @RequestMapping(value = "/testException03")
    public String test03() {
        int[] arr = new int[]{1, 8, 9, 5};
        // 抛出一个数组越界异常 ArrayIndexOutOfBoundsException
        System.out.println(arr[102]);
        return "success";
    }

    // 如果发生了没有归类的异常，可以给出统一提示页面
    @RequestMapping(value = "/testException04")
    public String test04() {
        String str = "China";
        // 这里会抛出StringIndexOutOfBoundsException，该异常没有匹配的局部/全局/自定义处理，
        // 而是作为未知异常的一种进行的统一异常处理，即 <prop key="java.lang.Exception">allEx</prop>
        char c = str.charAt(101);
        return "success";
    }

    @RequestMapping(value = "/testGlobalException")
    public String global() {
        // 1. 这里模拟了一个异常：NumberFormatException
        // 2. 该异常没有在局部异常处理，按照异常处理机制，就会交给全局异常处理类
        int num = Integer.parseInt("hello");
        return "success";
    }
}
