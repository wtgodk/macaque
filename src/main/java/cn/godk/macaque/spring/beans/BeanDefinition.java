package cn.godk.macaque.spring.beans;

/**
 * @author: godk
 * @program: macaque
 * @create: 2018-12-03  11:14
 */
public interface BeanDefinition {

    public static final String SCOPE_SINGLETON = "singleton";
    public static final String SCOPE_PROTOTYPE = "prototype";
    public static final String SCOPE_DEFAULT = "";
    /**
     * get  class name
     *
     * @return
     */
    String getBeanClassName();


    public boolean isSingleton();

    public boolean isPrototype();

    String getScope();

    void setScope(String scope);
}
