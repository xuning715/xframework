package com.x.framework.remote;

public interface BaseRemoteService {
    /**
     *手动开始一个事务
     */
    public String beginTransaction();

    /**
     *手动提交一个事务
     */
    public void commitTransaction(String key);

    /**
     *手动回滚一个事务
     */
    public void rollbackTransaction(String key);

}
