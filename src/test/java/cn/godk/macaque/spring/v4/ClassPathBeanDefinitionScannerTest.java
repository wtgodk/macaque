package cn.godk.macaque.spring.v4;

import cn.godk.macaque.spring.beans.BeanDefinition;
import cn.godk.macaque.spring.beans.factory.support.DefaultBeanFactory;
import cn.godk.macaque.spring.context.context.ClassPathBeanDefinitionScanner;
import cn.godk.macaque.spring.context.context.ScannedGenericBeanDefinition;
import cn.godk.macaque.spring.core.annotation.AnnotationAttributes;
import cn.godk.macaque.spring.core.type.AnnotationMetadata;
import cn.godk.macaque.spring.stereotype.Component;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;


public class ClassPathBeanDefinitionScannerTest {
	
	@Test
	public void testParseScanedBean()  {
		
		DefaultBeanFactory factory = new DefaultBeanFactory();
		
		String basePackages = "cn.godk.macaque.spring";
		
		ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(factory);
		scanner.doScan(basePackages);
		
		
		String annotation = Component.class.getName();
		
		{
			BeanDefinition bd = factory.getBeanDefinition("petStore");
			Assert.assertTrue(bd instanceof ScannedGenericBeanDefinition);
			ScannedGenericBeanDefinition sbd = (ScannedGenericBeanDefinition)bd;
			AnnotationMetadata amd = sbd.getMetadata();
			
			
			Assert.assertTrue(amd.hasAnnotation(annotation));
			AnnotationAttributes attributes = amd.getAnnotationAttributes(annotation);
			Assert.assertEquals("petStore", attributes.get("value"));
		}
		{
			BeanDefinition bd = factory.getBeanDefinition("accountDao");
			Assert.assertTrue(bd instanceof ScannedGenericBeanDefinition);
			ScannedGenericBeanDefinition sbd = (ScannedGenericBeanDefinition)bd;			
			AnnotationMetadata amd = sbd.getMetadata();
			Assert.assertTrue(amd.hasAnnotation(annotation));
		}
		{
			BeanDefinition bd = factory.getBeanDefinition("itemDao");
			Assert.assertTrue(bd instanceof ScannedGenericBeanDefinition);
			ScannedGenericBeanDefinition sbd = (ScannedGenericBeanDefinition)bd;			
			AnnotationMetadata amd = sbd.getMetadata();
			Assert.assertTrue(amd.hasAnnotation(annotation));
		}
	}
}
