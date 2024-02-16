package com.charlie.test;

import com.charlie.hspspringmvc.xml.XMLParser;
import org.junit.Test;

public class HspSpringMVCTest {
    @Test
    public void readXML() {
        String basePackage = XMLParser.getBasePackage("hspspringmvc.xml");
        // basePackage=com.charlie.controller
        System.out.println("basePackage=" + basePackage);
    }
}
