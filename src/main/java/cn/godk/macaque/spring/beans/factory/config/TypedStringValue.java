package cn.godk.macaque.spring.beans.factory.config;

/**
 * @author wt
 * @program macaque
 * @create 2020-12-25  11:08
 */
public class TypedStringValue {
    /**
     * 功能描述: <br>
     * 〈〉  字符串属性 值
     *
     * @param null
     * @return
     * @author weitao
     * @date 2020/12/25 11:08
     */
    private String value;

    public TypedStringValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
