package com.x.framework;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.Random;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.io.FileTransfer;

import java.text.ParseException;

public abstract class Base {
    private final static String SPACE = "　| ";
    public final static String BLANK = "";
    private final static String DOT = ".";
    private final static String YYMMDDHHMMSS = "yyMMddHHmmss";

    public Base() {
    }

    /**
     * 判断字符串是否为空
     *
     * @param str String
     * @return boolean
     */
    public static boolean isString(String str) {
        if (str != null && str.replaceAll(SPACE, BLANK).length() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取文件后缀名（不带点）
     * @return 如："jpg" or ""
     */
    public static String getSuffix(String fileName) {
        if (StringUtils.isEmpty(fileName) || !fileName.contains(DOT)) {
            return BLANK;
        } else {
            return fileName.substring(fileName.lastIndexOf(DOT) + 1).toLowerCase(); // 不带最后的点
        }
    }

    public static boolean isNotNull(Object obj) {
        if (obj != null) {
            if (obj.toString().length() > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 返回去空格的String
     *
     * @param str String
     * @return String
     */
    public static String replaceBlank(String str) {
        if (str != null) {
            str = str.replaceAll(SPACE, BLANK);
        }
        return str;
    }

    /**
     * 返回唯一id
     *
     * @return String
     */
    public static String getUid(int length) {
        if (length >= 12) {
            length = length - 12;
            StringBuffer stringBuffer = new StringBuffer(getCurrentDate(0, YYMMDDHHMMSS));
            Random random = new Random();
            for (int i = 0; i < length; i++) {
                stringBuffer.append(random.nextInt(10));
            }
            return stringBuffer.toString();
        } else {
            return getRandom(length);
        }
    }

    private static String getRandom(int length) {
        StringBuffer stringBuffer = new StringBuffer(BLANK);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            stringBuffer.append(random.nextInt(10));
        }
        return stringBuffer.toString();
    }

    /**
     * 返回指定位数的小数
     *
     * @param value      Double
     * @param formatType String
     * @return double
     */
    public static double formatDouble(Double value, String formatType) {
        DecimalFormat format = new DecimalFormat(formatType);
        return Double.parseDouble(format.format(value));
    }

    /**
     * 返回指定位数的小数
     *
     * @param value      Float
     * @param formatType String
     * @return float
     */
    public static float formatFloat(Float value, String formatType) {
        DecimalFormat format = new DecimalFormat(formatType);
        return Float.parseFloat(format.format(value));
    }

    /**
     * 根据字符串和日期格式返回Date日期
     *
     * @param str     String
     * @param pattern String
     * @return Date
     */
    public static Date getDate(String str, String pattern) {
        Date date = null;
        if (isString(str)) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                date = sdf.parse(str);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return date;
    }


    /**
     * 根据字符串和日期格式返回Date日期
     *
     * @param date    Date
     * @param pattern String
     * @return Date
     */
    public static String getDate(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.format(date);
        } else {
            return BLANK;
        }
    }

    /**
     * 返回周几
     *
     * @return int
     */
    public static int getDayOfWeek() {
        Calendar c = Calendar.getInstance();
        Date date = new Date();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 根据格式返回日期字符串，increment为0时是当前日期
     *
     * @param increment int
     * @param pattern   String
     * @return String
     */
    public static String getCurrentDate(int increment, String pattern) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, increment);
        Date date = c.getTime();
        DateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }

    /**
     * 根据格式返回日期字符串，increment为0时是当前日期
     *
     * @param increment int
     * @return String
     */
    public static Date getDate(int increment) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, increment);
        Date date = c.getTime();
        return date;
    }

    public static Date getDate(Date date, int increment) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, increment);
        return c.getTime();
    }

    /**
     * 返回当前小时数
     *
     * @return int
     */
    public static int getCurrentHour() {
        Calendar c = Calendar.getInstance();
        Date date = new Date();
        c.setTime(date);
        return c.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 上传文件
     *
     * @param uploadFile InputStream 类型为file的input域
     * @param fileUrl    String 上传路劲
     * @param fileName   String 文件名
     * @return String
     */
    public String uploadFile(FileTransfer uploadFile, String fileUrl, String fileName) throws Exception {
        if (uploadFile != null && uploadFile.getSize() > 0) {
            fileUrl = getRealPath(fileUrl);
            File dir = new File(fileUrl);
            if (!dir.exists() || !dir.isDirectory()) {
                dir.mkdirs();
            }
            File file = new File(dir, fileName);
            InputStream is = uploadFile.getInputStream();
            int available = is.available();
            byte[] b = new byte[available];
            FileOutputStream fos = new FileOutputStream(file);
            is.read(b);
            fos.write(b);
            fos.flush();
            fos.close();
            is.close();
            fos = null;
            is = null;
            uploadFile = null;
            return fileUrl + "/" + fileName;
        } else {
            return null;
        }
    }

    /**
     * 返回 web root 下的某个目录
     *
     * @param dir String
     * @return String
     */
    public String getRealPath(String dir) {
        return WebContextFactory.get().getServletContext().getRealPath(dir);
    }
    /**
     * 根据目录名地址删除目录及其所有文件
     *
     * @param str String
     */
    public void delDir(String str) {
        str = getRealPath(str);
        File dir = new File(str);
        if (dir != null && dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.exists() && file.isFile()) {
                        file.delete();
                    }
                }
            }
            dir.delete();
        }
    }

    /**
     * 根据目录和文件名删除文件
     *
     * @param dir      String
     * @param fileName String
     */
    public void deleteFile(String dir, String fileName) {
        dir = getRealPath(dir);
        File file = new File(dir, fileName);
        if (file.exists() && file.isFile()) {
            file.delete();
        }
    }

    /**
     * 通过反射获取静态变量值
     *
     * @param clazz     Class
     * @param fieldName String
     * @return Object
     * @throws Exception
     */
    public Object getStaticProperty(Class clazz, String fieldName) throws Exception {
        Field field = clazz.getField(fieldName);
        Object property = field.get(clazz);
        return property;
    }

    /**
     * 把map映射成T型的model
     *
     * @param map   Map
     * @param clazz Class
     * @return T
     */
    public <T extends Object> T mapToModel(Map<String, Object> map, Class<T> clazz) {
        T model = null;
        try {
            model = clazz.newInstance();
            if (model != null) {
                BeanUtils.populate(model, map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }

}
