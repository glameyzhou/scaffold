package org.glamey.scaffold.web.mvc.handler;

import org.glamey.scaffold.web.mvc.annotation.JsonBody;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 针对{@link JsonBody}标签的业务处理
 *
 * @author by zhouyang.zhou.
 */
public class JsonBodyMethodProcessor implements HandlerMethodReturnValueHandler {
    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return returnType.getMethodAnnotation(JsonBody.class) != null;
    }

    @Override
    public void handleReturnValue(final Object returnValue, MethodParameter returnType,
                                  ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {

        mavContainer.setRequestHandled(true);

        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);

        JsonSerializer.write(returnValue, returnType.getMethod(), request, response);
    }
}
