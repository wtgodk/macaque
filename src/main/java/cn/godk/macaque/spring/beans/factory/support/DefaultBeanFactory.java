package cn.godk.macaque.spring.beans.factory.support;

import cn.godk.macaque.spring.beans.BeanDefinition;
import cn.godk.macaque.spring.beans.Exception.BeanCreationException;
import cn.godk.macaque.spring.beans.Exception.BeanDefinitionStoreException;
import cn.godk.macaque.spring.beans.GenericBeanDefinition;
import cn.godk.macaque.spring.beans.factory.BeanFactory;
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
 *
 *  one of BeanFatory Implementation class
 *
 * @author: godk
 * @program: macaque
 * @create: 2018-12-03  11:10
 */
public class DefaultBeanFactory  implements BeanFactory {

    public static final String ID_ATTRIBUTE = "id";   // xml node name
    public static final String CLASS_ATTRIBUTE = "class";    // xml node name
    // cache xml file bean info
    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>(64);
    public  DefaultBeanFactory(String configPath){
                loadBeanDefinition(configPath); //load xml file get bean definition
    }

    public  DefaultBeanFactory(){

    }

    /**
     * xml file reader  use dom4j
     * @param configPath
     */
    private void loadBeanDefinition(String configPath) {

        InputStream resourceAsStream = null;
        try {
            ClassLoader classLoader = ClassUtils.getDefaultClassLoader();
             resourceAsStream = classLoader.getResourceAsStream(configPath);
            SAXReader saxReader = new SAXReader();
            Document doc = saxReader.read(resourceAsStream);
            Element root = doc.getRootElement(); //<beans>
            Iterator<Element> iTer = root.elementIterator();
            while(iTer.hasNext()){
                Element ele = (Element)iTer.next();
                String id = ele.attributeValue(ID_ATTRIBUTE);
                String beanClassName = ele.attributeValue(CLASS_ATTRIBUTE);
                BeanDefinition bd = new GenericBeanDefinition(id,beanClassName);  //create new BeanDeifintion
                this.beanDefinitionMap.put(id, bd);
            }
        } catch (DocumentException e) {
            throw new BeanDefinitionStoreException("IOException parsing XML document from " + configPath ,e);
        }finally {
            if(resourceAsStream!=null){
                try {
                    //close stream
                    resourceAsStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    public Object getBean(String beanName) throws BeanCreationException{
        BeanDefinition beanDefinition = this.getBeanDefinition(beanName);
        if(beanDefinition==null){
            throw new BeanCreationException("Bean Definition does not exist");
        }
        ClassLoader classLoader = ClassUtils.getDefaultClassLoader();
        String beanClassName = beanDefinition.getBeanClassName();  //get bean path
        Class<?> clazz = null;
        try {
            clazz = classLoader.loadClass(beanClassName);
            return clazz.newInstance();  // return a clazz instance
        } catch (Exception e) {
            throw new BeanCreationException("create bean "+ beanClassName + " failed ",e);
        }
    }

    public BeanDefinition getBeanDefinition(String beanName){
        return this.beanDefinitionMap.get(beanName);
    }
}
