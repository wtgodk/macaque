package cn.godk.macaque.spring.aop.config;

import cn.godk.macaque.spring.aop.aspectj.AspectJAfterReturningAdvice;
import cn.godk.macaque.spring.aop.aspectj.AspectJAfterThrowingAdvice;
import cn.godk.macaque.spring.aop.aspectj.AspectJBeforeAdvice;
import cn.godk.macaque.spring.aop.aspectj.AspectJExpressionPointcut;
import cn.godk.macaque.spring.beans.BeanDefinition;
import cn.godk.macaque.spring.beans.ConstructorArgument;
import cn.godk.macaque.spring.beans.GenericBeanDefinition;
import cn.godk.macaque.spring.beans.PropertyValue;
import cn.godk.macaque.spring.beans.factory.config.RuntimeBeanReference;
import cn.godk.macaque.spring.beans.factory.support.BeanDefinitionReaderUtils;
import cn.godk.macaque.spring.beans.factory.support.BeanDefinitionRegistry;
import cn.godk.macaque.spring.utils.StringUtils;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wt
 * @program macaque
 * @create 2021-02-23  13:27
 */
public class ConfigBeanDefinitionParser {

    private static final String ASPECT = "aspect";
    private static final String EXPRESSION = "expression";
    private static final String ID = "id";
    private static final String POINTCUT = "pointcut";
    private static final String POINTCUT_REF = "pointcut-ref";
    private static final String REF = "ref";
    private static final String BEFORE = "before";
    private static final String AFTER = "after";
    private static final String AFTER_RETURNING_ELEMENT = "after-returning";
    private static final String AFTER_THROWING_ELEMENT = "after-throwing";
    private static final String AROUND = "around";
    private static final String ASPECT_NAME_PROPERTY = "aspectName";

    public BeanDefinition parse(Element element, BeanDefinitionRegistry registry) {

        List<Element> childElts = element.elements();
        for (Element elt: childElts) {
            String localName = elt.getName();
			/*if (POINTCUT.equals(localName)) {
				parsePointcut(elt, registry);
			}*/
			/*else if (ADVISOR.equals(localName)) {
				parseAdvisor(elt, registry);
			}*/
            if (ASPECT.equals(localName)) {
                parseAspect(elt, registry);
            }
        }

        return null;
    }

    private void parseAspect(Element aspectElement, BeanDefinitionRegistry registry) {


//	<aop:config>
//		<aop:aspect ref="tx">
//			<aop:pointcut id="placeOrder"
//        expression="execution(* cn.godk.macaque.spring.service.v5.*.placeOrder(..))" />
//			<aop:before pointcut-ref="placeOrder" method="start" />
//			<aop:after-returning pointcut-ref="placeOrder"	method="commit" />
//			<aop:after-throwing pointcut-ref="placeOrder" method = "rollback"/>
//		</aop:aspect>
//	</aop:config>

        String aspectId = aspectElement.attributeValue(ID);
        String aspectName = aspectElement.attributeValue(REF);


        List<BeanDefinition> beanDefinitions = new ArrayList<BeanDefinition>();
        List<RuntimeBeanReference> beanReferences = new ArrayList<RuntimeBeanReference>();


        List<Element> eleList = aspectElement.elements();
        // 是否已经初始化了<aop:aspect ref="tx">
        boolean adviceFoundAlready = false;
        for (int i=0;i<eleList.size();i++) {
            Element ele = eleList.get(i);
            if (isAdviceNode(ele)) {
                // 判断是否为 aop:before aop:after-throwing 等等，跳过aop:pointcut
                if (!adviceFoundAlready) {
                    // 将 aspect加入，仅假如一次
                    adviceFoundAlready = true;
                    if (!StringUtils.hasText(aspectName)) {
                        return;
                    }
                    //  aop:aspect  ref="aspectName"
                    beanReferences.add(new RuntimeBeanReference(aspectName));
                }
                // 构造advice
                GenericBeanDefinition advisorDefinition = parseAdvice(
                        aspectName, i, aspectElement, ele, registry, beanDefinitions, beanReferences);
                beanDefinitions.add(advisorDefinition);
            }
        }

        List<Element> pointcuts = aspectElement.elements(POINTCUT);
        for (Element pointcutElement : pointcuts) {
            parsePointcut(pointcutElement, registry);
        }



    }

    /**
     * Parses the supplied {@code &lt;pointcut&gt;} and registers the resulting
     * Pointcut with the BeanDefinitionRegistry.
     */
    private GenericBeanDefinition parsePointcut(Element pointcutElement,BeanDefinitionRegistry registry) {
        String id = pointcutElement.attributeValue(ID);
        String expression = pointcutElement.attributeValue(EXPRESSION);

        GenericBeanDefinition pointcutDefinition = null;


        //this.parseState.push(new PointcutEntry(id));
        pointcutDefinition = createPointcutDefinition(expression);
        //pointcutDefinition.setSource(parserContext.extractSource(pointcutElement));

        String pointcutBeanName = id;
        if (StringUtils.hasText(pointcutBeanName)) {
            registry.registerBeanDefinition(pointcutBeanName, pointcutDefinition);
        }
        else {
            BeanDefinitionReaderUtils.registerWithGeneratedName(pointcutDefinition, registry);

        }


        return pointcutDefinition;
    }

    private GenericBeanDefinition parseAdvice(
            String aspectName, int order, Element aspectElement, Element adviceElement, BeanDefinitionRegistry registry,
            List<BeanDefinition> beanDefinitions, List<RuntimeBeanReference> beanReferences) {

        //  创建构造函数的参数
        //  1、 MethodLocatingFactory 作为第一个构造函数参数， 对应于  {@link AbstractAspectJAdvice}的 Method参数 ，此处参数不匹配 可以使用MethodLocatingFactory.getObject()获取method
        //   MethodLocatingFactory 继承 FactoryBean( 注意不是BeanFactory) factoryBean 区别于BeanFactory,BeanFactory用于创建bean，FactoryBean是一个bean，用于对当前bean进行装饰，返回getObject()对象
        //   在此处MethodLocatingFactory最终将返回Method类型参数

        GenericBeanDefinition methodDefinition = new GenericBeanDefinition(MethodLocatingFactory.class);
        methodDefinition.getPropertyValues().add(new PropertyValue("targetBeanName", aspectName));
        methodDefinition.getPropertyValues().add(new PropertyValue("methodName", adviceElement.attributeValue("method")));
        methodDefinition.setSynthetic(true);

        // 2、对应于  {@link AbstractAspectJAdvice} ,AspectInstanceFactory 参数
        // create instance factory definition
        GenericBeanDefinition aspectFactoryDef =
                new GenericBeanDefinition(AspectInstanceFactory.class);
        aspectFactoryDef.getPropertyValues().add(new PropertyValue("aspectBeanName", aspectName));
        aspectFactoryDef.setSynthetic(true);

        // 3、 AspectJExpressionPointcut 构建
        // register the pointcut
        GenericBeanDefinition adviceDef = createAdviceDefinition(
                adviceElement, registry, aspectName, order, methodDefinition, aspectFactoryDef,
                beanDefinitions, beanReferences);

        adviceDef.setSynthetic(true);


        // register the final advisor
        BeanDefinitionReaderUtils.registerWithGeneratedName(adviceDef, registry);

        return adviceDef;

    }
    /**
     * Return {@code true} if the supplied node describes an advice type. May be one of:
     * '{@code before}', '{@code after}', '{@code after-returning}',
     * '{@code after-throwing}' or '{@code around}'.
     */
    private boolean isAdviceNode(Element ele) {

        String name = ele.getName();
        return (BEFORE.equals(name) || AFTER.equals(name) || AFTER_RETURNING_ELEMENT.equals(name) ||
                AFTER_THROWING_ELEMENT.equals(name) || AROUND.equals(name));

    }
    /**
     * Creates the RootBeanDefinition for a POJO advice bean. Also causes pointcut
     * parsing to occur so that the pointcut may be associate with the advice bean.
     * This same pointcut is also configured as the pointcut for the enclosing
     * Advisor definition using the supplied MutablePropertyValues.
     */
    private GenericBeanDefinition createAdviceDefinition(
            Element adviceElement, BeanDefinitionRegistry registry, String aspectName, int order,
            GenericBeanDefinition methodDef, GenericBeanDefinition aspectFactoryDef,
            List<BeanDefinition> beanDefinitions, List<RuntimeBeanReference> beanReferences) {
        // 根据 elementName获取对应的 Advice 比如 aop:before 对应 AspectJBeforeAdvice
        Class<?> adviceClass = getAdviceClass(adviceElement);
        GenericBeanDefinition adviceDefinition = new GenericBeanDefinition(adviceClass);
        adviceDefinition.getPropertyValues().add(new PropertyValue(ASPECT_NAME_PROPERTY, aspectName));


        ConstructorArgument cav = adviceDefinition.getConstructorArgument();
        cav.addArgumentValue(methodDef);

        Object pointcut = parsePointcutProperty(adviceElement);
        if (pointcut instanceof BeanDefinition) {
            cav.addArgumentValue(pointcut);

            beanDefinitions.add((BeanDefinition) pointcut);
        }
        else if (pointcut instanceof String) {
            RuntimeBeanReference pointcutRef = new RuntimeBeanReference((String) pointcut);
            cav.addArgumentValue(pointcutRef);
            beanReferences.add(pointcutRef);
        }
        cav.addArgumentValue(aspectFactoryDef);

        return adviceDefinition;
    }

    /**
     * Parses the {@code pointcut} or {@code pointcut-ref} attributes of the supplied
     * {@link Element} and add a {@code pointcut} property as appropriate. Generates a
     * {@link org.springframework.beans.factory.config.BeanDefinition} for the pointcut if  necessary
     * and returns its bean name, otherwise returns the bean name of the referred pointcut.
     */
    private Object parsePointcutProperty(Element element/*, ParserContext parserContext*/) {
        if ((element.attribute(POINTCUT)== null) && (element.attribute(POINTCUT_REF)==null)) {
			/*parserContext.getReaderContext().error(
					"Cannot define both 'pointcut' and 'pointcut-ref' on <advisor> tag.",
					element, this.parseState.snapshot());*/
            return null;
        }
        else if (element.attribute(POINTCUT)!=null) {
            // Create a pointcut for the anonymous pc and register it.
            String expression = element.attributeValue(POINTCUT);
            GenericBeanDefinition pointcutDefinition = createPointcutDefinition(expression);
            //pointcutDefinition.setSource(parserContext.extractSource(element));
            return pointcutDefinition;
        }
        else if (element.attribute(POINTCUT_REF)!=null) {
            String pointcutRef = element.attributeValue(POINTCUT_REF);
            if (!StringUtils.hasText(pointcutRef)) {
				/*parserContext.getReaderContext().error(
						"'pointcut-ref' attribute contains empty value.", element, this.parseState.snapshot());*/
                return null;
            }
            return pointcutRef;
        }
        else {/*
			parserContext.getReaderContext().error(
					"Must define one of 'pointcut' or 'pointcut-ref' on <advisor> tag.",
					element, this.parseState.snapshot());*/
            return null;
        }
    }
    /**
     * Creates a {@link BeanDefinition} for the {@link AspectJExpressionPointcut} class using
     * the supplied pointcut expression.
     */
    protected GenericBeanDefinition createPointcutDefinition(String expression) {
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition(AspectJExpressionPointcut.class);
        beanDefinition.setScope(BeanDefinition.SCOPE_PROTOTYPE);
        beanDefinition.setSynthetic(true);
        beanDefinition.getPropertyValues().add(new PropertyValue(EXPRESSION, expression));
        return beanDefinition;
    }

    /**
     * Gets the advice implementation class corresponding to the supplied {@link Element}.
     */
    private Class<?> getAdviceClass(Element adviceElement) {
        String elementName = adviceElement.getName();
        if (BEFORE.equals(elementName)) {
            return AspectJBeforeAdvice.class;
        }
		/*else if (AFTER.equals(elementName)) {
			return AspectJAfterAdvice.class;
		}*/
        else if (AFTER_RETURNING_ELEMENT.equals(elementName)) {
            return AspectJAfterReturningAdvice.class;
        }
        else if (AFTER_THROWING_ELEMENT.equals(elementName)) {
            return AspectJAfterThrowingAdvice.class;
        }
		/*else if (AROUND.equals(elementName)) {
			return AspectJAroundAdvice.class;
		}*/
        else {
            throw new IllegalArgumentException("Unknown advice kind [" + elementName + "].");
        }
    }
}
