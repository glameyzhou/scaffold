package org.glamey.scaffold.component.exception;

/**
 * 公共组件异常类
 *
 * @author zhouyang.zhou
 */
public class ComponentException extends RuntimeException {

    private int code;

    public ComponentException() {
        super();
    }

    public ComponentException(String message) {
        super(message);
    }

    public ComponentException(int code, String message) {
        super(message);
        this.code = code;
    }

    public ComponentException(String message, Throwable cause) {
        super(message, cause);
    }

    public ComponentException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public ComponentException(Throwable cause) {
        super(cause);
    }

    protected ComponentException(String message, Throwable cause,
                                 boolean enableSuppression,
                                 boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public int getCode() {
        return code;
    }
}
