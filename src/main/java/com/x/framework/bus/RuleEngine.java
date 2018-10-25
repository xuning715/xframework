package com.x.framework.bus;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.x.framework.Base;
import com.x.framework.model.BaseModel;
import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class RuleEngine {
    private final static String js = "js";

    public static boolean validateRule(BaseModel obj, List<String> rules) throws Exception {
        Map<String, Object> map = transBean2Map(obj);
        return validateRule(map, rules);
    }

    public static boolean validateRule(Map<String, Object> map, List<String> rules) throws Exception {
        boolean responseFlag = true;
        for (String rule : rules) {
            responseFlag = validateRule(map, rule);
            if (!responseFlag) {
                break;
            }
        }
        return responseFlag;
    }

    public static boolean validateRule(Map<String, Object> map, String rule) throws Exception {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            rule = rule.replaceAll(entry.getKey(), entry.getValue() + Base.BLANK);
        }
        return evalRule(rule, Boolean.class);
    }

    private static <T> T evalRule(String expression, Class<T> clazz) throws Exception {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine se = manager.getEngineByName(js);
        Object value = se.eval(expression);
        if (null == value) {
            return null;
        } else {
            return (T) value;
        }
    }

    private <T> T evalRule(String expression, Map<String, String> map, Class<T> clazz) {
        JexlEngine jexl = new JexlEngine();
        Expression e = jexl.createExpression(expression);
        JexlContext jc = new MapContext();
        for (String key : map.keySet()) {
            jc.set(key, map.get(key));
        }
        Object value = e.evaluate(jc);
        if (null == value) {
            return null;
        } else {
            return (T) value;
        }
    }

    private static Map<String, Object> transBean2Map(BaseModel obj) throws Exception {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            String key = property.getName();
            // 过滤class属性
            if (!key.equals("class")) {
                // 得到property对应的getter方法
                Method getter = property.getReadMethod();
                Object value = getter.invoke(obj);
                map.put(key, value);
            }
        }
        return map;
    }

    public static void main(String[] args) {
        // try {
        // Map<String, Object> map = new HashMap<String, Object>();
        // map.put("money", 2100);
        // String expression = "money>=2000&&money<=4000";
        // Object code = convertToCode(expression, map);
        // } catch (Exception e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        // try {
        // Map<String,Object> map=new HashMap<String,Object>();
        // map.put("testService",testService);
        // map.put("person",person);
        // String expression="testService.save(person)";
        // convertToCode(expression,map);
        // } catch (Exception e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
    }

}
