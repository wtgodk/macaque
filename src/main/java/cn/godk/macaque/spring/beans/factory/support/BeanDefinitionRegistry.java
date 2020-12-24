package cn.godk.macaque.spring.beans.factory.support;

import cn.godk.macaque.spring.beans.BeanDefinition;

/**
 *
 *  bean 注册获取  BeanDefinition
 *
 * @author wt
 * @program macaque
 * @create 2020-12-24  14:29
 */
public interface BeanDefinitionRegistry {


/**
 * 功能描述: <br>
 * 〈〉  获取 bean definition
 * @param beanId
 * @return cn.godk.macaque.spring.beans.BeanDefinition
 * @author weitao
 * @date 2020/12/24 14:29
 */
    BeanDefinition getBeanDefinition(String beanId);
    /**
     * 功能描述: <br>
     * 〈〉  注册 BeanDefinition
     * @param beanId
     * @param bd
     * @return void
     * @author weitao
     * @date 2020/12/24 14:30
     */
    void registerBeanDefinition(String beanId, BeanDefinition bd);
}
