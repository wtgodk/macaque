package cn.godk.macaque.spring.aop.framework;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author wt
 * @program macaque
 * @create 2021-02-08  09:41
 */
public class ReflectiveMethodInvocation implements MethodInvocation {


    private Object targetClass;

    private Method method;

    private Object[] paramList;


    private List<MethodInterceptor> methodInterceptorList;

    private int index = -1;

    public ReflectiveMethodInvocation(Object targetClass, Method method, Object[] paramList, List<MethodInterceptor> methodInterceptorList) {
        this.targetClass = targetClass;
        this.method = method;
        this.paramList = paramList;
        this.methodInterceptorList = methodInterceptorList;
    }

    @Override
    public Object proceed() throws Throwable {

            if(index == methodInterceptorList.size() -1){

                return invokeJoinPoint();
            }
            index++;
        MethodInterceptor methodInterceptor = methodInterceptorList.get(index);
        return methodInterceptor.invoke(this);
    }

    @Override
    public Object getThis() {
        return this.targetClass;
    }

    @Override
    public AccessibleObject getStaticPart() {
        return this.method;
    }

    private Object invokeJoinPoint() throws Throwable {

      return  method.invoke(targetClass,paramList);
    }


    @Override
    public Method getMethod() {
        return this.method;
    }

    @Override
    public Object[] getArguments() {
        return this.paramList != null ? this.paramList : new Object[0];
    }
}
