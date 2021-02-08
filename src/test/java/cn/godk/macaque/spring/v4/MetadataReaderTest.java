package cn.godk.macaque.spring.v4;

import cn.godk.macaque.spring.core.annotation.AnnotationAttributes;
import cn.godk.macaque.spring.core.io.ClassPathResource;
import cn.godk.macaque.spring.core.type.AnnotationMetadata;
import cn.godk.macaque.spring.core.type.classreading.MetadataReader;
import cn.godk.macaque.spring.core.type.classreading.SimpleMetadataReader;
import cn.godk.macaque.spring.stereotype.Component;
import org.junit.Assert;
import org.junit.Test;


import java.io.IOException;

public class MetadataReaderTest {
	@Test
	public void testGetMetadata() throws IOException{
		ClassPathResource resource = new ClassPathResource("cn/godk/macaque/spring/service/v4/PetStoreService.class");

		MetadataReader reader = new SimpleMetadataReader(resource);
		//注意：不需要单独使用ClassMetadata
		//ClassMetadata clzMetadata = reader.getClassMetadata();
		AnnotationMetadata amd = reader.getAnnotationMetadata();
		
		String annotation = Component.class.getName();
		
		Assert.assertTrue(amd.hasAnnotation(annotation));
		AnnotationAttributes attributes = amd.getAnnotationAttributes(annotation);
		Assert.assertEquals("petStore", attributes.get("value"));
		
		//注：下面对class metadata的测试并不充分
		Assert.assertFalse(amd.isAbstract());
		Assert.assertFalse(amd.isFinal());
		Assert.assertEquals("cn.godk.macaque.spring.service.v4.PetStoreService", amd.getClassName());
		
	}
}
