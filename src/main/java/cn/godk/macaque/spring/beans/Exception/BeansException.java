package cn.godk.macaque.spring.beans.Exception;

/**
 * Exception for beans Base class
 *
 * @author: godk
 * @program: macaque
 * @create: 2018-12-03  11:38
 */
public class BeansException extends RuntimeException {

    public BeansException(String msg) {
        super(msg);
    }

    public BeansException(String msg, Throwable cause) {

        super(msg, cause);
    }

}
