package org.glamey.scaffold.db.mybatis.handler;

import org.apache.ibatis.executor.result.DefaultMapResultHandler;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.session.ResultContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @author by zhouyang.zhou.
 */
public class MapResultHandler<K, V> extends DefaultMapResultHandler {
    private final String mapValue;

    /**
     * @param mapKey               mapKey
     * @param mapValue             mapValue
     * @param objectFactory        objectFactory
     * @param objectWrapperFactory objectWrapperFactory
     */
    public MapResultHandler(String mapKey, String mapValue, ObjectFactory objectFactory, ObjectWrapperFactory objectWrapperFactory) {
        super(mapKey, objectFactory, objectWrapperFactory);
        this.mapValue = mapValue;
    }

    /**
     * handle the result
     *
     * @param context the result context
     */
    @Override
    public void handleResult(ResultContext context) {
        super.handleResult(context);
        Map<K, V> mappedResults = getMappedResults();
        K key = null;
        for (Map.Entry<K, V> entry : mappedResults.entrySet()) {
            V value = entry.getValue();
            key = entry.getKey();
            /**
             * 默认的Map实例为HashMap
             * {@link DefaultObjectFactory#resolveInterface}
             */
            if (value.getClass().isAssignableFrom(HashMap.class)) {
                key = entry.getKey();
                break;
            }
        }
        if (mappedResults.get(key) == null) {
            getMappedResults().put(key, null);
        } else {
            Map subMap = (Map) mappedResults.get(key);
            getMappedResults().put(key, subMap.get(this.mapValue));
        }
    }
}
