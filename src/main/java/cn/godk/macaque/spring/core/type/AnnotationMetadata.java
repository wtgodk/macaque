package cn.godk.macaque.spring.core.type;

import cn.godk.macaque.spring.core.annotation.AnnotationAttributes;

import java.util.Set;

/**
 * @author wt
 * @program macaque
 * @create 2021-01-21  14:47
 */
public interface AnnotationMetadata extends ClassMetadata{

    Set<String> getAnnotationTypes();


    boolean hasAnnotation(String annotationType);

    public AnnotationAttributes getAnnotationAttributes(String annotationType);


}
