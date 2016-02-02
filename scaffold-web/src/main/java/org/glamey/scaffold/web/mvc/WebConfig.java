package org.glamey.scaffold.web.mvc;

import org.glamey.scaffold.web.mvc.handler.JsonBodyExceptionResolver;
import org.glamey.scaffold.web.mvc.handler.JsonBodyMethodProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.lang.reflect.Method;
import java.util.List;

/**
 * WEB启动扫描代理
 *
 * @author by zhouyang.zhou.
 */
//@Configuration
//@EnableWebMvc
public class WebConfig extends WebMvcConfigurationSupport {
    @Bean
    public JsonBodyExceptionResolver jsonBodyExceptionResolver() {
        return new JsonBodyExceptionResolver();
    }

    @Bean
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {

        RequestMappingHandlerAdapter adapter = super.requestMappingHandlerAdapter();
        //set JsonBodyMethodProcessor as the first
        List<HandlerMethodReturnValueHandler> returnValueHandlers = getDefault(adapter, "getDefaultReturnValueHandlers");
        returnValueHandlers.add(0, new JsonBodyMethodProcessor());
        adapter.setReturnValueHandlers(returnValueHandlers);

        return adapter;
    }

    @SuppressWarnings("unchecked")
    private <T> List<T> getDefault(RequestMappingHandlerAdapter adapter, String methodName) {
        try {
            Method method = RequestMappingHandlerAdapter.class.getDeclaredMethod(methodName);
            method.setAccessible(true);
            return (List<T>) method.invoke(adapter);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
