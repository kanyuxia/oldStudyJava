package com.kanyuxia.factoryspring;

import org.springframework.beans.BeansException;

/**
 * Created by kanyuxia on 2017/3/14.
 */
public interface BeanFactory {
    Object getBean(String name) throws BeansException;
}
