package com.charlie.hspspringmvc.context;

import com.charlie.hspspringmvc.annotation.AutoWired;
import com.charlie.hspspringmvc.annotation.Controller;
import com.charlie.hspspringmvc.annotation.Service;
import com.charlie.hspspringmvc.xml.XMLParser;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// HspWebApplicationContext 是我们自己的spring容器
public class HspWebApplicationContext {
    // 保存扫描包/子包的类的全路径
    private List<String> classFullPathList = new ArrayList<>();
    // 定义属性ioc,存放反射生成的bean对象/Controller/Service
    public ConcurrentHashMap<String, Object> ioc = new ConcurrentHashMap<>();
    // 定义属性，表示spring容器配置文件
    private String configLocation;

    public HspWebApplicationContext() {
    }

    public HspWebApplicationContext(String configLocation) {
        this.configLocation = configLocation;
    }

    // 编写方法，完成自己spring容器的初始化
    public void init() {
        // 这里是通过硬编码写死了配置文件 => 动态的从web.xml中获取spring配置文件
        //String basePackage = XMLParser.getBasePackage("hspspringmvc.xml");
        String basePackage = XMLParser.getBasePackage(configLocation.split(":")[1]);
        // 这时 basePackage = com.charlie.controller,com.charlie.service
        String[] basePackages = basePackage.split(",");
        // 遍历basePackages,进行扫描
        for (String pack : basePackages) {
            scanPackage(pack);
        }
        System.out.println("classFullPathList=" + classFullPathList);
        // 将扫描到的类,反射注入到ioc容器
        executeInstance();
        System.out.println("扫描后的ioc容器=" + ioc);

        // 完成注入的bean对象属性的装配
        executeAutowired();
        System.out.println("装配后ioc容器=" + ioc);
    }

    // 创建方法完成对包的扫描，pack标识要扫描的包，如 "com.charlie.controller"
    public void scanPackage(String pack) {
        // 得到包所在的工作路径[绝对]，以下通过类加载器得到指定包对应的工作路径的绝对路径
        // com.charlie.controller => E:\SpringMVC\hsp-springmvc\target\classes\com\charlie\controller\
        // 提示：不要直接使用JUnit测试，否则url为null；应该启动tomcat进行测试，才能拿到真正运行时候的路径
        URL url =
                this.getClass().getClassLoader().getResource("/" + pack.replaceAll("\\.", "/"));
        // url=file:/E:/SpringMVC/hsp-springmvc/target/hsp-springmvc/WEB-INF/classes/com/charlie/controller/
        //System.out.println("url=" + url);
        // 根据得到的路径，对其进行扫描，保存到classFullPathList
        String path = url.getFile();
        // 在io中，把目录视为一个文件
        File dir = new File(path);
        // 遍历dir[文件/子目录]
        for (File f : dir.listFiles()) {
            if (f.isDirectory()) {
                // 如果是一个目录，需要递归扫描
                scanPackage(pack + "." + f.getName());
            } else {
                // 现在扫描的文件，可能是.class，也可能是其它文件；就算是.class文件，也可能不需要注入到容器
                // 目前先把文件的全路径都保存到集合，后面在注入对象到容器时，再处理
                String classFullPath = pack + "." + f.getName().replaceAll(".class", "");
                classFullPathList.add(classFullPath);
            }
        }
    }

    // 编写方法,将扫描的类在满足条件的情况下,反射到ioc容器
    public void executeInstance() {
        // 判断是否扫描到类
        if (classFullPathList.isEmpty()) {    // 说明没有扫描到类
            return;
        }
        try {
            // 遍历classFullPath,进行反射
            for (String classFullPath : classFullPathList) {
                Class<?> clazz = Class.forName(classFullPath);
                // 说明当前类有 @Controller
                if (clazz.isAnnotationPresent(Controller.class)) {
                    // 得到类名首字母小写
                    String beanName = clazz.getSimpleName().substring(0, 1).toLowerCase() + clazz.getSimpleName().substring(1);
                    ioc.put(beanName, clazz.newInstance());
                } // 如果有其它注解,可以扩展 => 处理 @Service注解
                else if (clazz.isAnnotationPresent(Service.class)) {
                    // 先获取到service的value值 => 即注入时的beanName
                    Service serviceAnnotation = clazz.getAnnotation(Service.class);
                    String beanName = serviceAnnotation.value();
                    if ("".equals(beanName)) {  // 说明没有指定value，就使用默认机制注入Service
                        // 可以通过接口名/类名(首字母小写)来注入ioc容器
                        // 1) 得到所有接口的名称
                        Class<?>[] interfaces = clazz.getInterfaces();
                        Object instance = clazz.newInstance();
                        // 2) 遍历接口，通过多个接口名来注入
                        for (Class<?> anInterface : interfaces) {
                            // 接口名 -> 首字母小写
                            String beanName2 =
                                    anInterface.getSimpleName().substring(0, 1).toLowerCase() + anInterface.getSimpleName().substring(1);
                            ioc.put(beanName2, instance);
                        }
                        // 3) 使用类名的首字母小写来注入bean
                        String beanName3 =
                                clazz.getSimpleName().substring(0, 1).toLowerCase() + clazz.getSimpleName().substring(1);
                        ioc.put(beanName3, instance);
                    } else {    // 如果有指定名称，就使用该名称注入即可
                        ioc.put(beanName, clazz.newInstance());
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 编写方法-完成属性的自动装配
    public void executeAutowired() {
        if (ioc.isEmpty()) {    // 判断ioc有没有要装配的对象
            return; // 也可以抛出异常： throw new RuntimeException("ioc 容器中没有bean对象");
        }
        // 遍历ioc容器中的所有注入的bean独享，然后获取bean的所有字段/属性，判断是否需要装配
        // entry => <String, Object> => String 就是注入对象时的名称，Object是bean对象
        for (Map.Entry<String, Object> entry : ioc.entrySet()) {
            //String key = entry.getKey();        // 如："monsterController"
            Object bean = entry.getValue();     // 如：monsterController对象
            // 得到bean的所有字段/属性
            Field[] declaredFields = bean.getClass().getDeclaredFields();
            for (Field declaredField : declaredFields) {
                // 判断当前字段是否有 @AutoWired 注解
                if (declaredField.isAnnotationPresent(AutoWired.class)) {
                    AutoWired autowiredAnnotation = declaredField.getAnnotation(AutoWired.class);
                    String beanName = autowiredAnnotation.value();
                    if ("".equals(beanName)) {
                        // 即得到字段类型的首字母小写作为bean名字来装配
                        String simpleName = declaredField.getType().getSimpleName();
                        beanName = simpleName.substring(0, 1).toLowerCase() + simpleName.substring(1);
                    }
                    // 从ioc容器中获取到bean
                    if (null == ioc.get(beanName)) {    // 没拿到，说明指定的名字对应的bean不在ioc容器中
                        throw new RuntimeException("ioc容器中，不存在你要装配的bean");
                    }
                    // 因为属性可能是 private，所以要暴力破解
                    declaredField.setAccessible(true);
                    // 可以装配属性 字段.set(Controller, Service)
                    try {
                        /****************** 通过反射设置字段值 ***********************/
                        declaredField.set(bean, ioc.get(beanName));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } // 字段上没有 @AutoWired 注解，则不需要进行处理
            }
        }
    }
}
