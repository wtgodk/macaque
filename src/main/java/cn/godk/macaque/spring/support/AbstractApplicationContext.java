package cn.godk.macaque.spring.support;

import cn.godk.macaque.spring.beans.factory.annotation.AutowiredAnnotationProcessor;
import cn.godk.macaque.spring.beans.factory.config.ConfigurableBeanFactory;
import cn.godk.macaque.spring.beans.factory.support.DefaultBeanFactory;
import cn.godk.macaque.spring.beans.factory.xml.XmlBeanDefinitionReader;
import cn.godk.macaque.spring.context.ApplicationContext;
import cn.godk.macaque.spring.core.io.Resource;
import cn.godk.macaque.spring.utils.ClassUtils;

/**
 * @author wt
 * @program macaque
 * @create 2020-12-24  15:16
 */
public abstract class AbstractApplicationContext implements ApplicationContext {

    private DefaultBeanFactory factory = null;
    private ClassLoader beanClassLoader;

    public AbstractApplicationContext(String configFile) {
        factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = this.getResourceByPath(configFile);
        reader.loadBeanDefinitions(resource);
        factory.setBeanClassLoader(this.getBeanClassLoader());
        registerBeanPostProcessors(this.factory);
    }

    /**
     * 功能描述: <br>
     * 〈〉  资源文件载入
     *
     * @param configFile
     * @return cn.godk.macaque.spring.core.io.Resource
     * @author weitao
     * @date 2020/12/24 15:23
     */
    protected abstract Resource getResourceByPath(String configFile);


    @Override
    public Object getBean(String beanName) {
        return this.factory.getBean(beanName);
    }


    public ClassLoader getBeanClassLoader() {
        return (this.beanClassLoader != null ? this.beanClassLoader : ClassUtils.getDefaultClassLoader());
    }

    protected void registerBeanPostProcessors(ConfigurableBeanFactory beanFactory) {

        AutowiredAnnotationProcessor postProcessor = new AutowiredAnnotationProcessor();
        postProcessor.setBeanFactory(beanFactory);
        beanFactory.addBeanPostProcessor(postProcessor);

    }
//
//    @Override
//    public void setBeanClassLoader(ClassLoader beanClassLoader) {
//        this.beanClassLoader = beanClassLoader;
//    }

    @Override
    public Class<?> getType(String targetBeanName) {
        return this.getType(targetBeanName);
    }
}
