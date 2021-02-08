package cn.godk.macaque.spring.beans.factory;

/**
 * beanFactory interface
 *
 * @author: godk
 * @program: macaque
 * @create: 2018-12-03  10:33
 */
public interface BeanFactory {


    /**
     * get bean by name
     *
     * @param beanName
     * @return
     */
    Object getBean(String beanName);

    Class<?> getType(String targetBeanName);
}
