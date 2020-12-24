package cn.godk.macaque.spring.beans.factory.config;

/**
 *
 *  单例bean内容注入
 * @author wt
 * @program macaque
 * @create 2020-12-24  14:42
 */
public interface SingletonBeanRegistry  {


    void registerSingleton(String beanName, Object singletonObject);

    Object getSingleton(String beanName);
}
