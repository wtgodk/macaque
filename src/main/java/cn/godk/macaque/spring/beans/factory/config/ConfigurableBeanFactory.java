package cn.godk.macaque.spring.beans.factory.config;

import cn.godk.macaque.spring.beans.factory.BeanFactory;

/**
 * @author wt
 * @program macaque
 * @create 2020-12-24  15:31
 */
public interface ConfigurableBeanFactory extends BeanFactory {

    ClassLoader getBeanClassLoader();

    void setBeanClassLoader(ClassLoader beanClassLoader);
}
