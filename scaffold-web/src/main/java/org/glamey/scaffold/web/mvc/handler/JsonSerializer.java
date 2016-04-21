package org.glamey.scaffold.web.mvc.handler;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import org.glamey.scaffold.json.Codes;
import org.glamey.scaffold.json.JsonMapper;
import org.glamey.scaffold.json.JsonV1;
import org.glamey.scaffold.web.mvc.annotation.JsonBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Method;

import static org.glamey.scaffold.json.Codes.SYSTEM_ERROR;


/**
 * 数据序列化为json字符串
 *
 * @author by zhouyang.zhou.
 */
final class JsonSerializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonSerializer.class);
    public static final String CONTENT_TYPE_JSON = "application/json; charset=UTF-8";
    public static final String CONTENT_TYPE_JAVASCRIPT = "application/javascript; charset=UTF-8";

    static void write(Object value, Method method, HttpServletRequest request, HttpServletResponse response) {
        JsonBody meta = method.getAnnotation(JsonBody.class);
        LOGGER.debug("meta={}, value={}", meta, value);

        final JsonMapper jsonMapper = JsonMapper.create();

        SerializationFeature[] enables = meta.enable();
        if (enables != null && enables.length > 0) {
            for (SerializationFeature enable : enables) {
                jsonMapper.getMapper().enable(enable);
            }
        }

        SerializationFeature[] disables = meta.disable();
        if (disables != null && disables.length > 0) {
            for (SerializationFeature disable : disables) {
                jsonMapper.getMapper().disable(disable);
            }
        }

        String callback = Strings.emptyToNull(request.getParameter(meta.callback()));
        try {
            response(callback, buildValue(value), jsonMapper, response);
        } catch (IOException e) {
            LOGGER.error("response write error", e);
        }
    }

    private static void response(String callback, Object value, JsonMapper jsonMapper, HttpServletResponse response) throws IOException {
        if (callback != null) {
            response.setContentType(CONTENT_TYPE_JAVASCRIPT);
            Writer writer = response.getWriter();
            writer.write(callback);
            writer.write('(');
            jsonMapper.write(writer, value);
            writer.write(')');
        } else {
            response.setContentType(CONTENT_TYPE_JSON);
            jsonMapper.write(response.getWriter(), value);
        }
    }

    private static Object buildValue(Object value) {
        if (value instanceof JsonV1) {
            return value;
        }

        if (value instanceof Throwable) {
            return new JsonV1<>(SYSTEM_ERROR,
                    Throwables.getStackTraceAsString((Throwable) value),
                    null);
        }

        return new JsonV1<>(Codes.OK, "OK", value);
    }
}
