package cn.godk.macaque.spring.core.io;

import com.sun.istack.internal.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author wt
 * @program macaque
 * @create 2020-12-24  14:02
 */
public class FileSystemResource implements Resource{


    private final String path;
    private final File file;


    public FileSystemResource(@NotNull String path){
        this.path = path;
        this.file = new File(path);
    }
    public InputStream getInputStream() throws IOException {
        return new FileInputStream(this.file);
    }

    public String getDescription() {
        return "file [" + this.file.getAbsolutePath() + "]";
    }
}
