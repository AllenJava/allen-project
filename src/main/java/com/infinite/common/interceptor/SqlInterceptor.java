package com.infinite.common.interceptor;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Properties;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.infinite.common.constant.Constants;


/**
 * 
* @ClassName: SqlInterceptor
* @Description: SQL拦截器
* @author chenliqiao
* @date 2019年1月17日 上午11:32:10
*
 */
@Intercepts(
        {
            @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
            @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
        }
)
@Component
public class SqlInterceptor implements Interceptor{
    
    protected final Logger LOG=LoggerFactory.getLogger(getClass());
    
    protected Properties properties;
    
    @Value("${sqlInterceptor.printSqlSwitch}")
    private String printSqlSwitch;

    @SuppressWarnings("rawtypes")
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        Object parameter = args[1];
        //获取当前mapped的namespace
        String mappedStatementId = ms.getId();
        Object returnValue=null;
        Long startTime=System.currentTimeMillis();
        //insert/update/delete语句
        if(args.length==2){
            returnValue=invocation.proceed();
            if(Objects.equals(Constants.SQL_PRINT_SWITCH, this.printSqlSwitch)){
                LOG.info("*********************** 打印SQL（执行时长：{}ms）***************************",(System.currentTimeMillis()-startTime));
                LOG.info(" ==> "+mappedStatementId);
                LOG.info(" ==> "+showSql(ms.getConfiguration(), ms.getBoundSql(parameter))+"\n");
            }    
            return returnValue;
        } 
        
        //select语句
        RowBounds rowBounds = (RowBounds) args[2];
        ResultHandler resultHandler = (ResultHandler) args[3];
        Executor executor = (Executor) invocation.getTarget();
        CacheKey cacheKey;
        BoundSql boundSql;
        if(args.length == 4){
            boundSql = ms.getBoundSql(parameter);
            cacheKey = executor.createCacheKey(ms, parameter, rowBounds, boundSql);
        } else {
            cacheKey = (CacheKey) args[4];
            boundSql = (BoundSql) args[5];
        }
        returnValue=executor.query(ms, parameter, rowBounds, resultHandler, cacheKey, boundSql);
        if(Objects.equals(Constants.SQL_PRINT_SWITCH, this.printSqlSwitch)){
            LOG.info("*********************** 打印SQL（执行时长：{}ms） ***************************",(System.currentTimeMillis()-startTime));
            LOG.info(" ==> "+mappedStatementId);
            LOG.info(" ==> "+showSql(ms.getConfiguration(), boundSql)+"\n");
        }
        return returnValue;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        this.properties=properties;
    }
    
    /**
     * 展示SQL
     */
    private  String showSql(Configuration configuration, BoundSql boundSql) {
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        if (parameterMappings.size() > 0 && parameterObject != null) {
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                sql = sql.replaceFirst("\\?", getParameterValue(parameterObject));
 
            } else {
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);
                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
                    }
                }
           }
    }
    return sql;
   }
   
   /**
    * 获取SQL参数值
    */
   private String getParameterValue(Object obj) {
        String value = null;
        if (obj instanceof String) {
            value = "'" + obj.toString() + "'";
        } else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
            value = "'" + formatter.format(obj) + "'";
        } else {
            if (obj != null) {
                value = obj.toString();
            } else {
                value = "";
            }
        }
        return value;
    }

}
