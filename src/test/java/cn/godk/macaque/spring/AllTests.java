package cn.godk.macaque.spring;

import cn.godk.macaque.spring.v1.V1AllTests;
import cn.godk.macaque.spring.v2.V2AllTests;
import cn.godk.macaque.spring.v3.V3AllTests;
import cn.godk.macaque.spring.v4.V4AllTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({V1AllTests.class, V2AllTests.class, V3AllTests.class, V4AllTest.class})
public class AllTests {

}
