package cn.godk.macaque.spring.aop.aspectj;

import cn.godk.macaque.spring.aop.Advice;
import cn.godk.macaque.spring.aop.Pointcut;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 * @author wt
 * @program macaque
 * @create 2021-02-08  09:31
 */
public abstract  class AbstractAspectJAdvice implements Advice {

    protected Method adviceMethod;
    protected AspectJExpressionPointcut pointcut;
    protected Object adviceObject;

    public AbstractAspectJAdvice(Method adviceMethod, AspectJExpressionPointcut pointcut, Object adviceObject) {
        this.adviceMethod = adviceMethod;
        this.pointcut = pointcut;
        this.adviceObject = adviceObject;
    }


    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }

    public void invokeAdviceMethod() throws  Throwable{
        adviceMethod.invoke(adviceObject);
    }


}
