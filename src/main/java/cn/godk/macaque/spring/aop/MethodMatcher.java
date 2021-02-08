package cn.godk.macaque.spring.aop;

import java.lang.reflect.Method;

/**
 * @author wt
 * @program macaque
 * @create 2021-02-07  13:54
 */
public interface MethodMatcher {

    boolean matches(Method method/*, Class<?> targetClass*/);
}
