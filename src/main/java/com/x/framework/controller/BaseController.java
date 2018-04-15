package com.x.framework.controller;

import com.x.framework.Base;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class BaseController extends Base {

    public Logger logger = LogManager.getLogger(this.getClass());

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    @ResponseBody
    public ResultModel exception(Throwable e) {
        logger.error(e.getMessage(), e);
        return new ResultModel(ResultCode.RESULT_ERROR, e.getMessage());
    }

}
