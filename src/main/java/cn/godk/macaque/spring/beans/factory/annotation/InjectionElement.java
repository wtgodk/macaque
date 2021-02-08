package cn.godk.macaque.spring.beans.factory.annotation;

import cn.godk.macaque.spring.beans.factory.config.AutowireCapableBeanFactory;

import java.lang.reflect.Member;

/**
 * @author wt
 * @program macaque
 * @create 2021-02-03  09:49
 */
public abstract class InjectionElement {

    protected Member member;

    protected AutowireCapableBeanFactory factory;

    InjectionElement(Member member,AutowireCapableBeanFactory factory){
        this.member = member;
        this.factory = factory;
    }

    public abstract void inject(Object target);
}
