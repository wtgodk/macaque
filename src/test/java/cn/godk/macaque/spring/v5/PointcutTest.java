package cn.godk.macaque.spring.v5;

import cn.godk.macaque.spring.aop.MethodMatcher;
import cn.godk.macaque.spring.aop.aspectj.AspectJExpressionPointcut;
import cn.godk.macaque.spring.service.v5.PetStoreService;
import org.junit.Assert;
import org.junit.Test;


import java.lang.reflect.Method;


public class PointcutTest {
	@Test
	public void testPointcut() throws Exception{
		
		String expression = "execution(* cn.godk.macaque.spring.service.v5.*.placeOrder(..))";
		
		AspectJExpressionPointcut pc = new AspectJExpressionPointcut();
		pc.setExpression(expression);
		
		MethodMatcher mm = pc.getMethodMatcher();
		
		{
			Class<?> targetClass = PetStoreService.class;
			
			Method method1 = targetClass.getMethod("placeOrder");		
			Assert.assertTrue(mm.matches(method1));
			
			Method method2 = targetClass.getMethod("getAccountDao");		
			Assert.assertFalse(mm.matches(method2));
		}
		
		{
			Class<?> targetClass = cn.godk.macaque.spring.service.v4.PetStoreService.class;
		
			Method method = targetClass.getMethod("getAccountDao");		
			Assert.assertFalse(mm.matches(method));
		}

		
	}
}
