package cn.godk.macaque.spring.v2;

import cn.godk.macaque.spring.context.ApplicationContext;
import cn.godk.macaque.spring.dao.AccountDao;
import cn.godk.macaque.spring.dao.ItemDao;
import cn.godk.macaque.spring.service.v2.PetStoreService;
import cn.godk.macaque.spring.support.ClassPathXmlApplicationContext;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ApplicationContextTestV2 {

    @Test
    public void testGetBeanProperty() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v2.xml");
        PetStoreService petStore = (PetStoreService) ctx.getBean("petStore");
        //TODO   setter注入
        assertNotNull(petStore.getAccountDao());
        assertNotNull(petStore.getItemDao());

        assertTrue(petStore.getAccountDao() instanceof AccountDao);
        assertTrue(petStore.getItemDao() instanceof ItemDao);

        assertEquals("godk", petStore.getOwner());
        assertEquals(1, petStore.getVersion());

    }

}
