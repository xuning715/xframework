package com.x.framework.controller;

import com.x.framework.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class BaseController extends Base {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    @ResponseBody
    public ResultModel exception(Throwable e) {
        logger.error(e.getMessage(), e);
        return new ResultModel(ResultCode.RESULT_ERROR, e.getMessage());
    }

}
