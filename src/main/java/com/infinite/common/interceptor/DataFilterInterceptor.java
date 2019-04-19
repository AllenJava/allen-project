package com.infinite.common.interceptor;

import java.util.Properties;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.infinite.common.constant.Constants.DataFilterConstant;
import com.infinite.common.utils.LocalUserCacheUtil;
import com.infinite.common.utils.ReflectUtil;
import com.infinite.service.dto.CurrentUserCacheDTO;


/**
 * 
* @ClassName: DataFilterInterceptor
* @Description:数据权限拦截器
* @author chenliqiao
* @date 2019年1月11日 上午10:56:59
*
 */
@Intercepts(
    {
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
    }
)
public class DataFilterInterceptor implements Interceptor{
    
    protected final Logger LOG=LoggerFactory.getLogger(getClass());    

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {}
    
    @SuppressWarnings("rawtypes")
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
         Object[] args = invocation.getArgs();
         MappedStatement ms = (MappedStatement) args[0];
         Object parameter = args[1];
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
         //获取当前mapped的namespace
         String mappedStatementId = ms.getId();
         String currentClassName = mappedStatementId.substring(0, mappedStatementId.lastIndexOf("."));
         //判断DataFilterConstant常量类中配置的namespace是否与当前的mapped namespace匹配，如果包含则进行拦截，否则放行
         if(DataFilterConstant.namespaces.contains(currentClassName)){
             ReflectUtil.setFieldValue(boundSql, "sql", this.dataFilterSql(boundSql.getSql()));
         }        
         return executor.query(ms, parameter, rowBounds, resultHandler, cacheKey, boundSql);
    }
    
    /**
     * 数据权限sql包装
     */
    protected String dataFilterSql(String sql) {
        CurrentUserCacheDTO localUser=LocalUserCacheUtil.get();
        StringBuilder sbSql = new StringBuilder(sql);
        if(localUser!=null && localUser.getChannelId()!=null){
            LOG.info("当前用户的渠道id为->{}",localUser.getChannelId());
            sbSql = new StringBuilder("select * from (")
                    .append(sbSql)
                    .append(" ) s ")
                    .append(" where s.channel_id=")
                    .append(localUser.getChannelId());
        }
        return sbSql.toString();
    }
}
