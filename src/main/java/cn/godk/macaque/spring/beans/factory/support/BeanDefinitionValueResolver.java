package cn.godk.macaque.spring.beans.factory.support;

import cn.godk.macaque.spring.beans.factory.config.ConfigurableBeanFactory;
import cn.godk.macaque.spring.beans.factory.config.RuntimeBeanReference;
import cn.godk.macaque.spring.beans.factory.config.TypedStringValue;

/**
 * bean 属性解析器
 *
 * @author wt
 * @program macaque
 * @create 2020-12-25  11:13
 */
public class BeanDefinitionValueResolver {


    private final ConfigurableBeanFactory factory;

    public BeanDefinitionValueResolver(ConfigurableBeanFactory factory) {
        this.factory = factory;
    }


    public Object resolveValueIfNecessary(Object value) {
        if (value instanceof RuntimeBeanReference) {
            RuntimeBeanReference runtimeBeanReference = (RuntimeBeanReference) value;
            String beanName = runtimeBeanReference.getBeanName();
            return factory.getBean(beanName);
        } else if (value instanceof TypedStringValue) {
            return ((TypedStringValue) value).getValue();
        } else {
            //todo 暂不支持其他类型
            throw new RuntimeException("the value " + value + " has not implemented");
        }

    }
}
