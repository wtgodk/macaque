package cn.godk.macaque.spring.beans.factory.support;

import cn.godk.macaque.spring.beans.BeanDefinition;
import cn.godk.macaque.spring.beans.Exception.BeanCreationException;
import cn.godk.macaque.spring.beans.PropertyValue;
import cn.godk.macaque.spring.beans.SimpleTypeConverter;
import cn.godk.macaque.spring.beans.TypeConverter;
import cn.godk.macaque.spring.beans.factory.config.ConfigurableBeanFactory;
import cn.godk.macaque.spring.utils.ClassUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * one of BeanFatory Implementation class
 *
 * @author: godk
 * @program: macaque
 * @create: 2018-12-03  11:10
 */
public class DefaultBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory, BeanDefinitionRegistry {

    /**
     * 功能描述: <br>
     * 〈〉  cache xml file bean info
     *
     * @param null
     * @return
     * @author weitao
     * @date 2020/12/24 14:05
     */
    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>(64);
    private ClassLoader beanClassLoader;

    public DefaultBeanFactory() {

    }

    @Override
    public Object getBean(String beanName) throws BeanCreationException {
        BeanDefinition beanDefinition = this.getBeanDefinition(beanName);
        if (beanDefinition == null) {
            throw new BeanCreationException("Bean Definition does not exist");
        }
        if (beanDefinition.isSingleton()) {
            Object bean = this.getSingleton(beanName);
            if (bean == null) {
                bean = createBean(beanDefinition);
                this.registerSingleton(beanName, bean);
            }
            return bean;
        }
        return createBean(beanDefinition);
    }


    private Object createBean(BeanDefinition beanDefinition) {
        Object bean = instantiateBean(beanDefinition);
        // 属性注入

        populateBean(beanDefinition, bean);
        return bean;
    }


    /**
     * 功能描述: <br>
     * 〈〉  属性注入方法
     *
     * @param beanDefinition
     * @param bean
     * @return void
     * @author weitao
     * @date 2020/12/25 11:17
     */
    private void populateBean(BeanDefinition beanDefinition, Object bean) {
        // 所有属性
        List<PropertyValue> pvs = beanDefinition.getPropertyValues();
        if (pvs == null || pvs.isEmpty()) {
            return;
        }
        BeanDefinitionValueResolver valueResolver = new BeanDefinitionValueResolver(this);
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
            PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
            TypeConverter converter = new SimpleTypeConverter();
            for (PropertyValue pv : pvs) {
                Object o = valueResolver.resolveValueIfNecessary(pv.getValue());
                for (PropertyDescriptor pd : pds) {
                    if (pd.getName().equals(pv.getName())) {
                        pd.getWriteMethod().invoke(bean, converter.convertIfNecessary(o, pd.getPropertyType()));
                        break;
                    }
                }
            }
        } catch (Exception e) {
            throw new BeanCreationException("Failed to obtain BeanInfo for class [" + beanDefinition.getBeanClassName() + "]", e);
        }
    }

    /**
     * 功能描述: <br>
     * 〈〉  初始化bean
     *
     * @param bd
     * @return java.lang.Object
     * @author weitao
     * @date 2020/12/25 11:17
     */
    private Object instantiateBean(BeanDefinition bd) {
        ClassLoader cl = this.getBeanClassLoader();
        String beanClassName = bd.getBeanClassName();
        try {
            Class<?> clz = cl.loadClass(beanClassName);
            return clz.newInstance();
        } catch (Exception e) {
            throw new BeanCreationException("create bean for " + beanClassName + " failed", e);
        }
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        return this.beanDefinitionMap.get(beanName);
    }

    @Override
    public void registerBeanDefinition(String beanId, BeanDefinition bd) {
        this.beanDefinitionMap.put(beanId, bd);
    }

    @Override
    public ClassLoader getBeanClassLoader() {
        return (this.beanClassLoader != null ? this.beanClassLoader : ClassUtils.getDefaultClassLoader());
    }

    @Override
    public void setBeanClassLoader(ClassLoader beanClassLoader) {
        this.beanClassLoader = beanClassLoader;
    }
}
