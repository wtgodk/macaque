package cn.godk.macaque.spring.bean;

import cn.godk.macaque.spring.context.ApplicationContext;
import cn.godk.macaque.spring.context.support.ClassPathXmlApplicationContext;
import cn.godk.macaque.spring.service.IPetStoreService;
import cn.godk.macaque.spring.service.PetStoreServiceImpl;
import org.junit.Assert;

/**
 *
 * beanFactory test case
 * @author: godk
 * @program: macaque
 * @create: 2018-12-03  10:04
 */
public class ApplicationContextTest {


    public void testGetBean( ){
        String path  = "";
        ApplicationContext applicationContext  = new ClassPathXmlApplicationContext(path);
        IPetStoreService petStore = (PetStoreServiceImpl)applicationContext.getBean("petStore");
        Assert.assertNotNull(petStore);
    }




}
