package org.glamey.scaffold.web.mvc;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.glamey.scaffold.web.mvc.handler.JsonBodyExceptionResolver;
import org.glamey.scaffold.web.mvc.handler.JsonBodyMethodProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.CollectionUtils;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.lang.reflect.Method;
import java.util.List;

/**
 * WEB启动扫描代理
 *
 * @author by zhouyang.zhou.
 */
@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurationSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebConfig.class);

    @Bean
    public JsonBodyExceptionResolver jsonBodyExceptionResolver() {
        return new JsonBodyExceptionResolver();
    }

    @Bean
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
        RequestMappingHandlerAdapter adapter = super.requestMappingHandlerAdapter();
        List<HandlerMethodReturnValueHandler> returnValueHandlers = getDefault(adapter, "getDefaultReturnValueHandlers");

        /**
         * 设置{@link JsonBodyMethodProcessor}为第一个处理器
         */
        returnValueHandlers.add(0, new JsonBodyMethodProcessor());
        adapter.setReturnValueHandlers(returnValueHandlers);

        rebuildMessageConverter(adapter);

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


    private void rebuildMessageConverter(RequestMappingHandlerAdapter adapter) {
        List<HttpMessageConverter<?>> messageConverters = adapter.getMessageConverters();
        if (!CollectionUtils.isEmpty(messageConverters)) {
            for (HttpMessageConverter<?> messageConverter : messageConverters) {
                if (messageConverter instanceof MappingJackson2HttpMessageConverter) {
                    rebuildJacksonV2((MappingJackson2HttpMessageConverter) messageConverter);
                }

//                if (messageConverter instanceof MappingJacksonHttpMessageConverter) {
//                    rebuildJacksonV1((MappingJacksonHttpMessageConverter) messageConverter);
//                }
            }
        }

        /**
         * 处理form表单
         * form表单通过get方法请求,controller里边的方法中只需设置一个接收的对象即可.
         */
        adapter.getMessageConverters().add(new FormHttpMessageConverter());

    }
/*
    private void rebuildJacksonV1(MappingJacksonHttpMessageConverter messageConverter) {
        final org.codehaus.jackson.map.ObjectMapper mapper = new org.codehaus.jackson.map.ObjectMapper();
        mapper.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
        messageConverter.setObjectMapper(mapper);
        LOGGER.info("-----> rebuild MappingJacksonHttpMessageConverter");
    }*/

    private void rebuildJacksonV2(MappingJackson2HttpMessageConverter messageConverter) {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        messageConverter.setObjectMapper(mapper);
        LOGGER.info("-----> rebuild MappingJackson2HttpMessageConverter");
    }


}
