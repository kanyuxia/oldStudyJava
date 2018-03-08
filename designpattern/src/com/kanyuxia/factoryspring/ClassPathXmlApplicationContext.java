package com.kanyuxia.factoryspring;


import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by kanyuxia on 2017/3/14.
 */
public class ClassPathXmlApplicationContext implements BeanFactory {
    private Map<String, Object> map = new HashMap<>(20);
    private String xmlFile;

    @Override
    public Object getBean(String name) {
        Object object = map.get(name);
        if(object == null) {
            throw new NoSuchBeanDefinitionException(name);
        }
        return object;
    }

    ClassPathXmlApplicationContext(String xmlFile) throws Exception{
        this.xmlFile = xmlFile;
        readFromXmlFile();
    }

    /**
     * 从配置文件中读取并初始化bean对象,放到BeanFactory中
     */
    private void readFromXmlFile() throws Exception{
        File file = new File(xmlFile);
        SAXReader reader = new SAXReader();
        Document document = reader.read(file);
        Element root = document.getRootElement();
        Iterator iterator = root.elementIterator("bean");
        Element foo;
        while(iterator.hasNext()) {
            foo = (Element) iterator.next();
            String id = foo.attributeValue("id");
            String className = foo.attributeValue("class");
            initObject(id, className);
        }
    }

    /**
     * 初始化对象
     * @param id id
     * @param className className
     */
    private void initObject(String id, String className) throws Exception {
        Class c = Class.forName(className);
        Object object =  c.newInstance();
        map.put(id, object);
    }
}
