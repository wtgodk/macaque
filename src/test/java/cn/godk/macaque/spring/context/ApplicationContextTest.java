package cn.godk.macaque.spring.context;

import cn.godk.macaque.spring.context.support.ClassPathXmlApplicationContext;
import cn.godk.macaque.spring.service.IPetStoreService;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author wt
 * @program macaque
 * @create 2020-12-24  15:25
 */
public class ApplicationContextTest {

    @Test
    public void testApplicationContext(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("petStore.xml");
        Object petStore = applicationContext.getBean("petStore");
        Assert.assertNotNull(petStore);
        Assert.assertTrue(petStore instanceof IPetStoreService);
    }
}
