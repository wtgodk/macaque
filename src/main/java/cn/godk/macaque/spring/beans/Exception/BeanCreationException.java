package cn.godk.macaque.spring.beans.Exception;

/**
 * for bean create  runtime exception
 *
 * @author: godk
 * @program: macaque
 * @create: 2018-12-03  11:45
 */
public class BeanCreationException extends BeansException {

    public BeanCreationException(String msg) {
        super(msg);
    }

    public BeanCreationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
