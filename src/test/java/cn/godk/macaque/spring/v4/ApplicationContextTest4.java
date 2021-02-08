package cn.godk.macaque.spring.v4;

import cn.godk.macaque.spring.context.ApplicationContext;
import cn.godk.macaque.spring.service.v4.PetStoreService;
import cn.godk.macaque.spring.support.ClassPathXmlApplicationContext;
import org.junit.Assert;
import org.junit.Test;

public class ApplicationContextTest4 {

	@Test
	public void testGetBeanProperty() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v4.xml");
		PetStoreService petStore = (PetStoreService)ctx.getBean("petStore");
		
		Assert.assertNotNull(petStore.getAccountDao());
		Assert.assertNotNull(petStore.getItemDao());
		
	}	
}
