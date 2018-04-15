package com.x.framework.service;

import com.x.framework.Base;
import com.x.framework.dao.XJdbcTemplate;
import com.x.framework.model.BaseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class BaseService extends Base {

    private XJdbcTemplate xJdbcTemplate;

    public BaseService() {
    }

    @Autowired
    public void setXJdbcTemplate(XJdbcTemplate xJdbcTemplate) {
        this.xJdbcTemplate = xJdbcTemplate;
    }

    public <T extends BaseObject> T insert(T model) throws Exception {
        return this.xJdbcTemplate.insert(model);
    }

    public <T extends BaseObject> int update(T model) {
        return  this.xJdbcTemplate.update(model);
    }

    public <T extends BaseObject> int update(T modelSet, T modelWhere) {
        return this.xJdbcTemplate.update(modelSet, modelWhere);
    }

    public <T extends BaseObject> int delete(T model) {
        return  this.xJdbcTemplate.delete(model);
    }

}
