package cn.godk.macaque.spring.v5;

import cn.godk.macaque.spring.aop.aspectj.AspectJAfterReturningAdvice;
import cn.godk.macaque.spring.aop.aspectj.AspectJBeforeAdvice;
import cn.godk.macaque.spring.aop.aspectj.AspectJExpressionPointcut;
import cn.godk.macaque.spring.aop.config.AspectInstanceFactory;
import cn.godk.macaque.spring.aop.framework.AopConfig;
import cn.godk.macaque.spring.aop.framework.AopConfigSupport;
import cn.godk.macaque.spring.aop.framework.CglibProxyFactory;
import cn.godk.macaque.spring.beans.factory.BeanFactory;
import cn.godk.macaque.spring.service.v5.PetStoreService;
import cn.godk.macaque.spring.tx.TransactionManager;
import cn.godk.macaque.spring.util.MessageTracker;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;


public class CglibAopProxyTest extends AbstractV5Test{
	private  AspectJBeforeAdvice beforeAdvice = null;
	private  AspectJAfterReturningAdvice afterAdvice = null;
	private  AspectJExpressionPointcut pc = null;
	private BeanFactory beanFactory = null;
	private AspectInstanceFactory aspectInstanceFactory = null;


	@Before
	public  void setUp() throws Exception{		

		String expression = "execution(* cn.godk.macaque.spring.service.v5.*.placeOrder(..))";
		pc = new AspectJExpressionPointcut();
		pc.setExpression(expression);

		beanFactory = this.getBeanFactory("petstore-v5.xml");
		aspectInstanceFactory = this.getAspectInstanceFactory("tx");
		aspectInstanceFactory.setBeanFactory(beanFactory);

		beforeAdvice = new AspectJBeforeAdvice(
				getAdviceMethod("start"),
				pc,
				aspectInstanceFactory);

		afterAdvice = new AspectJAfterReturningAdvice(
				getAdviceMethod("commit"),
				pc,
				aspectInstanceFactory);

	}
	
	@Test
	public void testGetProxy(){
		
		AopConfig config = new AopConfigSupport();
		
		config.addAdvice(beforeAdvice);
		config.addAdvice(afterAdvice);
		config.setTargetObject(new PetStoreService());
		
		CglibProxyFactory proxyFactory = new CglibProxyFactory(config);
		
		PetStoreService proxy = (PetStoreService)proxyFactory.getProxy();
		
		proxy.placeOrder();				

		List<String> msgs = MessageTracker.getMsgs();
		Assert.assertEquals(3, msgs.size());
		Assert.assertEquals("start tx", msgs.get(0));
		Assert.assertEquals("place order", msgs.get(1));
		Assert.assertEquals("commit tx", msgs.get(2));
		
		proxy.toString();
	}
	
	
	
	
}
