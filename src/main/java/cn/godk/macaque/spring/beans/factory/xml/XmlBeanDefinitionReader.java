package cn.godk.macaque.spring.beans.factory.xml;

import cn.godk.macaque.spring.beans.BeanDefinition;
import cn.godk.macaque.spring.beans.Exception.BeanDefinitionStoreException;
import cn.godk.macaque.spring.beans.GenericBeanDefinition;
import cn.godk.macaque.spring.beans.factory.BeanFactory;
import cn.godk.macaque.spring.beans.factory.support.BeanDefinitionRegistry;
import cn.godk.macaque.spring.core.io.Resource;
import cn.godk.macaque.spring.utils.ClassUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 *
 *
 * @author: godk
 * @program: macaque
 * @create: 2018-12-03  13:28
 */
public class XmlBeanDefinitionReader {

    /**
     * 功能描述: <br>
     * 〈〉 xml node name
     * @param null
     * @return
     * @author weitao
     * @date 2020/12/24 14:05
     */
    public static final String ID_ATTRIBUTE = "id";
    /**
     * 功能描述: <br>
     * 〈〉xml node name
     * @param null
     * @return
     * @author weitao
     * @date 2020/12/24 14:05
     */
    public static final String CLASS_ATTRIBUTE = "class";


    private BeanDefinitionRegistry registry = null;

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }


    public void loadBeanDefinitions(Resource resource) {
        InputStream resourceAsStream = null;
        try {
            resourceAsStream =  resource.getInputStream();
            SAXReader saxReader = new SAXReader();
            Document doc = saxReader.read(resourceAsStream);
            // //<beans>
            Element root = doc.getRootElement();
            Iterator<Element> iTer = root.elementIterator();
            while (iTer.hasNext()) {
                Element ele = (Element) iTer.next();
                String id = ele.attributeValue(ID_ATTRIBUTE);
                String beanClassName = ele.attributeValue(CLASS_ATTRIBUTE);
                //create new BeanDeifintion
                BeanDefinition bd = new GenericBeanDefinition(id, beanClassName);
                registry.registerBeanDefinition(id, bd);
            }
        } catch (Exception e) {
            throw new BeanDefinitionStoreException("IOException parsing XML document from " + resource.getDescription(), e);
        }  finally {
            if (resourceAsStream != null) {
                try {
                    resourceAsStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
