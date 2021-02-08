package cn.godk.macaque.spring.v3;

import cn.godk.macaque.spring.beans.BeanDefinition;
import cn.godk.macaque.spring.beans.factory.support.ConstructorResolver;
import cn.godk.macaque.spring.beans.factory.support.DefaultBeanFactory;
import cn.godk.macaque.spring.beans.factory.xml.XmlBeanDefinitionReader;
import cn.godk.macaque.spring.core.io.ClassPathResource;
import cn.godk.macaque.spring.core.io.Resource;
import cn.godk.macaque.spring.service.IPetStoreService;
import cn.godk.macaque.spring.service.v2.PetStoreService;
import cn.godk.macaque.spring.service.v3.PetStoreServiceImplV3;
import org.junit.Assert;
import org.junit.Test;

public class ConstructorResolverTest {

	@Test
	public void testAutowireConstructor() {
		
		DefaultBeanFactory factory = new DefaultBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
		Resource resource = new ClassPathResource("petstore-v3.xml");
		reader.loadBeanDefinitions(resource);

		BeanDefinition bd = factory.getBeanDefinition("petStore");
		
		ConstructorResolver resolver = new ConstructorResolver(factory);

		PetStoreServiceImplV3 petStore = (PetStoreServiceImplV3)resolver.autowireConstructor(bd);
		
		// 验证参数version 正确地通过此构造函数做了初始化
		// PetStoreService(AccountDao accountDao, ItemDao itemDao,int version)
		Assert.assertEquals(1, petStore.getVersion());
		
		Assert.assertNotNull(petStore.getAccountDao());
		Assert.assertNotNull(petStore.getItemDao());
		
		
	}
}
