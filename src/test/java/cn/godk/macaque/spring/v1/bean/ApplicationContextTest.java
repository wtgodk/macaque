package cn.godk.macaque.spring.v1.bean;


import cn.godk.macaque.spring.context.ApplicationContext;
import cn.godk.macaque.spring.service.IPetStoreService;
import cn.godk.macaque.spring.support.ClassPathXmlApplicationContext;
import cn.godk.macaque.spring.support.FileSystemXMLApplicationContext;
import org.junit.Assert;
import org.junit.Test;

/**
 * beanFactory test case
 *
 * @author: godk
 * @program: macaque
 * @create: 2018-12-03  10:04
 */
public class ApplicationContextTest {
    @Test
    public void testGetBean() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v1.xml");
        IPetStoreService petStore = (IPetStoreService) ctx.getBean("petStore");
        Assert.assertNotNull(petStore);
    }

    @Test
    public void testGetBeanFromFileSystemContext() {

        ApplicationContext ctx = new FileSystemXMLApplicationContext("src\\test\\resources\\petstore-v1.xml");
        IPetStoreService petStore = (IPetStoreService) ctx.getBean("petStore");
        Assert.assertNotNull(petStore);

    }


}
