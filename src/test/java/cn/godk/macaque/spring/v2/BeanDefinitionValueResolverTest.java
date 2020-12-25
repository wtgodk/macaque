package cn.godk.macaque.spring.v2;

import cn.godk.macaque.spring.beans.factory.config.RuntimeBeanReference;
import cn.godk.macaque.spring.beans.factory.config.TypedStringValue;
import cn.godk.macaque.spring.beans.factory.support.BeanDefinitionValueResolver;
import cn.godk.macaque.spring.beans.factory.support.DefaultBeanFactory;
import cn.godk.macaque.spring.beans.factory.xml.XmlBeanDefinitionReader;
import cn.godk.macaque.spring.core.io.ClassPathResource;
import cn.godk.macaque.spring.dao.AccountDao;
import org.junit.Assert;
import org.junit.Test;

public class BeanDefinitionValueResolverTest {

    @Test
    public void testResolveRuntimeBeanReference() {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinitions(new ClassPathResource("petstore-v2.xml"));

        BeanDefinitionValueResolver resolver = new BeanDefinitionValueResolver(factory);

        RuntimeBeanReference reference = new RuntimeBeanReference("accountDao");
        Object value = resolver.resolveValueIfNecessary(reference);

        Assert.assertNotNull(value);
        Assert.assertTrue(value instanceof AccountDao);
    }

    @Test
    public void testResolveTypedStringValue() {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinitions(new ClassPathResource("petstore-v2.xml"));

        BeanDefinitionValueResolver resolver = new BeanDefinitionValueResolver(factory);

        TypedStringValue stringValue = new TypedStringValue("test");
        Object value = resolver.resolveValueIfNecessary(stringValue);
        Assert.assertNotNull(value);
        Assert.assertEquals("test", value);

    }
}
