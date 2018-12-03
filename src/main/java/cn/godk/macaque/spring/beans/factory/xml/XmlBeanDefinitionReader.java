package cn.godk.macaque.spring.beans.factory.xml;

import cn.godk.macaque.spring.beans.factory.BeanFactory;

/**
 * @author: godk
 * @program: macaque
 * @create: 2018-12-03  13:28
 */
public class XmlBeanDefinitionReader  {

    public static final String ID_ATTRIBUTE = "id";
    public static final String CLASS_ATTRIBUTE = "class";

    private BeanFactory factory = null ;
    public XmlBeanDefinitionReader(BeanFactory factory) {
        factory = factory;
    }


   public BeanFactory loadBeanDefinitions(String configFile){

        return factory;
   }
}
