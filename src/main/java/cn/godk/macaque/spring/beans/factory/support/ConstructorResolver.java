package cn.godk.macaque.spring.beans.factory.support;

import cn.godk.macaque.spring.beans.BeanDefinition;
import cn.godk.macaque.spring.beans.ConstructorArgument;
import cn.godk.macaque.spring.beans.Exception.BeanCreationException;
import cn.godk.macaque.spring.beans.SimpleTypeConverter;
import cn.godk.macaque.spring.beans.factory.config.ConfigurableBeanFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.List;

/**
 *
 *  构造函数   解析
 *
 * @author wt
 * @program macaque
 * @create 2021-01-19  09:57
 */
public class ConstructorResolver {

    private ConfigurableBeanFactory  configurableBeanFactory;

    private BeanDefinitionValueResolver beanDefinitionValueResolver ;
    private  SimpleTypeConverter typeConverter = new SimpleTypeConverter();

    public ConstructorResolver(ConfigurableBeanFactory configurableBeanFactory) {
        this.configurableBeanFactory = configurableBeanFactory;
        this.beanDefinitionValueResolver = new BeanDefinitionValueResolver(configurableBeanFactory);

    }

    public Object autowireConstructor(BeanDefinition bd)  {
        ConstructorArgument constructorArgument = bd.getConstructorArgument();
        List<ConstructorArgument.ValueHolder> argumentValues = constructorArgument.getArgumentValues();
        Class<?> beanClass = null;
        try {
            // 实例化 bd
           beanClass = configurableBeanFactory.getBeanClassLoader().loadClass(bd.getBeanClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        // 获取所有构造函数
        Constructor<?>[] constructors = beanClass.getConstructors();
        Object[] argsToUse   = new Object[argumentValues.size()];
        Constructor<?> constructorToUse = null;
        for (Constructor<?> constructor : constructors) {
            int parameterCount = constructor.getParameterCount();
            if(parameterCount != argumentValues.size()){
                //参数数量就不对
                continue;
            }
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            for(int i=0;i<parameterTypes.length;i++){
                Class<?> parameterType = parameterTypes[i];
                ConstructorArgument.ValueHolder valueHolder = argumentValues.get(i);
                Object o = this.beanDefinitionValueResolver.resolveValueIfNecessary(valueHolder.getValue());
                try {
                    Object o1 = typeConverter.convertIfNecessary(o, parameterType);
                    argsToUse[i] = o1;
                }catch (Exception e){
                    break;
                }
            }
           if(argsToUse.length == argumentValues.size()){
               constructorToUse = constructor;
            }
        }
        if(constructorToUse!=null){
            try {
                return      constructorToUse.newInstance(argsToUse);
            } catch (InstantiationException e) {
                throw new BeanCreationException( bd.getID(), "can't find a apporiate constructor");
            } catch (IllegalAccessException e1) {
                throw new BeanCreationException( bd.getID(), "can't find a apporiate constructor");
            } catch (InvocationTargetException e) {
                throw new BeanCreationException( bd.getID(), "can't find a apporiate constructor");
            }
        }else{
            throw new BeanCreationException( bd.getID(), "can't find a apporiate constructor");
        }


    }
}
