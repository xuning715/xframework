package com.x.framework.aop;

import com.x.framework.dao.DataSourceHandler;
import com.x.framework.dao.DynamicDataSource;
import org.aspectj.lang.JoinPoint;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 切换数据源(不同方法调用不同数据源)
 */
public class DataSourceAspect {
    private static Map<String, String> methodKeyMap = new HashMap<String, String>();

    // 切面 before方法
    public void before(JoinPoint point) {
//        String className = point.getTarget().getClass().getName();
        String method = point.getSignature().getName();
        String key = methodKeyMap.get(method);
        if (key != null) {
            DataSourceHandler.putDataSource(key);
        } else {
            List<String> prefixList;
            for (Map.Entry<String, List<String>> entry : DynamicDataSource.METHOD_TYPE_MAP.entrySet()) {
                key = entry.getKey();
                prefixList = entry.getValue();
                for (String prefix : prefixList) {
                    if (method.startsWith(prefix)) {
                        DataSourceHandler.putDataSource(key);
                        methodKeyMap.put(method, key);
                    }
                }
            }
        }
    }

}
