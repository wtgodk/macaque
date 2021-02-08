package cn.godk.macaque.spring.v4;

import cn.godk.macaque.spring.core.annotation.AnnotationAttributes;
import cn.godk.macaque.spring.core.io.ClassPathResource;
import cn.godk.macaque.spring.core.type.classreading.AnnotationMetadataReadingVisitor;
import cn.godk.macaque.spring.core.type.classreading.ClassMetadataReadingVisitor;
import org.junit.Assert;
import org.junit.Test;

import org.springframework.asm.ClassReader;

import java.io.IOException;


public class ClassReaderTest {

	@Test
	public void testGetClasMetaData() throws IOException {
		ClassPathResource resource = new ClassPathResource("cn/godk/macaque/spring/service/v4/PetStoreService.class");
		ClassReader reader = new ClassReader(resource.getInputStream());
		
		ClassMetadataReadingVisitor visitor = new ClassMetadataReadingVisitor();
		
		reader.accept(visitor, ClassReader.SKIP_DEBUG);
		
		Assert.assertFalse(visitor.isAbstract());
		Assert.assertFalse(visitor.isInterface());
		Assert.assertFalse(visitor.isFinal());
		Assert.assertEquals("cn.godk.macaque.spring.service.v4.PetStoreService", visitor.getClassName());
		Assert.assertEquals("java.lang.Object", visitor.getSuperClassName());
		Assert.assertEquals(0, visitor.getInterfaceNames().length);
	}
	
	@Test
	public void testGetAnnonation() throws Exception{
		ClassPathResource resource = new ClassPathResource("cn/godk/macaque/spring/service/v4/PetStoreService.class");
		ClassReader reader = new ClassReader(resource.getInputStream());

		AnnotationMetadataReadingVisitor visitor = new AnnotationMetadataReadingVisitor();

		reader.accept(visitor, ClassReader.SKIP_DEBUG);

		String annotation = "cn.godk.macaque.spring.stereotype.Component";
		Assert.assertTrue(visitor.hasAnnotation(annotation));

		AnnotationAttributes attributes = visitor.getAnnotationAttributes(annotation);

		Assert.assertEquals("petStore", attributes.get("value"));

	}


}
