package com.x.framework.model;

import java.io.Serializable;

public class BaseObject implements Serializable {
    
    private Integer pageIndex;
    
    private Integer pageSize;

    public BaseObject() {

    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

}
