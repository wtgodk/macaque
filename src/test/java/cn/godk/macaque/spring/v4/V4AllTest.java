package cn.godk.macaque.spring.v4;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author wt
 * @program macaque
 * @create 2021-02-03  11:34
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({ ApplicationContextTest4.class, AutowiredAnnotationProcessorTest.class,
        ClassPathBeanDefinitionScannerTest.class, ClassReaderTest.class, DependencyDescriptorTest.class,
        InjectionMetadataTest.class, MetadataReaderTest.class, PackageResourceLoaderTest.class,
        XmlBeanDefinitionReaderTest.class })
public class V4AllTest {



}
