package cn.godk.macaque.spring.beans.factory.config;

import cn.godk.macaque.spring.beans.Exception.BeansException;

/**
 * @author wt
 * @program macaque
 * @create 2021-02-03  10:36
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {

    Object beforeInstantiation(Class<?> beanClass, String beanName) throws BeansException;

    boolean afterInstantiation(Object bean, String beanName) throws BeansException;

    void postProcessPropertyValues(Object bean, String beanName)
            throws BeansException;
}
