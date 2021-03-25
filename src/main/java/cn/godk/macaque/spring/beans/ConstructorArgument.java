package cn.godk.macaque.spring.beans;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

import java.util.LinkedList;
import java.util.List;

/**
 * @author wt
 * @program macaque
 * @create 2021-01-19  09:33
 */
public class ConstructorArgument {

    private final List<ValueHolder> argumentValues = new LinkedList<ValueHolder>();


    public void add(ValueHolder valueHolder){
        this.argumentValues.add(valueHolder);
    }

    public List<ValueHolder> getArgumentValues() {
        return this.argumentValues;
    }

    public boolean isEmpty(){
        return argumentValues.isEmpty();
    }

    public void addArgumentValue(Object methodDef) {
        this.argumentValues.add(new ValueHolder(methodDef));
    }


    public static class  ValueHolder{


        private String type;

        private Object value;

        private String name ;

        public ValueHolder(Object value) {
            this.value = value;
        }

        public ValueHolder() {
        }

        public String getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }


}
}
