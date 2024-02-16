package com.charlie.hspspringmvc.xml;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;

// XMLParser用于解析spring配置文件
public class XMLParser {
    public static String getBasePackage(String xmlFile) {
        SAXReader saxReader = new SAXReader();
        // 得到类的加载路径，获取到spring配置文件[对应的资源流]
        InputStream inputStream = XMLParser.class.getClassLoader().getResourceAsStream(xmlFile);
        try {
            // 得到xmlFile文档
            Document document = saxReader.read(inputStream);
            Element rootElement = document.getRootElement();
            Element componentScanElement = rootElement.element("component-scan");
            // 获取component-scan的属性
            Attribute attribute = componentScanElement.attribute("base-package");
            String basePackage = attribute.getText();
            return basePackage;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
