package com.charlie.web.datavalid;

import com.charlie.web.datavalid.entity.Monster;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;


/**
 * MonsterHandler处理器响应用户提交数据
 * @Scope(value = "prototype") 表示每次请求MonsterHandler会生成一个新的对象
 */
@Controller
@Scope(value = "prototype")
public class MonsterHandler {
    /**
     * 显示添加monster的界面
     * 1. 这里的Map<String, Object> map，当向map中添加数据时会默认存放在request域中
     */
    @RequestMapping(value = "/addMonsterUI")
    public String addMonsterUI(Map<String, Object> map) {
        map.put("monster", new Monster());
        // 如果跳转的页面使用了SpringMVC标签，那么就需要准备一个对象，放入request域中，这个对象的属性名monster
        //      对应表单标签的 modelAttribute="monster"
        return "datavalid/monster_addUI";
    }

    /* 编写方法，处理添加妖怪
    1. SpringMVC可以将提交的数据，按照参数名和对象的属性名匹配
    2. 直接封装到对象中
    3. @Valid Monster monster 表示对monster接收的数据进行校验
    4. Errors errors 表示如果校验出现错误，将校验错误信息保存到errors
    5. Map<String, Object> map 表示如果校验出现错误，将校验的错误信息保存到map，提示保存monster对象
    6. 校验发生的时机：在springmvc底层，反射调用目标方法时，会接收http请求的数据，然后根据注解来进行验证，
        在验证过程中，如果出现了错误，就把错误信息填充errors和map。底层：反射+注解
     */
    @RequestMapping(value = "/save")
    public String save(@Valid Monster monster, Errors errors, Map<String, Object> map) {
        System.out.println("------monster------" + monster);
        // 为了看到校验的效果，输出map和errors
        System.out.println("======== map ========");
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            System.out.println("key=" + entry.getKey() + ", value=" + entry.getValue());
        }
        System.out.println("======== errors ========");
        if (errors.hasErrors()) {
            List<ObjectError> allErrors = errors.getAllErrors();
            for (ObjectError error : allErrors) {
                System.out.println("error=" + error);
            }
            return "datavalid/monster_addUI";
        }
        return "datavalid/success";
    }

    // 取消绑定monster的name表单提交的值给monster.name属性
    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        /*
        1. 方法上需要标注 @InitBinder，SpringMVC底层会初始化WebDataBinder
        2. 调用 webDataBinder.setDisallowedFields("name"); 表示取消指定属性的绑定，即
            当表单提交的字段为name时，就不再把接收到的name值填充到monster的name属性
        3. 机制：SpringMVC在底层通过反射调用方法时，会接收到http请求的参数和值，使用反射+注解技术，取消对指定属性的填充
        4. setDisallowedFields支持可变参数，可以填写多个字段，如 setDisallowedFields("name", "email");
        5. 如果取消某个属性绑定，验证也就没有意义了，应当把验证的注解去掉，否则可能会报错；name属性会使用默认值，即null
            // @NotEmpty
            private String name;
         */
        webDataBinder.setDisallowedFields("name");
    }
}
