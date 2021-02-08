package cn.godk.macaque.spring.v3;

import cn.godk.macaque.spring.beans.BeanDefinition;
import cn.godk.macaque.spring.beans.ConstructorArgument;
import cn.godk.macaque.spring.beans.factory.config.RuntimeBeanReference;
import cn.godk.macaque.spring.beans.factory.config.TypedStringValue;
import cn.godk.macaque.spring.beans.factory.support.DefaultBeanFactory;
import cn.godk.macaque.spring.beans.factory.xml.XmlBeanDefinitionReader;
import cn.godk.macaque.spring.core.io.ClassPathResource;
import cn.godk.macaque.spring.core.io.Resource;
import org.junit.Assert;
import org.junit.Test;


import java.util.List;

public class BeanDefinitionTestV3 {

	@Test
	public void testConstructorArgument() {
		
		DefaultBeanFactory factory = new DefaultBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
		Resource resource = new ClassPathResource("petstore-v3.xml");
		reader.loadBeanDefinitions(resource);

		BeanDefinition bd = factory.getBeanDefinition("petStore");
		Assert.assertEquals("cn.godk.macaque.spring.service.v3.PetStoreServiceImplV3", bd.getBeanClassName());

		ConstructorArgument args = bd.getConstructorArgument();
		List<ConstructorArgument.ValueHolder> valueHolders = args.getArgumentValues();

		Assert.assertEquals(3, valueHolders.size());

		RuntimeBeanReference ref1 = (RuntimeBeanReference)valueHolders.get(0).getValue();
		Assert.assertEquals("accountDao", ref1.getBeanName());
		RuntimeBeanReference ref2 = (RuntimeBeanReference)valueHolders.get(1).getValue();
		Assert.assertEquals("itemDao", ref2.getBeanName());

		TypedStringValue strValue = (TypedStringValue)valueHolders.get(2).getValue();
		Assert.assertEquals( "1", strValue.getValue());
	}

}
