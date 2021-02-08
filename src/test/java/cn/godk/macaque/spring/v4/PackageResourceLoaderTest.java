package cn.godk.macaque.spring.v4;

import cn.godk.macaque.spring.core.io.PackageResourceLoader;
import cn.godk.macaque.spring.core.io.Resource;
import org.junit.Assert;
import org.junit.Test;


import java.io.IOException;

public class PackageResourceLoaderTest {

	@Test
	public void testGetResources() throws IOException{
		PackageResourceLoader loader = new PackageResourceLoader();
		Resource[] resources = loader.getResources("cn.godk.macaque.spring.dao");
		Assert.assertEquals(4, resources.length);
		
	}

}
