package cn.godk.macaque.spring.beans.factory.config;

/**
 * @author wt
 * @program macaque
 * @create 2020-12-25  11:07
 */
public class RuntimeBeanReference {

    /**
     * 功能描述: <br>
     * 〈〉  注入bean name 用于获取 bean实例
     *
     * @param null
     * @return
     * @author weitao
     * @date 2020/12/25 11:07
     */
    private String beanName;

    public RuntimeBeanReference(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }
}
