package cn.godk.macaque.spring.beans.factory.xml;

import cn.godk.macaque.spring.aop.config.ConfigBeanDefinitionParser;
import cn.godk.macaque.spring.beans.BeanDefinition;
import cn.godk.macaque.spring.beans.ConstructorArgument;
import cn.godk.macaque.spring.beans.Exception.BeanDefinitionStoreException;
import cn.godk.macaque.spring.beans.GenericBeanDefinition;
import cn.godk.macaque.spring.beans.PropertyValue;
import cn.godk.macaque.spring.beans.factory.config.RuntimeBeanReference;
import cn.godk.macaque.spring.beans.factory.config.TypedStringValue;
import cn.godk.macaque.spring.beans.factory.support.BeanDefinitionRegistry;
import cn.godk.macaque.spring.context.context.ClassPathBeanDefinitionScanner;
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

    public static final String CONSTRUCTOR_ARG_ELEMENT = "constructor-arg";

    public static final String TYPE_ATTRIBUTE = "type";

    public static final String BEANS_NAMESPACE_URI = "http://www.springframework.org/schema/beans";

    public static final String CONTEXT_NAMESPACE_URI = "http://www.springframework.org/schema/context";
    public static final String AOP_NAMESPACE_URI = "http://www.springframework.org/schema/aop";
    private static final String BASE_PACKAGE_ATTRIBUTE = "base-package";
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
                if(isContextNamespace(ele.getNamespaceURI())){
                    parseComponentElement(ele);

                }else if(isDefaultNamespace(ele.getNamespaceURI())){
                    // 普通bean
                    parseDefaultElement(ele);
                }else if(this.isAOPNamespace(ele.getNamespaceURI())){
                    parseAOPElement(ele);  //例如 <aop:config>
                }

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

    private void parseAOPElement(Element ele) {
        ConfigBeanDefinitionParser parser = new ConfigBeanDefinitionParser();
        parser.parse(ele, this.registry);

    }

    private void parseComponentElement(Element ele) {
        String basePackages = ele.attributeValue(BASE_PACKAGE_ATTRIBUTE);
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry);
        scanner.doScan(basePackages);

    }
    public boolean isAOPNamespace(String namespaceUri){
        return (!StringUtils.hasLength(namespaceUri) || AOP_NAMESPACE_URI.equals(namespaceUri));
    }

    public boolean isDefaultNamespace(String namespaceUri) {
        return (!StringUtils.hasLength(namespaceUri) || BEANS_NAMESPACE_URI.equals(namespaceUri));
    }
    public boolean isContextNamespace(String namespaceUri){
        return (!StringUtils.hasLength(namespaceUri) || CONTEXT_NAMESPACE_URI.equals(namespaceUri));
    }
    private void parseDefaultElement(Element ele) {
        String id = ele.attributeValue(ID_ATTRIBUTE);
        String beanClassName = ele.attributeValue(CLASS_ATTRIBUTE);
        BeanDefinition bd = new GenericBeanDefinition(id,beanClassName);
        if (ele.attribute(SCOPE_ATTRIBUTE)!=null) {
            bd.setScope(ele.attributeValue(SCOPE_ATTRIBUTE));
        }
        parseConstructorArgument(ele,bd);
        parsePropertyValue(ele,bd);
        this.registry.registerBeanDefinition(id, bd);

    }
    /**
     * 功能描述: <br>
     * 〈〉  解析 构造函数参数
     * @param beanElem
     * @param bd
     * @return void
     * @author weitao
     * @date 2021/1/19 9:28
     */
    private void parseConstructorArgument(Element beanElem, BeanDefinition bd) {
        Iterator iter = beanElem.elementIterator(CONSTRUCTOR_ARG_ELEMENT);
        while(iter.hasNext()){
            Element ele = (Element) iter.next();
           // String ref = ele.attributeValue(REF_ATTRIBUTE);
            String type = ele.attributeValue(TYPE_ATTRIBUTE);
            ConstructorArgument.ValueHolder valueHolder = new ConstructorArgument.ValueHolder();
            Object o = parsePropertyValue(ele, bd, null);
            valueHolder.setValue(o);
            if(StringUtils.hasText(type)){
               valueHolder.setType(type);
            }

            bd.getConstructorArgument().add(valueHolder);


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
            Object o = parsePropertyValue(ele, bd,propertyName);
            bd.getPropertyValues().add(new PropertyValue(propertyName, o));
        }


    }

    private Object parsePropertyValue( Element ele, BeanDefinition bd, String propertyName) {
        String ref = ele.attributeValue(REF_ATTRIBUTE);
        String val = ele.attributeValue(VALUE_ATTRIBUTE);

        boolean hasRefAttribute = (ele.attribute(REF_ATTRIBUTE) != null);
        boolean hasValueAttribute = (ele.attribute(VALUE_ATTRIBUTE) != null);

        if (hasRefAttribute) {
            // ref 属性 注入bean
            RuntimeBeanReference runtimeBeanReference = new RuntimeBeanReference(ref);
            return runtimeBeanReference;
        //    bd.getPropertyValues().add(new PropertyValue(propertyName, runtimeBeanReference));
        } else if (hasValueAttribute) {
            // string 属性 注入 value
            TypedStringValue stringValue = new TypedStringValue(val);
      //      bd.getPropertyValues().add(new PropertyValue(propertyName, stringValue));
            return stringValue;
        } else {
            //格式不对
            String elementName = (propertyName != null) ?
                    "<property> element for property '" + propertyName + "'" :
                    "<constructor-arg> element";
            throw new RuntimeException(elementName + " must specify a ref or value");
        }
    }


}
