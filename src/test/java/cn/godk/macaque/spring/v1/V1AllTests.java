package cn.godk.macaque.spring.v1;

import cn.godk.macaque.spring.v1.bean.ApplicationContextTest;
import cn.godk.macaque.spring.v1.bean.BeanFactoryTest;
import cn.godk.macaque.spring.v1.resource.ResourceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
        ApplicationContextTest.class,
        BeanFactoryTest.class,
        ResourceTest.class})
public class V1AllTests {

}
