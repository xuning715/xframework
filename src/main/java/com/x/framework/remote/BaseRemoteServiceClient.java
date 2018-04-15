package com.x.framework.remote;

import com.x.framework.exception.BusinessException;

//import org.codehaus.xfire.service.binding.ObjectServiceFactory;
//import org.codehaus.xfire.client.XFireProxyFactory;
//import org.codehaus.xfire.service.Service;

public class BaseRemoteServiceClient<T extends BaseRemoteService> {
    //远程服务地址
//    public static String url;
//    private final static String wsdl = "?wsdl";

    //hessian工厂
//    private static HessianProxyFactory hessianFactory;

    //xfire工厂
//    private static ObjectServiceFactory objectServiceFactory;
//    private static XFireProxyFactory xfireProxyFactory;

    //远程服务接口门面
//    private T webService;

//    private Class webServiceClass;

//    private static ThreadLocal<String> threadLocal = new ThreadLocal<String>();

//    public String getUrl() {
//        return url;
//    }
    /**
     * 调用webservcie
     * http://192.168.191.17:8026/icme_webservice/service/WebService?wsdl
     * @param serviceUrl String    http://192.168.191.17:8026/icme_webservice/service/
     * @param serviceName String   WebService
     * @param methodName String    queryStudentList
     * @param params Object[]
     * @return Object[]
     * @throws BusinessException
     * @throws Exception
     */
//    public Object[] invokeWebService(String serviceUrl, String serviceName, String methodName, Object[] params) throws BusinessException {
//        try {
//            URL url = new URL(serviceUrl + serviceName + wsdl);
//            Client client = new Client(url);
//            Object[] result = client.invoke(methodName, params);
//            return result;
//        } catch (Throwable e) {
//            throw new BusinessException(ExceptionCode.EXCEPTION_CODE0001, e);
//        }
//    }

//    public void setUrl(String url) {
//        this.url = url;
//        this.webServiceClass = webService.getClass();
//        this.webService = this.getHessianWebService(this.webServiceClass);
//    }

//    public T getWebService() {
//        return webService;
//    }

    /**
     * 初始化Hessian服务
     */
//    private T getHessianWebService(Class<T> webServiceClass) {
//        try {
//            logger.info("远程Hessian服务初始化");
//            hessianFactory = new HessianProxyFactory();
//            return (T) hessianFactory.create(webServiceClass, this.url + "/remote/" + webServiceClass.getSimpleName());
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException("远程Hessian服务初始化失败");
//        }
//    }

    /**
     * xfire服务工厂客户端初始化
     */
//    private T getXfireWebService(Class<T> webServiceClass) {
//        try {
//            logger.info("远程Xfire服务初始化");
//            objectServiceFactory = new ObjectServiceFactory();
//            xfireProxyFactory = new XFireProxyFactory();
//            Service serviceModel = objectServiceFactory.create(webServiceClass);
//            return (T) xfireProxyFactory.create(serviceModel, this.url + "/service/" + webServiceClass.getSimpleName());
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException("远程Xfire服务初始化失败");
//        }
//    }


    /**
     * 返回线程中的key值，为空的话返回""
     * @return String
     */
//    private static String getKey() {
//        return threadLocal.get() == null ? "" : threadLocal.get();
//    }

    /**
     *手动开始一个事务
     */
//    public String beginTransaction() {
//        logger.info("=====transaction is begin=====");
//        String key = webService.beginTransaction();
//        threadLocal.set(key);
//        return key;
//    }

    /**
     *手动提交一个事务
     */
//    public void commitTransaction() {
//        logger.info("=====transaction is commit=====");
//        webService.commitTransaction(getKey());
//        threadLocal.remove();
//    }

    /**
     *手动回滚一个事务
     */
//    public void rollbackTransaction() {
//        logger.info("=====transaction is rollback=====");
//        webService.rollbackTransaction(getKey());
//        threadLocal.remove();
//    }


    /**
     * 调用hessian服务例子
     */
//    static {
//       TestWebService testWebService = this.getHessianWebService(TestWebService.class);
//    }
//    public static int selectTest(String i) throws Exception {
//        return testWebService.selectTest(getKey(), i);
//    }

    /**
     * 调用xfire服务例子
     */
//    static {
//        TestWebService testWebService = this.getXfireWebService(TestWebService.class);
//    }
//    public static int selectTest(String i) throws Exception {
//        return testWebService.selectTest(getKey(), i);
//    }

}
