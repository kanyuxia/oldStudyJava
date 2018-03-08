package com.kanyuxia.factoryspring;


/**
 * Created by kanyuxia on 2017/3/14.
 */
public class Test {
    public static void main(String[] args) throws Exception {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("designpattern\\src\\ApplicationContext.xml");
        People people = (People) beanFactory.getBean("chinese");
        People people1 = (People) beanFactory.getBean("america");
        Vehicle vehicle = (Vehicle) beanFactory.getBean("plane");
        System.out.println(people.sayName());
        System.out.println(people1.sayName());
        System.out.println(vehicle);
    }
}
