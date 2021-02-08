package cn.godk.macaque.spring.aop;

import org.aopalliance.intercept.MethodInterceptor;

/**
 * @author wt
 * @program macaque
 * @create 2021-02-08  09:31
 */
public interface Advice extends MethodInterceptor {


    public Pointcut getPointcut();
}
