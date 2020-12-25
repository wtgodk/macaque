package cn.godk.macaque.spring.beans;

/**
 * 属性注入
 *
 * @author wt
 * @program macaque
 * @create 2020-12-25  10:33
 */
public class PropertyValue {


    private final String name;

    private final Object value;


    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }


    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }
}
