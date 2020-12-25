package cn.godk.macaque.spring.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * resource loader
 *
 * @author wt
 * @program macaque
 * @create 2020-12-24  13:51
 */
public interface Resource {

    /**
     * 功能描述: <br>
     * 〈〉  获取资源 inputStream
     *
     * @param
     * @return java.io.InputStream
     * @author weitao
     * @date 2020/12/24 13:52
     */
    InputStream getInputStream() throws IOException;


    String getDescription();

}
