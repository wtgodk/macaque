package cn.godk.macaque.spring.beans.factory.annotation;

import cn.godk.macaque.spring.beans.BeanDefinition;
import cn.godk.macaque.spring.core.type.AnnotationMetadata;

/**
 * @author wt
 * @program macaque
 * @create 2021-01-21  15:46
 */
public interface AnnotatedBeanDefinition extends BeanDefinition {
    AnnotationMetadata getMetadata();

}
