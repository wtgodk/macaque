package cn.godk.macaque.spring.bean;

import cn.godk.macaque.spring.beans.BeanDefinition;
import cn.godk.macaque.spring.beans.Exception.BeanCreationException;
import cn.godk.macaque.spring.beans.Exception.BeanDefinitionStoreException;
import cn.godk.macaque.spring.beans.factory.BeanFactory;
import cn.godk.macaque.spring.beans.factory.support.DefaultBeanFactory;
import cn.godk.macaque.spring.beans.factory.xml.XmlBeanDefinitionReader;
import cn.godk.macaque.spring.service.IPetStoreService;
import cn.godk.macaque.spring.service.PetStoreServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * BeanFactory test case
 *
 * @author: godk
 * @program: macaque
 * @create: 2018-12-03  11:07
 */
public class BeanFactoryTest {

    DefaultBeanFactory factory = null;
    XmlBeanDefinitionReader reader = null;

    @Before
    public void setUp() {
        factory = new DefaultBeanFactory();
        reader = new XmlBeanDefinitionReader(factory);

    }

    /**
     * getBean  method test
     */
    @Test
    public void getBean() {
        BeanDefinition bd = factory.getBeanDefinition("petStore");
        IPetStoreService petStore = (PetStoreServiceImpl) factory.getBean("petStore");
        Assert.assertEquals("cn.godk.macaque.spring.service.PetStoreServiceImpl", bd.getBeanClassName());
        Assert.assertNotNull(petStore);
    }

    @Test
    public void testInvalidBean() {

        BeanFactory bf = reader.loadBeanDefinitions("petstore-v1.xml");
        try {

            Object object = bf.getBean("invalid");
        } catch (BeanCreationException e) {
            e.printStackTrace();
            return;
        }
        Assert.fail();


    }

    @Test
    public void testInvalidXML() {
        try {
            reader.loadBeanDefinitions("xxx.xml");
        } catch (BeanDefinitionStoreException e) {
            return;
        }
        Assert.fail("expect BeanDefinitionStoreException ");
    }


}
