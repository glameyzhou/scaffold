package org.glamey.scaffold.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Writer;

/**
 * <p>参考文档地址：http://wiki.fasterxml.com/JacksonInFiveMinutes
 *
 * @author zhouyang.zhou
 */
public class JsonMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonMapper.class);

    private static ObjectMapper mapper = null;

    private JsonMapper() {
    }

    /**
     * 初始化
     *
     * @return {@link JsonMapper}
     */
    public static JsonMapper create() {
        LOGGER.debug("ObjectMapper init...");
        mapper = new ObjectMapper();
//        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);       //属性为NULL 不序列化
//        mapper.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);    //属性为默认值不序列化
//        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);      // 属性为 空（“”） 或者为 NULL 都不序列化
        mapper.configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//    mapper.setDateFormat(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        return new JsonMapper();
    }

    /**
     * 如果对象为Null, 返回"null".
     * 如果集合为空集合, 返回"[]".
     *
     * @param data data
     * @param <T>  the data type
     * @return 返回对象的json字符串
     */
    public <T> String toJson(T data) {
        try {
            return mapper.writeValueAsString(data);
        } catch (IOException e) {
            throw new RuntimeException("write to json string error:" + data, e);
        }
    }

    /**
     * 格式化生成的json
     *
     * @param data the object to json
     * @param <T>  the data type
     * @return to pretty json result
     */
    public <T> String toJsonPretty(T data) {
        try {
            //代码格式化
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
        } catch (IOException e) {
            throw new RuntimeException("write to json string error:" + data, e);
        }
    }

    /**
     * @param writer writer
     * @param data   data
     * @param <T>    the data type
     * @throws IOException 抛出IO异常
     */
    public <T> void write(Writer writer, T data) throws IOException {
        Preconditions.checkNotNull(writer);
        try {
            mapper.writeValue(writer, data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("jackson format error: " + data.getClass(), e);
        }
    }

    /**
     * 反序列化POJO或简单{@code Collection如List<String>}.
     * <p>
     * 如果JSON字符串为Null或"null"字符串, 返回Null.
     * 如果JSON字符串为"[]", 返回空集合.
     * <p>
     * 如需反序列化复杂Collection如{@code List<Bean>}, 请使用
     *
     * @param content the content
     * @param clazz   the class
     * @param <T>     the object type will be return
     * @return {@code T}
     * @see #fromJson(String, JavaType)
     */
    public <T> T fromJson(String content, Class<T> clazz) {
        if (StringUtils.isEmpty(content)) {
            return null;
        }

        try {
            return mapper.readValue(content, clazz);
        } catch (IOException e) {
            throw new RuntimeException("parse json content error:" + content, e);
        }
    }

    /**
     * 反序列化POJO或简单{@code Collection如List<String>}.
     * <p>
     * 如果JSON字符串为Null或"null"字符串, 返回Null.
     * 如果JSON字符串为"[]", 返回空集合.
     * <p>
     * 如需反序列化复杂Collection如{@code List<Bean>}, 请使用
     *
     * @param buffer the bytes content
     * @param clazz  the class
     * @param <T>    the object type will be return
     * @return {@code T}
     * @see #fromJson(String, JavaType)
     */
    public <T> T fromJson(byte[] buffer, Class<T> clazz) {
        if (buffer == null) {
            return null;
        }

        try {
            return mapper.readValue(buffer, clazz);
        } catch (IOException e) {
            throw new RuntimeException("parse bytes content error:", e);
        }
    }

    /**
     * 反序列化复杂Collection如{@code List<Bean>}, 先使用createCollectionType构造类型,然后调用本函数.
     *
     * @param content  the content
     * @param javaType java type
     * @param <T>      the object type will be return
     * @return the object class
     * @see #createCollectionType(Class, Class...)
     */
    @SuppressWarnings("unchecked")
    public <T> T fromJson(String content, JavaType javaType) {
        if (StringUtils.isEmpty(content)) {
            return null;
        }

        try {
            return mapper.readValue(content, javaType);
        } catch (IOException e) {
            throw new RuntimeException("parse json content error:" + content, e);
        }
    }

    /**
     * 反序列化复杂Collection如{@code List<Bean>}, 先使用createCollectionType构造类型,然后调用本函数.
     *
     * @param buffer   the json bytes
     * @param javaType java type
     * @param <T>      the object type will be return
     * @return the object class
     * @see #createCollectionType(Class, Class...)
     */
    public <T> T fromJson(byte[] buffer, JavaType javaType) {
        if (buffer == null)
            return null;

        try {
            return mapper.readValue(buffer, javaType);
        } catch (IOException e) {
            throw new RuntimeException("parse byte error:", e);
        }
    }

    /**
     * 构造的Collection Type如:
     * {@code ArrayList<Bean>}, 则调用constructCollectionType(ArrayList.class,Bean.class)
     * {@code HashMap<String,Bean>}, 则调用(HashMap.class,String.class, Bean.class)
     *
     * @param collectionClass collection class
     * @param elementClasses  the elements class
     * @return java type
     */
    public JavaType createCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    /**
     * 可以通过 {@code new TypeReference<Map<String,List<Bean>>>(){}}来调用
     *
     * @param content the content
     * @param tr      type reference
     * @param <T>     the object type will be return
     * @return {@code T}
     */
    @SuppressWarnings("unchecked")
    public <T> T fromJson(String content, TypeReference<T> tr) {
        if (StringUtils.isEmpty(content)) {
            return null;
        }

        try {
            return (T) mapper.readValue(content, tr);
        } catch (IOException e) {
            throw new RuntimeException("parse json content error:" + content, e);
        }
    }

    /**
     * 可以通过 {@code new TypeReference<Map<String,List<Bean>>>(){}}来调用
     *
     * @param buffer the byte content
     * @param tr     type reference
     * @param <T>    the object type will be return
     * @return {@code T}
     */
    public <T> T fromJson(byte[] buffer, TypeReference<T> tr) {
        if (buffer == null) {
            return null;
        }

        try {
            return (T) mapper.readValue(buffer, tr);
        } catch (IOException e) {
            throw new RuntimeException("parse bytes error:", e);
        }
    }

    /**
     * 当JSON里只含有Bean的部分属性时候，更新一个已经存在的Bean,只覆盖该部分属性.
     *
     * @param content content
     * @param object  object
     * @param <T>     the object type will be return
     * @return {@code T}
     */
    @SuppressWarnings("unchecked")
    public <T> T update(String content, T object) {
        try {
            return mapper.readerForUpdating(object).readValue(content);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("update json content:" + content + " to object:" + object + " error.", e);
        } catch (IOException e) {
            throw new RuntimeException("update json content:" + content + " to object:" + object + " error.", e);
        }
    }

    /**
     * 用于界面JsonP callback使用
     *
     * @param functionName the function name
     * @param data         object
     * @param <T>          the data type
     * @return jsonp string
     */
    public <T> String toJsonP(String functionName, T data) {
        return toJson(new JSONPObject(functionName, data));
    }

    /**
     * 设置是否使用Enun的toString函数来读写Enum,
     * 为false时候使用Enum的name()函数来读写Enum,默认为false.
     * 注意本函数一定要在Mapper创建后,所有的该读写动作之前调用
     */
    public void enableEnumUseToString() {
        mapper.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
        mapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);
    }

    /**
     * 取出Mapper做进一步的设置或使用其他序列化API.
     *
     * @return {@link ObjectMapper}
     */
    public ObjectMapper getMapper() {
        return mapper;
    }
}
