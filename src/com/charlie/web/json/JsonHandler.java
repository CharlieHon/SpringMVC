package com.charlie.web.json;

import com.charlie.web.json.entity.Dog;
import com.charlie.web.json.entity.User;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

// @ResponseBody可以写在Controller上对类进行注解，这样等价于在该类的所有方法上加上该注解
//@ResponseBody
//@Controller
@RestController // @ResponseBody + @Controller <==> @RestController
public class JsonHandler {

    /**
     * 1. 注解 @ResponseBody 表示返回的数据是json格式
     * 2. SpringMVC底层根据目标方法 @ResponseBody，返回指定格式，根据http请求来进行处理
     * 3. 底层原理在前面自定义@ResponseBody提到，这里原生的SpringMVC使用转换器
     * 4. HttpMessageConverter
     */
    @RequestMapping(value = "/json/dog")
    //@ResponseBody
    public Dog getJson() {
        // 返回对象，springmvc会根据设置，转成json格式数据
        Dog dog = new Dog();
        dog.setName("旺财");
        dog.setAddress("七里台");
        return dog;
    }

    // 编写方法，以json格式返回多个dog
    // @ResponseBody 支持返回集合
    @RequestMapping(value = "/json/dogs")
    //@ResponseBody
    public List<Dog> getJsons() {
        List<Dog> dogs = new ArrayList<>();
        dogs.add(new Dog("旺财", "七里台"));
        dogs.add(new Dog("小白", "八里台"));
        dogs.add(new Dog("阿宝", "张家界"));
        return dogs;
    }

    /**
     * 1. @RequestBody User user 在形参上加上注解 @RequestBody，springMVC就会将提交的json字符串数据填充给指定的JavaBean
     * 2. 如果不添加注解 @RequestBody 的话，返回的数据为 User{userName='null', age=null}
     */
    @RequestMapping(value = "/json/user")
    //@ResponseBody
    public User getUser(@RequestBody User user) {
        // 将前台传过来的数据，以json个格式返回
        System.out.println(user);
        return user;
    }

    // 编写方法，相应用户下载文件的请求
    // 构建一个ResponseEntity对象：1) 下载的文件(响应体) 2) http响应头的headers 3) http响应状态
    @RequestMapping(value = "/downFile")
    public ResponseEntity<byte[]> downFile(HttpSession session) throws IOException {
        // 1. 先获取到要下载文件的inputStream
        InputStream resourceAsStream = session.getServletContext().getResourceAsStream("/img/1.png");
        // 2. 开辟一个存放文件的byte数组，使用byte数组可以支持二进制数据(图片、视频)
        byte[] bytes = new byte[resourceAsStream.available()];
        // 3. 将要下载文件的数据，读入到byte[]
        resourceAsStream.read(bytes);
        // 4. 创建返回的HttpStatus
        HttpStatus httpStatus = HttpStatus.OK;
        // 5. 创建headers
        HttpHeaders headers = new HttpHeaders();
        // 指定返回的数据，客户端应当以附件形式处理
        // Content-Type：指示相应内容的格式；Content-Disposition：指示如何处理响应内容
        headers.add("Content-Disposition", "attachment;filename=1.png");

        // 构建ResponseEntity<T>
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(bytes, headers, httpStatus);
        return responseEntity;
    }
}
