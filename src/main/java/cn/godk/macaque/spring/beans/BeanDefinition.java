package cn.godk.macaque.spring.beans;

import java.util.List;

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

    /**
     * 功能描述: <br>
     * 〈〉  is singleton
     *
     * @param
     * @return boolean
     * @author weitao
     * @date 2020/12/25 10:35
     */
    public boolean isSingleton();

    /**
     * 功能描述: <br>
     * 〈〉 isPrototype
     *
     * @param
     * @return boolean
     * @author weitao
     * @date 2020/12/25 10:35
     */
    public boolean isPrototype();

    /**
     * 功能描述: <br>
     * 〈〉  getScope
     *
     * @param
     * @return java.lang.String
     * @author weitao
     * @date 2020/12/25 10:35
     */
    String getScope();

    /**
     * 功能描述: <br>
     * 〈〉  setScope
     *
     * @param scope
     * @return void
     * @author weitao
     * @date 2020/12/25 10:35
     */
    void setScope(String scope);

    /**
     * 功能描述: <br>
     * 〈〉  注入的 属性
     *
     * @param
     * @return java.util.List<cn.godk.macaque.spring.beans.PropertyValue>
     * @author weitao
     * @date 2020/12/25 10:35
     */
    public List<PropertyValue> getPropertyValues();
}
