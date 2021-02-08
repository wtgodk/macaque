package cn.godk.macaque.spring.v3;

import cn.godk.macaque.spring.context.ApplicationContext;
import cn.godk.macaque.spring.service.v2.PetStoreService;
import cn.godk.macaque.spring.service.v3.PetStoreServiceImplV3;
import cn.godk.macaque.spring.support.ClassPathXmlApplicationContext;
import org.junit.Assert;
import org.junit.Test;

public class ApplicationContextTestV3 {

	@Test
	public void testGetBeanProperty() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v3.xml");
		PetStoreServiceImplV3 petStore = (PetStoreServiceImplV3)ctx.getBean("petStore");
		
		Assert.assertNotNull(petStore.getAccountDao());
		Assert.assertNotNull(petStore.getItemDao());
		Assert.assertEquals(1, petStore.getVersion());
		
	}

}
