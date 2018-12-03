package cn.godk.macaque.spring.beans;

/**
 *
 *  generic BeanDefinition
 * @author: godk
 * @program: macaque
 * @create: 2018-12-03  11:22
 */
public class GenericBeanDefinition implements  BeanDefinition {

    private String id;
    private String beanClassName;

    public GenericBeanDefinition(String id, String beanClassName) {
        this.id = id;
        this.beanClassName = beanClassName;
    }
    public String getBeanClassName() {
        return this.beanClassName;
    }
}
