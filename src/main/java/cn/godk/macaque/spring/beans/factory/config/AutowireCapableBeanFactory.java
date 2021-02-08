package cn.godk.macaque.spring.beans.factory.config;

import cn.godk.macaque.spring.beans.factory.BeanFactory;
import cn.godk.macaque.spring.beans.factory.annotation.AutowiredAnnotationProcessor;

/**
 * @author wt
 * @program macaque
 * @create 2021-02-03  09:27
 */
public interface AutowireCapableBeanFactory extends BeanFactory {

    public Object resolveDependency(DependencyDescriptor descriptor);

    void addBeanPostProcessor(BeanPostProcessor postProcessor);
}
