package cn.godk.macaque.spring.context.context;

import cn.godk.macaque.spring.beans.GenericBeanDefinition;
import cn.godk.macaque.spring.beans.factory.annotation.AnnotatedBeanDefinition;
import cn.godk.macaque.spring.core.type.AnnotationMetadata;

/**
 * @author wt
 * @program macaque
 * @create 2021-01-21  15:47
 */
public class ScannedGenericBeanDefinition extends GenericBeanDefinition implements AnnotatedBeanDefinition {

    private final AnnotationMetadata annotationMetadata;

    public ScannedGenericBeanDefinition(AnnotationMetadata annotationMetadata) {
        super(null, annotationMetadata.getClassName());
        this.annotationMetadata = annotationMetadata;

    }
    @Override
    public AnnotationMetadata getMetadata() {
        return annotationMetadata;
    }
}
