package cn.godk.macaque.spring.v1.context.support;

import cn.godk.macaque.spring.core.io.ClassPathResource;
import cn.godk.macaque.spring.core.io.Resource;

/**
 * loading xml file  init context
 *
 * @author: godk
 * @program: macaque
 * @create: 2018-12-03  10:38
 */
public class ClassPathXmlApplicationContext extends AbstractApplicationContext {
    /**
     * read xml file
     *
     * @param xmlPath
     */
    public ClassPathXmlApplicationContext(String xmlPath) {
        super(xmlPath);
    }

    @Override
    public Resource getResourceByPath(String path) {
       return new ClassPathResource(path);

    }



}
