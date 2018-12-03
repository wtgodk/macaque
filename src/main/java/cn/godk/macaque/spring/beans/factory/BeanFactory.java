package cn.godk.macaque.spring.beans.factory;

import cn.godk.macaque.spring.beans.BeanDefinition;

/**
 *  beanFactory interface
 *
 * @author: godk
 * @program: macaque
 * @create: 2018-12-03  10:33
 */
public interface BeanFactory {
    /**
     * get Bean by beanID
     * @param beanID
     * @return
     */
    BeanDefinition getBeanDefinition(String beanID);

    /**
     *  get bean by name
     * @param beanName
     * @return
     */
    Object getBean(String beanName);
}
