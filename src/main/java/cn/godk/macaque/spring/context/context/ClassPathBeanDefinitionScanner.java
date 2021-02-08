package cn.godk.macaque.spring.context.context;

import cn.godk.macaque.spring.beans.BeanDefinition;
import cn.godk.macaque.spring.beans.Exception.BeanDefinitionStoreException;
import cn.godk.macaque.spring.beans.factory.annotation.AnnotatedBeanDefinition;
import cn.godk.macaque.spring.beans.factory.support.BeanDefinitionRegistry;
import cn.godk.macaque.spring.beans.factory.support.BeanNameGenerator;
import cn.godk.macaque.spring.beans.factory.support.DefaultBeanFactory;
import cn.godk.macaque.spring.core.io.PackageResourceLoader;
import cn.godk.macaque.spring.core.io.Resource;
import cn.godk.macaque.spring.core.type.AnnotationMetadata;
import cn.godk.macaque.spring.core.type.classreading.MetadataReader;
import cn.godk.macaque.spring.core.type.classreading.SimpleMetadataReader;
import cn.godk.macaque.spring.stereotype.Component;
import cn.godk.macaque.spring.utils.StringUtils;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author wt
 * @program macaque
 * @create 2021-01-21  15:49
 */
public class ClassPathBeanDefinitionScanner {


    BeanDefinitionRegistry registry;
    PackageResourceLoader loader = new PackageResourceLoader();

    private BeanNameGenerator  beanNameGenerator = new AnnotationBeanNameGenerator();
    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public Set<BeanDefinition> doScan(String packagesToScan) {
        String[] basePackages = StringUtils.tokenizeToStringArray(packagesToScan,",");
        Set<BeanDefinition> beanDefinitions = new LinkedHashSet<BeanDefinition>();
        if(basePackages!=null && basePackages.length>0){
            for (String basePackage : basePackages) {
                Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
                for (BeanDefinition candidate : candidates) {
                    this.registry.registerBeanDefinition(candidate.getID(),candidate);
                    beanDefinitions.add(candidate);
                }


            }
        }
return beanDefinitions;

    }

    private Set<BeanDefinition> findCandidateComponents(String basePackage){
        Set<BeanDefinition> beanDefinitions = new LinkedHashSet<BeanDefinition>();
        Resource[] resources = loader.getResources(basePackage);
        if(resources!=null && resources.length>0){

            for (Resource resource : resources) {
                try {
                MetadataReader reader = new SimpleMetadataReader(resource);

                if(reader.getAnnotationMetadata().hasAnnotation(Component.class.getName())){
                    // 有 Component 注解
                    ScannedGenericBeanDefinition scannedGenericBeanDefinition = new ScannedGenericBeanDefinition(reader.getAnnotationMetadata());
                    String name  = beanNameGenerator.generateBeanName(scannedGenericBeanDefinition, registry);
                    scannedGenericBeanDefinition.setID(name);
                    beanDefinitions.add(scannedGenericBeanDefinition);
                }
            }catch (Throwable ex) {
                    throw new BeanDefinitionStoreException(
                            "Failed to read candidate component class: " + resource, ex);
                }
            }

        }
        return beanDefinitions;
    }
}
