package cn.godk.macaque.spring.aop.aspectj;

import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 * @author wt
 * @program macaque
 * @create 2021-02-08  09:34
 */
public class AspectJAfterReturningAdvice extends AbstractAspectJAdvice {


    public AspectJAfterReturningAdvice(Method adviceMethod, AspectJExpressionPointcut pointcut, Object adviceObject) {
        super(adviceMethod, pointcut, adviceObject);
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        //  方法执行后 执行advice
        Object proceed = invocation.proceed();
        invokeAdviceMethod();
        return proceed;
    }
}
