package cn.godk.macaque.spring.core.type.classreading;

import cn.godk.macaque.spring.core.io.Resource;
import cn.godk.macaque.spring.core.type.AnnotationMetadata;
import cn.godk.macaque.spring.core.type.ClassMetadata;
import org.springframework.asm.ClassReader;

import java.io.IOException;

/**
 * @author wt
 * @program macaque
 * @create 2021-01-21  15:08
 */
public class SimpleMetadataReader  implements MetadataReader {

    Resource resource ;

    ClassMetadata classMetadata;

    AnnotationMetadata annotationMetadata;

    public SimpleMetadataReader(Resource resource) throws IOException {
        this.resource = resource;
        ClassReader reader = new ClassReader(resource.getInputStream());
        AnnotationMetadataReadingVisitor visitor = new AnnotationMetadataReadingVisitor();
        reader.accept(visitor, ClassReader.SKIP_DEBUG);
        this.annotationMetadata = visitor;
        this.classMetadata   = visitor;
    }


    @Override
    public Resource getResource() {
        return this.resource;
    }

    @Override
    public ClassMetadata getClassMetadata() {
        return this.classMetadata;
    }

    @Override
    public AnnotationMetadata getAnnotationMetadata() {
        return this.annotationMetadata;
    }
}
