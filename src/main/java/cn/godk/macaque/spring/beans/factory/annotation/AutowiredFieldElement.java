package cn.godk.macaque.spring.beans.factory.annotation;

import cn.godk.macaque.spring.beans.Exception.BeanCreationException;
import cn.godk.macaque.spring.beans.factory.config.AutowireCapableBeanFactory;
import cn.godk.macaque.spring.beans.factory.config.ConfigurableBeanFactory;
import cn.godk.macaque.spring.beans.factory.config.DependencyDescriptor;
import cn.godk.macaque.spring.utils.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Member;

/**
 * @author wt
 * @program macaque
 * @create 2021-02-03  09:52
 */
public class AutowiredFieldElement extends InjectionElement {

    private boolean required ;

    public AutowiredFieldElement(Member member, boolean required,AutowireCapableBeanFactory factory) {
        super(member, factory);
        this.required = required;
    }
    public Field getField(){
        return (Field)this.member;
    }
    @Override
    public void inject(Object target) {
        Field field = this.getField();
        DependencyDescriptor desc = new DependencyDescriptor(field, this.required);
        Object value = factory.resolveDependency(desc);
        try {
        // 注入到target
        if (value != null) {
            ReflectionUtils.makeAccessible(field);
            field.set(target, value);
        }
        }
		catch (Throwable ex) {
        throw new BeanCreationException("Could not autowire field: " + field, ex);
    }
    }
}
