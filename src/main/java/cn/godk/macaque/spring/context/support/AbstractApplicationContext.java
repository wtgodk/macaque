package cn.godk.macaque.spring.context.support;

import cn.godk.macaque.spring.beans.BeanDefinition;
import cn.godk.macaque.spring.beans.factory.support.DefaultBeanFactory;
import cn.godk.macaque.spring.beans.factory.xml.XmlBeanDefinitionReader;
import cn.godk.macaque.spring.context.ApplicationContext;
import cn.godk.macaque.spring.core.io.Resource;

/**
 * @author wt
 * @program macaque
 * @create 2020-12-24  15:16
 */
public abstract class AbstractApplicationContext implements ApplicationContext {

    private DefaultBeanFactory factory = null;
    private ClassLoader beanClassLoader;

    public AbstractApplicationContext(String configFile){
        factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = this.getResourceByPath(configFile);
        reader.loadBeanDefinitions(resource);
        factory.setBeanClassLoader(this.getBeanClassLoader());
    }
/**
 * 功能描述: <br>
 * 〈〉  资源文件载入
 * @param configFile
 * @return cn.godk.macaque.spring.core.io.Resource
 * @author weitao
 * @date 2020/12/24 15:23
 */
    protected abstract Resource getResourceByPath(String configFile);


    public Object getBean(String beanName) {
        return this.factory.getBean(beanName);
    }

    public ClassLoader getBeanClassLoader() {
        return beanClassLoader;
    }

    public void setBeanClassLoader(ClassLoader beanClassLoader) {
        this.beanClassLoader = beanClassLoader;
    }


}
