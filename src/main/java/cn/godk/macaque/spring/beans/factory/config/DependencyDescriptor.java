package cn.godk.macaque.spring.beans.factory.config;

import cn.godk.macaque.spring.utils.Assert;

import java.lang.reflect.Field;

/**
 * @author wt
 * @program macaque
 * @create 2021-02-03  09:25
 */
public class DependencyDescriptor {

    private  Field field;
    private boolean required;

    public DependencyDescriptor(Field field, boolean required) {
        Assert.notNull(field, "Field must not be null");
        this.field = field;
        this.required = required;

    }
    public Class<?> getDependencyType(){
        if(this.field != null){
            return field.getType();
        }
        throw new RuntimeException("only support field dependency");
    }

    public boolean isRequired() {
        return this.required;
    }
}
