package cn.godk.macaque.spring.beans.factory.xml;

import cn.godk.macaque.spring.beans.BeanDefinition;
import cn.godk.macaque.spring.beans.Exception.BeanDefinitionStoreException;
import cn.godk.macaque.spring.beans.GenericBeanDefinition;
import cn.godk.macaque.spring.beans.PropertyValue;
import cn.godk.macaque.spring.beans.factory.config.RuntimeBeanReference;
import cn.godk.macaque.spring.beans.factory.config.TypedStringValue;
import cn.godk.macaque.spring.beans.factory.support.BeanDefinitionRegistry;
import cn.godk.macaque.spring.core.io.Resource;
import cn.godk.macaque.spring.utils.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * @author: godk
 * @program: macaque
 * @create: 2018-12-03  13:28
 */
public class XmlBeanDefinitionReader {
    /**
     * 功能描述: <br>
     * 〈〉 xml node name
     *
     * @param null
     * @return
     * @author weitao
     * @date 2020/12/24 14:05
     */
    public static final String ID_ATTRIBUTE = "id";
    /**
     * 功能描述: <br>
     * 〈〉xml node name
     *
     * @param null
     * @return
     * @author weitao
     * @date 2020/12/24 14:05
     */
    public static final String CLASS_ATTRIBUTE = "class";
    public static final String PROPERTY_ELEMENT = "property";
    public static final String REF_ATTRIBUTE = "ref";
    public static final String VALUE_ATTRIBUTE = "value";
    public static final String NAME_ATTRIBUTE = "name";
    public static final String SCOPE_ATTRIBUTE = "scope";
    protected final Log logger = LogFactory.getLog(getClass());
    private BeanDefinitionRegistry registry = null;

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }


    public void loadBeanDefinitions(Resource resource) {
        InputStream resourceAsStream = null;
        try {
            resourceAsStream = resource.getInputStream();
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
                //获取 scope
                if (ele.attribute(SCOPE_ATTRIBUTE) != null) {
                    bd.setScope(ele.attributeValue(SCOPE_ATTRIBUTE));
                }
                // 获取 property
                parsePropertyValue(ele, bd);

            }
        } catch (Exception e) {
            throw new BeanDefinitionStoreException("IOException parsing XML document from " + resource.getDescription(), e);
        } finally {
            if (resourceAsStream != null) {
                try {
                    resourceAsStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void parsePropertyValue(Element beanElem, BeanDefinition bd) {
        Iterator iter = beanElem.elementIterator(PROPERTY_ELEMENT);
        while (iter.hasNext()) {
            Element ele = (Element) iter.next();
            String propertyName = ele.attributeValue(NAME_ATTRIBUTE);
            if (!StringUtils.hasLength(propertyName)) {
                logger.fatal("Tag 'property' must have a 'name' attribute");
                return;
            }
            //create new BeanDeifintion
            String ref = ele.attributeValue(REF_ATTRIBUTE);
            String val = ele.attributeValue(VALUE_ATTRIBUTE);
            boolean hasRefAttribute = (ele.attribute(REF_ATTRIBUTE) != null);
            boolean hasValueAttribute = (ele.attribute(VALUE_ATTRIBUTE) != null);

            if (hasRefAttribute) {
                // ref 属性 注入bean
                RuntimeBeanReference runtimeBeanReference = new RuntimeBeanReference(ref);
                bd.getPropertyValues().add(new PropertyValue(propertyName, runtimeBeanReference));
            } else if (hasValueAttribute) {
                // string 属性 注入 value
                TypedStringValue stringValue = new TypedStringValue(val);
                bd.getPropertyValues().add(new PropertyValue(propertyName, stringValue));
            } else {
                //格式不对
                String elementName = (propertyName != null) ?
                        "<property> element for property '" + propertyName + "'" :
                        "<constructor-arg> element";
                throw new RuntimeException(elementName + " must specify a ref or value");
            }
        }


    }


}
