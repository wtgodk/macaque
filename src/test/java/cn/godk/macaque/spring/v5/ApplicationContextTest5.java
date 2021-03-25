package cn.godk.macaque.spring.v5;

import cn.godk.macaque.spring.context.ApplicationContext;
import cn.godk.macaque.spring.service.v5.PetStoreService;
import cn.godk.macaque.spring.support.ClassPathXmlApplicationContext;
import cn.godk.macaque.spring.util.MessageTracker;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ApplicationContextTest5 {
	
	@Before
	public void setUp(){
		MessageTracker.clearMsgs();
	}
	@Test
	public void testPlaceOrder() {
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v5.xml");
		PetStoreService petStore = (PetStoreService)ctx.getBean("petStore");
		
		Assert.assertNotNull(petStore.getAccountDao());
		Assert.assertNotNull(petStore.getItemDao());
		
		petStore.placeOrder();
		
		List<String> msgs = MessageTracker.getMsgs();
		
		Assert.assertEquals(3, msgs.size());
		Assert.assertEquals("start tx", msgs.get(0));
		Assert.assertEquals("place order", msgs.get(1));
		Assert.assertEquals("commit tx", msgs.get(2));
		
	}	
	
	
}
