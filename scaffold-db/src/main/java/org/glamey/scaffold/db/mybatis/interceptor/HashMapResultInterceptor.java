package org.glamey.scaffold.db.mybatis.interceptor;

import com.google.common.collect.Lists;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.*;

import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static org.apache.commons.lang3.StringUtils.defaultIfBlank;

/**
 * 针对mybatis resultType="java.util.HashMap",并且Dao中声明 Map queryForMap();的方法进行处理
 * <p>
 * 如果Dao中声明了MapKey，此方法将失效
 *
 * @author by zhouyang.zhou.
 */

@Intercepts({@Signature(
        type = ResultSetHandler.class,
        method = "handleResultSets",
        args = {Statement.class})})
public class HashMapResultInterceptor implements Interceptor {
    private String mapKey;
    private String mapValue;

    /**
     * @param invocation 拦截实例
     * @return Object
     * @throws Throwable 异常
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        /*Object target = invocation.getTarget();
        if (!target.getClass().isAssignableFrom(DefaultResultSetHandler.class)) {
            return invocation.proceed();
        }
        DefaultResultSetHandler resultSetHandler = (DefaultResultSetHandler) target;
        Statement statement = (Statement) invocation.getArgs()[0];
        List<Object> objectList = resultSetHandler.handleResultSets(statement);
        if (CollectionUtils.isEmpty(objectList))
            return objectList;*/

        List<Object> objectList = (List<Object>) invocation.proceed(); // process result never be null
        if (objectList.size() == 0)
            return objectList;

        Map resultMap = new HashMap<>();
        for (Object o : objectList) {
            if (!o.getClass().isAssignableFrom(HashMap.class))
                return objectList;

            Map map = (Map) o;
            resultMap.put(map.get(this.mapKey), map.get(this.mapValue));

        }
        return Lists.newArrayList(resultMap);
    }

    /**
     * 加载插件
     *
     * @param target 目标
     * @return object
     */
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    /**
     * 加载拦截器的配置属性
     *
     * @param properties 配置文件
     */
    @Override
    public void setProperties(Properties properties) {
        this.mapKey = defaultIfBlank(properties.getProperty("mapKey"), "key");
        this.mapValue = defaultIfBlank(properties.getProperty("mapValue"), "value");
    }
}
