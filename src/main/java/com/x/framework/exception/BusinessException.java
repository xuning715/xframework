package com.x.framework.exception;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.apache.log4j.Logger;
import org.apache.log4j.LogManager;

public class BusinessException extends RuntimeException {
	private static final long serialVersionUID = 1L;
//	private static Logger logger = LoggerFactory.getLogger(BusinessException.class);
    private static final Logger logger = LogManager.getLogger(BusinessException.class);

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Throwable e) {
        super(e);
        logger.error(null, e);
    }

    public BusinessException(String message, Throwable e) {
        super(message);
        logger.error(message, e);
    }
}
