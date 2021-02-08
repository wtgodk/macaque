package cn.godk.macaque.spring.aop.aspectj;

import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 * @author wt
 * @program macaque
 * @create 2021-02-08  10:51
 */
public class AspectJAfterThrowingAdvice extends AbstractAspectJAdvice {


    public AspectJAfterThrowingAdvice(Method adviceMethod, AspectJExpressionPointcut pointcut, Object adviceObject) {
        super(adviceMethod, pointcut, adviceObject);
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        try {
            return  invocation.proceed();
        }catch (Throwable t) {
            invokeAdviceMethod();
            throw t;
        }

    }
}
