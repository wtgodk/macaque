package cn.godk.macaque.spring.core.io;


import cn.godk.macaque.spring.utils.ClassUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * 使用 项目路径获取
 *
 * @author wt
 * @program macaque
 * @create 2020-12-24  13:54
 */
public class ClassPathResource implements Resource {

    private String path;

    private ClassLoader classLoader;

    public ClassPathResource(String path) {
        this(path, null);

    }

    public ClassPathResource(String path, ClassLoader classLoader) {
        this.path = path;
        this.classLoader = classLoader == null ? ClassUtils.getDefaultClassLoader() : classLoader;
    }

    public InputStream getInputStream() throws IOException {
        InputStream in = this.classLoader.getResourceAsStream(this.path);
        if (in == null) {
            throw new FileNotFoundException("file path :" + getDescription());
        }
        return in;
    }

    public String getDescription() {
        return this.path;
    }
}
