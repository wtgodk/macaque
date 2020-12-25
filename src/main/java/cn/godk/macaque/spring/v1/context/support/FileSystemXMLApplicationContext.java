package cn.godk.macaque.spring.v1.context.support;

import cn.godk.macaque.spring.core.io.FileSystemResource;
import cn.godk.macaque.spring.core.io.Resource;

/**
 *
 *  指定资源路径
 * @author wt
 * @program macaque
 * @create 2020-12-24  15:23
 */
public class FileSystemXMLApplicationContext extends AbstractApplicationContext {
    public FileSystemXMLApplicationContext(String configFile) {
        super(configFile);
    }

    @Override
    protected Resource getResourceByPath(String configFile) {
        return new FileSystemResource(configFile);
    }
}
