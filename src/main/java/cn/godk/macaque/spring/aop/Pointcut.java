package cn.godk.macaque.spring.aop;

/**
 * @author wt
 * @program macaque
 * @create 2021-02-07  13:56
 */
public interface Pointcut {
    MethodMatcher getMethodMatcher();
    String getExpression();
}
