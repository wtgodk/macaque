package cn.godk.macaque.spring.beans.factory.config;

import cn.godk.macaque.spring.beans.Exception.BeansException;

/**
 * @author wt
 * @program macaque
 * @create 2021-02-03  10:36
 */
public interface BeanPostProcessor {


    Object beforeInitialization(Object bean, String beanName) throws BeansException;


    Object afterInitialization(Object bean, String beanName) throws BeansException;

}
