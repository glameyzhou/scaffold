package org.glamey.scaffold.db.mybatis.interceptor;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.glamey.scaffold.db.mybatis.handler.MapResultHandler;

import java.util.Properties;

import static org.apache.commons.lang3.StringUtils.defaultIfBlank;

/**
 * 针对mybatis resultType="java.util.HashMap",并且Dao中声明 Map queryForMap();的方法进行处理
 * <p>
 * 并且Dao中声明了MapKey，此方法将失效
 *
 * @author by zhouyang.zhou.
 */
@Intercepts({@Signature(
        type = Executor.class,
        method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class MapKeyResultInterceptor implements Interceptor {

    private String mapKey;
    private String mapValue;

    /**
     * intercept
     *
     * @param invocation invocation
     * @return invoker object
     * @throws Throwable Exception
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement statement = (MappedStatement) args[0];
        Configuration configuration = statement.getConfiguration();
        ResultHandler resultHandler = new MapResultHandler<>(this.mapKey, this.mapValue, configuration.getObjectFactory(), configuration.getObjectWrapperFactory());
        invocation.getArgs()[3] = resultHandler;
        return invocation.proceed();
    }

    /**
     * load the plugin
     *
     * @param target invoker
     * @return object
     */
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    /**
     * load the properties
     *
     * @param properties the properties configuration
     */
    @Override
    public void setProperties(Properties properties) {
        this.mapKey = defaultIfBlank(properties.getProperty("mapKey"), "key");
        this.mapValue = defaultIfBlank(properties.getProperty("mapValue"), "value");
    }
}
