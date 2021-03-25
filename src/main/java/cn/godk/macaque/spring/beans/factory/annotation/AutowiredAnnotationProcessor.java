package cn.godk.macaque.spring.beans.factory.annotation;

import cn.godk.macaque.spring.beans.Exception.BeanCreationException;
import cn.godk.macaque.spring.beans.Exception.BeansException;
import cn.godk.macaque.spring.beans.factory.config.AutowireCapableBeanFactory;
import cn.godk.macaque.spring.beans.factory.config.DependencyDescriptor;
import cn.godk.macaque.spring.beans.factory.config.InstantiationAwareBeanPostProcessor;
import cn.godk.macaque.spring.beans.factory.support.DefaultBeanFactory;
import cn.godk.macaque.spring.core.annotation.AnnotationUtils;
import cn.godk.macaque.spring.utils.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * @author wt
 * @program macaque
 * @create 2021-02-03  10:15
 */
public class AutowiredAnnotationProcessor implements InstantiationAwareBeanPostProcessor {

    private AutowireCapableBeanFactory factory;

    private AutowireCapableBeanFactory beanFactory;
    private String requiredParameterName = "required";
    private boolean requiredParameterValue = true;

    private final Set<Class<? extends Annotation>> autowiredAnnotationTypes =
            new LinkedHashSet<Class<? extends Annotation>>();

    public AutowiredAnnotationProcessor(){
        this.autowiredAnnotationTypes.add(Autowired.class);
    }
    public void setBeanFactory(AutowireCapableBeanFactory beanFactory) {
        this.factory = beanFactory;
    }

    public InjectionMetadata buildAutowiringMetadata(Class<?> clazz) {

        LinkedList<InjectionElement> elements = new LinkedList<InjectionElement>();
 //拿到所有的 field
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
         // 获取字段上的注解
            Annotation autowiredAnnotation = findAutowiredAnnotation(field);
            if(autowiredAnnotation!=null){
                if(Modifier.isStatic(field.getModifiers())){
                    continue;
                }
                boolean b = determineRequiredStatus(autowiredAnnotation);
                elements.add(new AutowiredFieldElement(field,b,this.factory));
            }
        }
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            //TODO 方法注解处理
        }
        return  new InjectionMetadata(clazz,elements);
    }

    private Annotation findAutowiredAnnotation(AccessibleObject ao) {
        for (Class<? extends Annotation> type : this.autowiredAnnotationTypes) {
            Annotation ann = AnnotationUtils.getAnnotation(ao, type);
            if (ann != null) {
                return ann;
            }
        }
        return null;


    }
    /**
     * 功能描述: <br>
     * 〈〉  拿到注解的 require 内容
     * @param ann
     * @return boolean
     * @author weitao
     * @date 2021/2/3 10:23
     */
    protected boolean determineRequiredStatus(Annotation ann) {
        try {
            Method method = ReflectionUtils.findMethod(ann.annotationType(), this.requiredParameterName);
            if (method == null) {
                // Annotations like @Inject and @Value don't have a method (attribute) named "required"
                // -> default to required status
                return true;
            }
            return (this.requiredParameterValue == (Boolean) ReflectionUtils.invokeMethod(method, ann));
        }
        catch (Exception ex) {
            // An exception was thrown during reflective invocation of the required attribute
            // -> default to required status
            return true;
        }
    }
    @Override
    public void postProcessPropertyValues(Object bean, String beanName) throws BeansException {
        InjectionMetadata injectionMetadata = buildAutowiringMetadata(bean.getClass());
        try {
            injectionMetadata.inject(bean);
        }
        catch (Throwable ex) {
            throw new BeanCreationException(beanName, "Injection of autowired dependencies failed", ex);
        }

    }
    @Override
    public Object beforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }

    @Override
    public boolean afterInstantiation(Object bean, String beanName) throws BeansException {
        return false;
    }



    @Override
    public Object beforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object afterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
