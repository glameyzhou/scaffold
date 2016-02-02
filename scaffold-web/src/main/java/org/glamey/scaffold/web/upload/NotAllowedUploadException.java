package org.glamey.scaffold.web.upload;

/**
 * <p>上传文件后缀不匹配异常类
 *
 * @author zhouyang.zhou
 */
public class NotAllowedUploadException extends RuntimeException {

    public NotAllowedUploadException() {
    }

    public NotAllowedUploadException(String message) {
        super(message);
    }

    public NotAllowedUploadException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotAllowedUploadException(Throwable cause) {
        super(cause);
    }

    public NotAllowedUploadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
