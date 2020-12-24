package cn.godk.macaque.spring.beans.factory.support;

import cn.godk.macaque.spring.beans.BeanDefinition;
import cn.godk.macaque.spring.beans.Exception.BeanCreationException;
import cn.godk.macaque.spring.beans.Exception.BeanDefinitionStoreException;
import cn.godk.macaque.spring.beans.GenericBeanDefinition;
import cn.godk.macaque.spring.beans.factory.BeanFactory;
import cn.godk.macaque.spring.beans.factory.config.ConfigurableBeanFactory;
import cn.godk.macaque.spring.utils.ClassUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * one of BeanFatory Implementation class
 *
 * @author: godk
 * @program: macaque
 * @create: 2018-12-03  11:10
 */
public class DefaultBeanFactory extends  DefaultSingletonBeanRegistry implements ConfigurableBeanFactory,BeanDefinitionRegistry {

    /**
     * 功能描述: <br>
     * 〈〉  cache xml file bean info
     * @param null
     * @return
     * @author weitao
     * @date 2020/12/24 14:05
     */
    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>(64);
    private ClassLoader beanClassLoader;
    public DefaultBeanFactory() {

    }

    public Object getBean(String beanName) throws BeanCreationException {
        BeanDefinition beanDefinition = this.getBeanDefinition(beanName);
        if (beanDefinition == null) {
            throw new BeanCreationException("Bean Definition does not exist");
        }
        if(beanDefinition.isSingleton()){
            Object bean = this.getSingleton(beanName);
            if(bean == null){
                bean = createBean(beanDefinition);
                this.registerSingleton(beanName, bean);
            }
            return bean;
        }
        return createBean(beanDefinition);
    }




    private Object createBean( BeanDefinition beanDefinition) {

        String beanClassName = beanDefinition.getBeanClassName();
        Class<?> clazz = null;
        try {
            clazz = this.getBeanClassLoader().loadClass(beanClassName);
            // return a clazz instance
            return clazz.newInstance();
        } catch (Exception e) {
            throw new BeanCreationException("create bean " + beanClassName + " failed ", e);
        }
    }

    public BeanDefinition getBeanDefinition(String beanName) {
        return this.beanDefinitionMap.get(beanName);
    }

    public void registerBeanDefinition(String beanId, BeanDefinition bd) {
        this.beanDefinitionMap.put(beanId,bd);
    }

    public void setBeanClassLoader(ClassLoader beanClassLoader) {
        this.beanClassLoader = beanClassLoader;
    }

    public ClassLoader getBeanClassLoader() {
        return (this.beanClassLoader != null ? this.beanClassLoader : ClassUtils.getDefaultClassLoader());
    }
}
