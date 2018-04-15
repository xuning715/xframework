package com.x.framework.dao;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取数据源，用于动态切换数据源
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    private static final String COMMA = ",";
    public static Map<String, List<String>> METHOD_TYPE_MAP = new HashMap<String, List<String>>();

    /**
     * 实现父类中的抽象方法，获取数据源名称
     *
     * @return
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceHandler.getDataSource();
    }

    // 设置方法名前缀对应的数据源
    public void setMethodType(Map<String, String> map) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            List<String> v = new ArrayList<String>();
            String[] types = entry.getValue().split(COMMA);
            for (String type : types) {
                if (StringUtils.isNotBlank(type)) {
                    v.add(type);
                }
            }
            METHOD_TYPE_MAP.put(entry.getKey(), v);
        }
    }
}
