package cn.godk.macaque.spring.v5;

import cn.godk.macaque.spring.aop.config.AspectInstanceFactory;
import cn.godk.macaque.spring.beans.factory.BeanFactory;
import cn.godk.macaque.spring.beans.factory.support.DefaultBeanFactory;
import cn.godk.macaque.spring.beans.factory.xml.XmlBeanDefinitionReader;
import cn.godk.macaque.spring.core.io.ClassPathResource;
import cn.godk.macaque.spring.core.io.Resource;
import cn.godk.macaque.spring.tx.TransactionManager;

import java.lang.reflect.Method;

public class AbstractV5Test {
		
	protected BeanFactory getBeanFactory(String configFile){
		DefaultBeanFactory defaultBeanFactory = new DefaultBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(defaultBeanFactory);
		Resource resource = new ClassPathResource(configFile);
		reader.loadBeanDefinitions(resource);	
		return  defaultBeanFactory;		
	}
	
	protected  Method getAdviceMethod( String methodName) throws Exception{
		return TransactionManager.class.getMethod(methodName);
	}

	protected AspectInstanceFactory getAspectInstanceFactory(String targetBeanName){
		AspectInstanceFactory factory = new AspectInstanceFactory();
		factory.setAspectBeanName(targetBeanName);
		return factory;
	}

	
}
