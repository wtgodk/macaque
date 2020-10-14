package cn.godk.macaque.spring.context.support;

import cn.godk.macaque.spring.context.ApplicationContext;

/**
 * loading xml file  init context
 *
 * @author: godk
 * @program: macaque
 * @create: 2018-12-03  10:38
 */
public class ClassPathXmlApplicationContext implements ApplicationContext {
    /**
     * read xml file
     *
     * @param xmlPath
     */
    public ClassPathXmlApplicationContext(String xmlPath) {
        getResourceByPath(xmlPath);
    }

    public void getResourceByPath(String path) {


    }

    public Object getBean(String beanName) {
        return null;
    }
}
