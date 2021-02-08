package cn.godk.macaque.spring.beans.factory.config;



/**
 * @author wt
 * @program macaque
 * @create 2020-12-24  15:31
 */
public interface ConfigurableBeanFactory extends AutowireCapableBeanFactory {

    ClassLoader getBeanClassLoader();

    void setBeanClassLoader(ClassLoader beanClassLoader);
}
