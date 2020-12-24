package cn.godk.macaque.spring.beans.factory.support;

import cn.godk.macaque.spring.beans.factory.config.SingletonBeanRegistry;
import com.sun.istack.internal.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 *  singleton 处理
 * @author wt
 * @program macaque
 * @create 2020-12-24  14:43
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<String, Object>(64);

    public void registerSingleton(@NotNull String beanName, Object singletonObject) {
             Object oldObject = this.singletonObjects.get(beanName);
        if (oldObject != null) {
            throw new IllegalStateException("Could not register object [" + singletonObject +
                    "] under bean name '" + beanName + "': there is already object [" + oldObject + "] bound");
        }
        this.singletonObjects.put(beanName, singletonObject);
    }

    public Object getSingleton(String beanName) {

        return this.singletonObjects.get(beanName);
    }
}
