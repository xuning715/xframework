package com.x.framework.aop;

import com.alibaba.fastjson.JSON;
import com.x.framework.Base;
import com.x.framework.annotation.MappingTable;
import com.x.framework.annotation.NotKey;
import com.x.framework.model.BaseModel;
import com.x.framework.model.MappingModel;
import com.x.framework.model.ModelMap;
import com.x.framework.redis.XRedisTemplate;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

@Service
public class RedisAspect {

    private final static String DOT = ".";
    private final static String AT = "@";
    private final static String QUOTE = "\"";
    private final static String MINUS = "-";
    private final static String LEFTBRACKET = "[{]";
    private final static String RIGHTBRACKET = "}";
    private final static String LEFTRIGHTBRACKETS = "[{]}";
    private final static String RIGHTLEFTBRACKETS = "}[{]";
    private int expireSeconds;

    @Autowired
    private XRedisTemplate xRedisTemplate;

    public RedisAspect(int expireSeconds) {
        this.expireSeconds = expireSeconds;
    }

    public Object aroundRedisGet(ProceedingJoinPoint point) throws Throwable {
        Object[] args = point.getArgs();
        if (args == null || args.length == 0) {
            throw new Exception("args is null");
        }
        String className = point.getTarget().toString();
        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        Annotation[][] paramAnnotations = method.getParameterAnnotations();
        String key = className.substring(className.lastIndexOf(DOT) + 1, className.indexOf(AT)) + signature.getName();
        int i = 0;
        MappingTable mappingTable;
        String argJson;
        for (Object arg : args) {
            argJson = arg == null ? Base.BLANK : JSON.toJSONString(arg);
            if (paramAnnotations[i].length == 0 || !paramAnnotations[i][0].annotationType().equals(NotKey.class)) {
                if (i == 0) {
                    if (!(BaseModel.class.isInstance(arg))) {
                        throw new Exception("arg0 is not instance of BaseModel");
                    }
                    mappingTable = arg.getClass().getAnnotation(MappingTable.class);
                    if (mappingTable == null) {
                        throw new Exception("arg0's mappingTable annotation is null");
                    }
                    key = mappingTable.tableName().toUpperCase() + key + argJson;
                } else {
                    key += argJson;
                }
            }
            i++;
        }
        key = key.replaceAll(QUOTE, Base.BLANK).replaceAll(LEFTRIGHTBRACKETS, Base.BLANK).replaceAll(RIGHTLEFTBRACKETS, MINUS).replaceAll(LEFTBRACKET, MINUS).replaceAll(RIGHTBRACKET, MINUS);
//            Class<?> returnType = methodSignature.getReturnType();
        System.out.println("====aroundRedisGet====key===" + key);

        Object value = xRedisTemplate.getAopObject(key, expireSeconds);
        if (value == null) {
            value = point.proceed();
            xRedisTemplate.setAopObject(key, value, expireSeconds);
        }
        return value;
    }

    public void afterReturnRedisRemove(BaseModel arg) {
        MappingModel<? extends BaseModel> mappingModel = ModelMap.getMappingModel(arg.getClass());
        // 删除前缀的所有集合
        System.out.println("====afterReturnRedisRemove====================className===" + arg.getClass());
        Map<String, String> tableNameMap = mappingModel.getTableModelMap();
        for (String tableName : tableNameMap.keySet()) {
            System.out.println("====afterReturnRedisRemove====================tableName===" + tableName);
            xRedisTemplate.deleteAopObject(tableName);
        }
    }

    public void beforeLog(JoinPoint point) {
        String methodName = point.getSignature().getName();
        Object[] args = point.getArgs();
        System.out.println(methodName + args);
    }

    public void aroundQuartz(ProceedingJoinPoint point) {
//		String key = null;
//		try {
//			Signature signature = point.getSignature();
//			key = signature.getName();
//			Thread.sleep(random.nextInt(10000));
//			String lock = aopRedis.get(key, String.class, expireSeconds);
//			if (lock == null || lock.equals(LOCK_FALSE)) {
//                aopRedis.set(key, LOCK_TRUE);
//				point.proceed();
//                aopRedis.set(key, LOCK_FALSE);
//			}
//		} catch (Throwable e) {
//			logger.error("aroundQuartz exception : ", e);
//            aopRedis.set(key, LOCK_FALSE);
//		}
    }

    // public void afterThrowingLog(JoinPoint point, Exception e) {
    // if(e instanceof BusinessException) {
    //
    // }
    // Signature signature = point.getSignature();
    // MethodSignature methodSignature = (MethodSignature) signature;
    // Method method = methodSignature.getMethod();
    // Annotation[] methodAnnotations = method.getDeclaredAnnotations();
    // String message = null;
    // for (Annotation annotation : methodAnnotations) {
    // if (annotation.annotationType().equals(DefaultExceptionCode.class)) {
    // message = ((DefaultExceptionCode) annotation).value();
    // break;
    // }
    // }
    // String methodName = method.getName();
    // Object[] args = point.getArgs();
    // logger.error(methodName + e.getMessage(), args);
    // if(message == null) {
    // message = e.getMessage();
    // }
    // message = message == null ? e.getMessage() : message;
    // throw new BusinessException(e.getMessage(), e);
    // }

    public void afterReturnXxx(BaseModel arg, List<BaseModel> list) {
        MappingTable mappingTable = arg.getClass().getAnnotation(MappingTable.class);
        String tableName = mappingTable.tableName().toUpperCase();
        System.out.println("====afterReturnRedisSave====================tableName===" + tableName);
        System.out.println("====afterReturnRedisSave====================list===" + list.size());
        // logger.info("====RedisUtil=================redisSave===point.getSignature().getName()==="
        // + point.getSignature().getName());
    }

    public static void main(String[] arg) {
        System.out.println(LEFTBRACKET);
        System.out.println(LEFTRIGHTBRACKETS);
        System.out.println(RIGHTBRACKET);
        System.out.println(RIGHTLEFTBRACKETS);
    }

}
