package com.charlie.web.requestparam;

import com.charlie.web.requestparam.entity.Master;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/vote")
public class VoteHandler {
    /* @RequestParam(value = "name", required = false)
    1. 获取到超链接传递的数据，请求 http://localhost:8080/springmvc/vote/vote01?name=charlie
    2. @RequestParam 表示会接收提交的参数
    3. value 表示提交的参数名是 name
    4. required=false 表示该参数可以没有；默认true，表示必须有这个参数
    5. 当使用了 @RequestParam(value = "name", required = false) 就将参数name指定给传入形参
     */
    @RequestMapping("/vote01")
    public String test01(@RequestParam(value = "name", required = false) String username) {
        System.out.println("得到的username=" + username);
        return "success";
    }

    /*
    需求：获取http请求头消息，获取到 Accept-Encoding 和 Host
    1. 这个涉及到前面讲过的http协议
    2. @RequestHeader("Http请求头字段")
     */
    @RequestMapping("/vote02")
    public String test02(@RequestHeader("Accept-Encoding") String ae, @RequestHeader("Host") String host) {
        System.out.println("Accept-Encoding=" + ae);
        System.out.println("Host=" + host);
        return "success";
    }

    /*
    演示如何获取到提交的数据->封装成java对象
    1. 方法的形参用对应的类型来指定即可，SpringMVC会自动地进行封装
    2. 如果要自动地完成封装，要求提交地数据参数名和对象地字段名保持一致
    3. 如果属性是对象，这里仍然是通过 字段名.字段名 来赋值，比如 Master[pet]，
        提交地数据参数名是 pet.id, pet.name
    4. 如果提交地数据的参数名和对象的字段名不匹配，则对象的属性值就是 null
    5. 底层是 反射+注解
     */
    @RequestMapping("/vote03")
    public String test03(Master master) {
        System.out.println("master=" + master);
        return "success";
    }

    // 获取servlet api，来获取提交的数据
    @RequestMapping("/vote04")
    public String test04(HttpServletRequest req, HttpServletResponse resp,
                         HttpSession hs) {

        // 获取到session
        HttpSession session = req.getSession();
        System.out.println("session=" + session);
        // 注意：通过参数传入的hs和req.getSession()得到的session对象是同一个对象
        System.out.println("hs=" + hs);

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        System.out.println("username=" + username + "\npassword=" + password);
        return "success";
    }

    /* 演示将提交的数据封装到java对象，springmvc会自动将其放入到request域
    1. springmvc会自动将获取的model模型，放入到request域
    2. 也可以手动将master放入到request域，即 ("master", master)
    3. 这样就可以在跳转到的页面(vote_ok.jsp)，取出数据
     */
    @RequestMapping("/vote05")
    public String test05(Master master, HttpServletRequest req) {
        // 1) 手动添加request域数据
        req.setAttribute("address", "TianJin");
        // 2) 也可以修改master的属性值，根据引用机制，域中的数据也会改变
        master.setName("Charlie");
        // 3) springmvc默认存放对象到request域中，属性名是通过 request域 ("master", master)
        //      属性名：类型名首字母小写
        return "vote_ok";
    }

    // 演示通过Map<String, Object> 设置数据到request域
    @RequestMapping("/vote06")
    public String test06(Master master, Map<String, Object> map) {
        // 1. 需求：通过map对象，添加属性到request域
        // 2. 原理分析：springmvc会遍历map，将map的k-v存放到request域
        map.put("address", "HK");
        //map.put("master", null);  // 将request中k为master的值置为null
        return "vote_ok";
    }

    // 演示通过返回ModelAndView对象，将数据放入到request域
    @RequestMapping("/vote07")
    public ModelAndView test07(Master master) {
        ModelAndView modelAndView = new ModelAndView();
        // 放入属性到modelAndView对象，最终也会放入到request域中
        modelAndView.addObject("address", "CN");
        // 可以把从数据库中得到的数据对象，放入到modelAndView[service-dao-db]
        // 这里指定跳转的视图名称
        modelAndView.setViewName("vote_ok");
        return modelAndView;
    }

    // 演示如何将数据设置到session中
    @RequestMapping("/vote08")
    public String test08(Master master, HttpSession httpSession) {
        // master对象是默认放在request域中，以下将对象放入到session中
        httpSession.setAttribute("master", master);
        httpSession.setAttribute("address", "JP");
        return "vote_ok";
    }

    /*
    1. 当Handler的方法被表示 @ModelAttribute
    2. 当调用该Handler的其它方法时，都会先执行该前置方法
    3. 类似于Spring的AOP的前置通知
    4. prepareModel配置方法，会切入到其它方法前执行...
     */
    @ModelAttribute
    public void prepareModel() {
        System.out.println("prepareModel()-------完成准备工作----------");
    }
}
