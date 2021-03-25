package cn.godk.macaque.spring.beans.factory;

/**
 * @author wt
 * @program macaque
 * @create 2021-02-23  14:40
 */
public interface  FactoryBean<T> {


    T getObject() throws Exception;

    Class<?> getObjectType();

}

