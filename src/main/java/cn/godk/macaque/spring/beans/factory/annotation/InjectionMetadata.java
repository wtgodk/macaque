package cn.godk.macaque.spring.beans.factory.annotation;

import java.util.LinkedList;
import java.util.List;

/**
 * @author wt
 * @program macaque
 * @create 2021-02-03  09:57
 */
public class InjectionMetadata {

    LinkedList<InjectionElement> elements ;

    private Class<?> clazz;

    public InjectionMetadata( Class<?> clazz,LinkedList<InjectionElement> elements) {
        this.elements = elements;
        this.clazz = clazz;
    }


    public void inject(Object obj) {
        for (InjectionElement element : elements) {
            element.inject(obj);
        }

    }

    public List<InjectionElement> getInjectionElements() {
        return elements;
    }
}
