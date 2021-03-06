package cn.godk.macaque.spring.beans;

import cn.godk.macaque.spring.aop.config.MethodLocatingFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * generic BeanDefinition
 *
 * @author: godk
 * @program: macaque
 * @create: 2018-12-03  11:22
 */
public class GenericBeanDefinition implements BeanDefinition {

    List<PropertyValue> propertyValues = new ArrayList<PropertyValue>();
    private String id;
    private String beanClassName;
    private Class<?> beanClass;
    private boolean singleton = true;
    private boolean prototype = false;
    private String scope = SCOPE_DEFAULT;
    private ConstructorArgument  constructorArgument  = new ConstructorArgument();
    // 是否为手动合成的beanDefinition
    private boolean isSynthetic = false;

    public GenericBeanDefinition(String id, String beanClassName) {
        this.id = id;
        this.beanClassName = beanClassName;
    }

    public GenericBeanDefinition(Class<?> clz) {
        // bean class   object.class
        this.beanClass = clz;
        // class 全路径 cn.godk.....
        this.beanClassName = clz.getName();
    }

    @Override
    public String getBeanClassName() {
        return this.beanClassName;
    }

    @Override
    public boolean isSingleton() {
        return this.singleton;
    }

    @Override
    public boolean isPrototype() {
        return this.prototype;
    }

    @Override
    public String getScope() {
        return this.scope;
    }

    @Override
    public void setScope(String scope) {
        this.scope = scope;
        this.singleton = SCOPE_SINGLETON.equals(scope) || SCOPE_DEFAULT.equals(scope);
        this.prototype = SCOPE_PROTOTYPE.equals(scope);
    }

    @Override
    public List<PropertyValue> getPropertyValues() {
        return this.propertyValues;
    }

    @Override
    public ConstructorArgument getConstructorArgument() {
        return constructorArgument;
    }

    @Override
    public Class<?> getBeanClass() throws IllegalStateException {
        return beanClass;
    }

    @Override
    public Class<?> resolveBeanClass(ClassLoader classLoader) throws ClassNotFoundException {
        String className = getBeanClassName();
        if (className == null) {
            return null;
        }
        Class<?> beanClass = classLoader.loadClass(className);
        this.beanClass = beanClass;
        return beanClass;
    }

    @Override
    public boolean isSynthetic() {
        return isSynthetic;
    }

    public void setSynthetic(boolean synthetic) {
        isSynthetic = synthetic;
    }

    public void setID(String id) {
        this.id = id;
    }

    @Override
    public String getID() {
        return id;
    }
    @Override
    public boolean hasBeanClass(){
        return this.beanClass != null;
    }

}
