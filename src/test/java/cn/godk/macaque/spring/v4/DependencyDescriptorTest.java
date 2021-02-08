package cn.godk.macaque.spring.v4;

import cn.godk.macaque.spring.beans.factory.config.DependencyDescriptor;
import cn.godk.macaque.spring.beans.factory.support.DefaultBeanFactory;
import cn.godk.macaque.spring.beans.factory.xml.XmlBeanDefinitionReader;
import cn.godk.macaque.spring.core.io.ClassPathResource;
import cn.godk.macaque.spring.core.io.Resource;
import cn.godk.macaque.spring.dao.v4.AccountDao;
import cn.godk.macaque.spring.service.v4.PetStoreService;
import org.junit.Assert;
import org.junit.Test;


import java.lang.reflect.Field;

public class DependencyDescriptorTest {

	@Test
	public void testResolveDependency() throws Exception{
		
		DefaultBeanFactory factory = new DefaultBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
		Resource resource = new ClassPathResource("petstore-v4.xml");
		reader.loadBeanDefinitions(resource);
		
		Field f = PetStoreService.class.getDeclaredField("accountDao");
		DependencyDescriptor descriptor = new DependencyDescriptor(f,true);
		Object o = factory.resolveDependency(descriptor);
		Assert.assertTrue(o instanceof AccountDao);
	}

}
