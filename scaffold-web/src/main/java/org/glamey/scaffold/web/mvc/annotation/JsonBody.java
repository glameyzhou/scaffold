package org.glamey.scaffold.web.mvc.annotation;

import com.fasterxml.jackson.databind.SerializationFeature;

import java.lang.annotation.*;

/**
 * 用于标注MVC相应的结果为JSON字符串的特殊化标准统一处理
 *
 * @author by zhouyang.zhou.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JsonBody {
    String callback() default "callback";

    Version version() default Version.v1;

    SerializationFeature[] enable() default {};

    SerializationFeature[] disable() default {};

    enum Version {
        v1
    }
}
