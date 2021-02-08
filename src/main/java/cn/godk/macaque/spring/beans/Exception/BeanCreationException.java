package cn.godk.macaque.spring.beans.Exception;

/**
 * for bean create  runtime exception
 *
 * @author: godk
 * @program: macaque
 * @create: 2018-12-03  11:45
 */
public class BeanCreationException extends BeansException {

    private String beanName;


    public BeanCreationException(String beanName, String msg, Throwable ex) {
        super(msg);
    }

    public BeanCreationException(String beanName) {
        super(beanName);
    }

    public BeanCreationException(String msg, Throwable cause) {
        super(msg, cause);
    }
    public BeanCreationException(String beanName, String msg) {
        super("Error creating bean with name '" + beanName + "': " + msg);
        this.beanName = beanName;
    }

}
