package cn.godk.macaque.spring.context;

import cn.godk.macaque.spring.beans.factory.BeanFactory;
import cn.godk.macaque.spring.beans.factory.config.ConfigurableBeanFactory;

/**
 * ApplicationContext interface
 */
public interface ApplicationContext extends BeanFactory {

    @Override
    Object getBean(String beanName);
}
