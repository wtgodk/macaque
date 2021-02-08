package cn.godk.macaque.spring.core.io;

import cn.godk.macaque.spring.utils.ClassUtils;

import java.io.File;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 *  包路径扫描载入
 *
 * @author wt
 * @program macaque
 * @create 2021-01-21  13:20
 */
public class PackageResourceLoader {

    private ClassLoader classLoader;


    public PackageResourceLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public PackageResourceLoader() {
        classLoader = ClassUtils.getDefaultClassLoader();
    }


    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public Resource[] getResources(String basePackage) {
        // . ——> /
        String location = ClassUtils.convertClassNameToResourcePath(basePackage);

        URL resource = getClassLoader().getResource(location);

        File file = new File(resource.getFile());
        Set<File> matchingFiles = retrieveMatchingFiles(file);
        Resource[] resources = new Resource[matchingFiles.size()];
        int i=0;
        for (File matchingFile : matchingFiles) {
            resources[i++] = new FileSystemResource(matchingFile);
        }
return resources;
    }
/**
 * 功能描述: <br>
 * 〈〉   获取 包路径下的所有 文件
 * @param file
 * @return java.util.Set<java.io.File>
 * @author weitao
 * @date 2021/1/21 13:26
 */
    private Set<File> retrieveMatchingFiles(File file) {
       // 路径不存在
        if(!file.exists()){
            return Collections.emptySet();
        }
        // 不是包路径
        if(!file.isDirectory()){
            return Collections.emptySet();
        }
        // 路径不可读取
        if(!file.canWrite()){
            return Collections.emptySet();
        }
        // 递归读取所有文件
        Set<File> result = new LinkedHashSet<File>(8);
        doRetrieveMatchingFiles(file, result);
        return result;
    }

    private void doRetrieveMatchingFiles(File file, Set<File> result) {
        if(file.isFile()){
            result.add(file);
        }else if(file.isDirectory() && file.canRead()){
            File[] files = file.listFiles();
            if(files!=null && files.length>0){
                for (File child : files) {
                    doRetrieveMatchingFiles(child,result);
                }
            }
        }
    }
}
