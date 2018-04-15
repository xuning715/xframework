package com.x.framework.remote;

import com.x.framework.exception.BusinessException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * import org.apache.http.Consts;
 * import org.apache.http.HttpEntity;
 * import org.apache.http.NameValuePair;
 * import org.apache.http.client.entity.UrlEncodedFormEntity;
 * import org.apache.http.client.methods.CloseableHttpResponse;
 * import org.apache.http.client.methods.HttpGet;
 * import org.apache.http.client.methods.HttpPost;
 * import org.apache.http.client.utils.URIBuilder;
 * import org.apache.http.entity.ContentType;
 * import org.apache.http.entity.StringEntity;
 * import org.apache.http.entity.mime.MultipartEntityBuilder;
 * import org.apache.http.entity.mime.content.FileBody;
 * import org.apache.http.entity.mime.content.StringBody;
 * import org.apache.http.impl.client.CloseableHttpClient;
 * import org.apache.http.impl.client.HttpClients;
 * import org.apache.http.message.BasicNameValuePair;
 * import org.apache.http.util.CharsetUtils;
 * import org.apache.http.util.EntityUtils;
 **/
public class HttpClient {
    /**
     * public static String doGet(String url, Map<String, String> param) throws Exception {
     * // 创建Httpclient对象
     * CloseableHttpClient httpclient = HttpClients.createDefault();
     * <p>
     * String resultString = "";
     * CloseableHttpResponse response = null;
     * try {
     * // 创建uri
     * URIBuilder builder = new URIBuilder(url);
     * if (param != null) {
     * for (String key : param.keySet()) {
     * builder.addParameter(key, param.get(key));
     * }
     * }
     * URI uri = builder.build();
     * <p>
     * // 创建http GET请求
     * HttpGet httpGet = new HttpGet(uri);
     * <p>
     * // 执行请求
     * response = httpclient.execute(httpGet);
     * // 判断返回状态是否为200
     * if (response.getStatusLine().getStatusCode() == 200) {
     * resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
     * }
     * } finally {
     * if (response != null) {
     * response.close();
     * }
     * httpclient.close();
     * return resultString;
     * }
     * }
     * <p>
     * public static String doGet(String url) throws Exception {
     * return doGet(url, null);
     * }
     * <p>
     * public static String doPost(String url, Map<String, String> param) throws Exception {
     * // 创建Httpclient对象
     * CloseableHttpClient httpClient = HttpClients.createDefault();
     * CloseableHttpResponse response = null;
     * String resultString = "";
     * try {
     * // 创建Http Post请求
     * HttpPost httpPost = new HttpPost(url);
     * // 创建参数列表
     * if (param != null) {
     * List<NameValuePair> paramList = new ArrayList<NameValuePair>();
     * for (String key : param.keySet()) {
     * paramList.add(new BasicNameValuePair(key, param.get(key)));
     * }
     * // 模拟表单
     * UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, "UTF-8");
     * httpPost.setEntity(entity);
     * }
     * // 执行http请求
     * response = httpClient.execute(httpPost);
     * resultString = EntityUtils.toString(response.getEntity(), "utf-8");
     * } finally {
     * response.close();
     * return resultString;
     * }
     * }
     * <p>
     * public static String doPost(String url) throws Exception {
     * return doPost(url, null);
     * }
     * <p>
     * public static String doPostJson(String url, String json) throws Exception {
     * CloseableHttpResponse closeableHttpResponse = null;
     * CloseableHttpClient httpClient = HttpClients.createDefault();
     * try {
     * // 创建Httpclient对象
     * // 创建Http Post请求
     * HttpPost httpPost = new HttpPost(url);
     * // 创建请求内容
     * StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
     * httpPost.setEntity(entity);
     * // 执行http请求
     * closeableHttpResponse = httpClient.execute(httpPost);
     * return EntityUtils.toString(closeableHttpResponse.getEntity(), "utf-8");
     * } finally {
     * if (closeableHttpResponse != null) {
     * closeableHttpResponse.close();
     * }
     * httpClient.close();
     * }
     * }
     * <p>
     * public static String uploadFile(File file, String url, Map<String, String> params) throws Exception {
     * CloseableHttpClient httpClient = HttpClients.createDefault();
     * <p>
     * String resultString = "";
     * CloseableHttpResponse response = null;
     * try {
     * HttpPost httpPost = new HttpPost(url);
     * MultipartEntityBuilder builder = MultipartEntityBuilder.create();
     * <p>
     * builder.addPart("file", new FileBody(file));
     * // StringBody
     * for (Entry<String, String> en : params.entrySet()) {
     * builder.addPart(en.getKey(), new StringBody(en.getValue(), ContentType.create("text/plain", Consts.UTF_8)));
     * }
     * <p>
     * HttpEntity reqEntity = builder.setCharset(CharsetUtils.get("UTF-8")).build();
     * <p>
     * httpPost.setEntity(reqEntity);
     * <p>
     * // 发起请求 并返回请求的响应
     * response = httpClient.execute(httpPost);
     * <p>
     * if (response.getStatusLine().getStatusCode() == 200) {
     * resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
     * }
     * } finally {
     * if (response != null) {
     * response.close();
     * }
     * httpClient.close();
     * return resultString;
     * }
     * }
     **/
    private static final int TIMEOUT_IN_MILLIONS = 300000;//30秒

    public static final String DEFAULT_CHARSET = "utf-8";

    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    public void setThreadPoolTaskExecutor(ThreadPoolTaskExecutor executor) {
        this.threadPoolTaskExecutor = executor;
    }


    public interface CallBack {
        void onRequestComplete(String result);
    }

    /**
     * 异步的Get请求
     *
     * @param urlStr
     * @param callBack
     */
    public void doGetAsyn(final String urlStr, final CallBack callBack) {
        threadPoolTaskExecutor.execute(new Runnable() {
                                           @Override
                                           public void run() {
                                               String result = null;
                                               try {
                                                   result = doGet(urlStr);
                                                   if (callBack != null) {
                                                       callBack.onRequestComplete(result);
                                                   }
                                               } catch (Exception e) {
                                                   throw new BusinessException(e);
                                               }
                                           }
                                       }
        );
    }

    /**
     * 异步的Post请求
     *
     * @param urlStr
     * @param paramMap
     * @param callBack
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    public void doPostAsyn(final String urlStr, final Map paramMap, final CallBack callBack) {
        threadPoolTaskExecutor.execute(new Runnable() {
                                           @Override
                                           public void run() {
                                               try {
                                                   String result = doPostMap(urlStr, paramMap);
                                                   if (callBack != null) {
                                                       callBack.onRequestComplete(result);
                                                   }
                                               } catch (Exception e) {
                                                   throw new BusinessException(e);
                                               }
                                           }
                                       }
        );
    }

    /**
     * Get请求，获得返回数据
     *
     * @param urlStr
     * @return
     * @throws Exception
     */
    public String doGet(String urlStr) throws Exception {
        URL url = null;
        HttpURLConnection conn = null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        String result = null;
        try {
            url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
            conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            if (conn.getResponseCode() == 200) {
                is = conn.getInputStream();
                baos = new ByteArrayOutputStream();
                int len = -1;
                byte[] buf = new byte[128];
                while ((len = is.read(buf)) != -1) {
                    baos.write(buf, 0, len);
                }
                baos.flush();
                result = baos.toString();
            } else {
                throw new RuntimeException(" responseCode is not 200 ... ");
            }
        } finally {
            if (baos != null) {
                baos.close();
            }
            if (is != null) {
                is.close();
            }
            conn.disconnect();
            return result;
        }
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url      发送请求的 URL
     * @param paramMap 请求参数，请求参数应该是key-value 的形式。
     * @return 所代表远程资源的响应结果
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    public String doPostMap(String url, Map paramMap) throws Exception {
        String params = mapToQueryString(paramMap, DEFAULT_CHARSET);
        return doPost(url, params, "application/x-www-form-urlencoded");
    }

    /**
     * MethodName: doPostJson
     * description: 向指定 URL 发送POST方法的请求
     * Date: 2016年4月14日 下午4:59:21
     *
     * @param url
     * @param json 转JSON后的数据
     * @return
     * @author yangyonghao
     */
    public String doPostJson(String url, String json) throws Exception {
        return doPost(url, json, "application/json");
    }

    public String doPostXml(String url, String xml) throws Exception {
        return doPost(url, xml, "text/xml");
    }

    public String doPost(String url, String params, String contentType) throws Exception {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", contentType);
            conn.setRequestProperty("charset", "utf-8");
            conn.setUseCaches(false);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
            conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);
            // 参数转换
            if (params != null && params.trim().length() > 0) {
                // 获取URLConnection对象对应的输出流
                out = new PrintWriter(conn.getOutputStream());
                // 发送请求参数
                out.print(params);
                // flush输出流的缓冲
                out.flush();
            }
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
            return result;
        }
    }

    /**
     * map参数转String
     *
     * @param paramMap
     * @param charSet
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public String mapToQueryString(Map paramMap, String charSet) throws Exception {
        String queryString = "";
        if (paramMap != null && !paramMap.isEmpty()) {
            Set<Entry> entrySet = paramMap.entrySet();
            for (Entry entry : entrySet) {
                String key = entry.getKey().toString();
                Object value = entry.getValue();
                List values = makeStringList(value);
                for (Object v : values) {
                    if (charSet != null) {
                        queryString += key
                                + "="
                                + URLEncoder.encode(v == null ? "" : v.toString(), charSet)
                                + "&";
                    } else {
                        queryString += key
                                + "="
                                + (v == null ? "" : v.toString())
                                + "&";
                    }

                }
            }
            if (queryString.length() > 0) {
                queryString = queryString
                        .substring(0, queryString.length() - 1);
            }
        }
        return queryString;
    }

    /**
     * String参数转map
     *
     * @param queryString
     * @param charSet
     * @return
     */
    @SuppressWarnings("rawtypes")
    public Map queryStringToMap(String queryString, String charSet) throws Exception {
        if (queryString == null) {
            throw new IllegalArgumentException("queryString must be specified");
        }
        int index = queryString.indexOf("?");
        if (index > 0) {
            queryString = queryString.substring(index + 1);
        }
        String[] keyValuePairs = queryString.split("&");
        Map<String, String> map = new HashMap<String, String>();
        for (String keyValue : keyValuePairs) {
            if (keyValue.indexOf("=") == -1) {
                continue;
            }
            String[] args = keyValue.split("=");
            if (args.length == 2) {
                if (charSet != null) {
                    map.put(args[0], URLDecoder.decode(args[1], charSet));
                } else {
                    map.put(args[0], args[1]);
                }
            }
            if (args.length == 1) {
                map.put(args[0], "");
            }
        }
        return map;
    }

    /**
     * 对象转字符串集合
     *
     * @param value
     * @return
     */
    @SuppressWarnings("rawtypes")
    private List<String> makeStringList(Object value) {
        if (value == null) {
            value = "";
        }
        List<String> result = new ArrayList<String>();
        if (value.getClass().isArray()) {
            for (int j = 0; j < Array.getLength(value); j++) {
                Object obj = Array.get(value, j);
                result.add(obj != null ? obj.toString() : "");
            }
            return result;
        }

        if (value instanceof Iterator) {
            Iterator it = (Iterator) value;
            while (it.hasNext()) {
                Object obj = it.next();
                result.add(obj != null ? obj.toString() : "");
            }
            return result;
        }

        if (value instanceof Collection) {
            for (Object obj : (Collection) value) {
                result.add(obj != null ? obj.toString() : "");
            }
            return result;
        }

        if (value instanceof Enumeration) {
            Enumeration enumeration = (Enumeration) value;
            while (enumeration.hasMoreElements()) {
                Object obj = enumeration.nextElement();
                result.add(obj != null ? obj.toString() : "");
            }
            return result;
        }
        result.add(value.toString());
        return result;
    }

}
