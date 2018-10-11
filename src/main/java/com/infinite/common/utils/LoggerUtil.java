package com.infinite.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
* @ClassName: LoggerUtil
* @Description: logger工具类
* @author chenliqiao
* @date 2018年8月10日 下午3:24:39
*
 */
public class LoggerUtil {
    
    public static <T> Logger Logger(Class<T> clazz){
        return LoggerFactory.getLogger(clazz);
    }
    
    /**
     * 打印到指定的文件下
     *
     * @param desc 日志文件名称
     * @return
     */
    public static Logger Logger(LogFileName desc) {
        return LoggerFactory.getLogger(desc.getLogFileName());
    }
    
    /**
     * 
    * @ClassName: LogFileName
    * @Description: 日志文件名配置类
    * @author chenliqiao
    * @date 2018年8月10日 下午3:41:46
    *
     */
    public enum LogFileName {

        //配置到logback.xml中的logger标签的name属性中
        USER_SERVICE("userService");

        private String logFileName;

        LogFileName(String fileName) {
            this.logFileName = fileName;
        }

        public String getLogFileName() {
            return logFileName;
        }

        public void setLogFileName(String logFileName) {
            this.logFileName = logFileName;
        }

        public static LogFileName getAwardTypeEnum(String value) {
            LogFileName[] arr = values();
            for (LogFileName item : arr) {
                if (null != item && StringUtils.isNotBlank(item.logFileName)) {
                    return item;
                }
            }
            return null;
        }
    }


}
