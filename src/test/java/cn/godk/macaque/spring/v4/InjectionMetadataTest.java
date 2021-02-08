package cn.godk.macaque.spring.v4;

import cn.godk.macaque.spring.beans.factory.annotation.AutowiredFieldElement;
import cn.godk.macaque.spring.beans.factory.annotation.InjectionElement;
import cn.godk.macaque.spring.beans.factory.annotation.InjectionMetadata;
import cn.godk.macaque.spring.beans.factory.support.DefaultBeanFactory;
import cn.godk.macaque.spring.beans.factory.xml.XmlBeanDefinitionReader;
import cn.godk.macaque.spring.core.io.ClassPathResource;
import cn.godk.macaque.spring.core.io.Resource;
import cn.godk.macaque.spring.dao.v4.AccountDao;
import cn.godk.macaque.spring.dao.v4.ItemDao;
import cn.godk.macaque.spring.service.v4.PetStoreService;
import org.junit.Assert;
import org.junit.Test;
import java.lang.reflect.Field;
import java.util.LinkedList;

public class InjectionMetadataTest {

	@Test
	public void testInjection() throws Exception{
		
		DefaultBeanFactory factory = new DefaultBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
		Resource resource = new ClassPathResource("petstore-v4.xml");
		reader.loadBeanDefinitions(resource);
		
		Class<?> clz = PetStoreService.class;
		LinkedList<InjectionElement> elements = new LinkedList<InjectionElement>();
		
		{
			Field f = PetStoreService.class.getDeclaredField("accountDao");		
			InjectionElement injectionElem = new AutowiredFieldElement(f,true,factory);
			elements.add(injectionElem);
		}
		{
			Field f = PetStoreService.class.getDeclaredField("itemDao");		
			InjectionElement injectionElem = new AutowiredFieldElement(f,true,factory);
			elements.add(injectionElem);
		}
		
		InjectionMetadata metadata = new InjectionMetadata(clz,elements);
		
		PetStoreService petStore = new PetStoreService();
		
		metadata.inject(petStore);
		
		Assert.assertTrue(petStore.getAccountDao() instanceof AccountDao);
		
		Assert.assertTrue(petStore.getItemDao() instanceof ItemDao);
		
	}
}
