package org.glamey.scaffold.web.mvc.handler;

import org.glamey.scaffold.web.mvc.annotation.JsonBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.mvc.annotation.ModelAndViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 针对{@link JsonBody}注解出现的异常处理
 *
 * @author by zhouyang.zhou.
 */
public class JsonBodyExceptionResolver extends SimpleMappingExceptionResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonBodyExceptionResolver.class);


    public JsonBodyExceptionResolver() {
        this.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response,
                                              Object _handler, Exception ex) {
        LOGGER.info("jsonBody exception....");
        HandlerMethod handler = (HandlerMethod) _handler;
        if (handler == null) {
            // like 'GET' not supported
            return null;
        }

        Method method = handler.getMethod();

        if (method.isAnnotationPresent(JsonBody.class)) {
//            String config = super.determineViewName(ex, request);
//            Object value = Strings.isNullOrEmpty(config) ? ex : JsonSerializer.parseConfig(config, ex);
            JsonSerializer.write(ex, method, request, response);
            // skip other resolver and view render
            return ModelAndViewResolver.UNRESOLVED;
        }

        return null;
    }
}
