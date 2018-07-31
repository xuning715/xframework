package com.x.framework.bootstrap;

import org.apache.commons.daemon.Daemon;
import org.apache.commons.daemon.DaemonContext;
import org.apache.commons.daemon.DaemonInitException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseLauncher implements Daemon {
    private static final Logger logger = LoggerFactory.getLogger(BaseLauncher.class);

    public BaseLauncher() {
    }

    @Override
    public void init(DaemonContext daemonContext) throws DaemonInitException, Exception {
        logger.info("init");
    }

    public abstract void start() throws Exception;

    @Override
    public void stop() throws Exception {
        logger.info("stop");
    }

    @Override
    public void destroy() {
        logger.info("destroy");
    }
}
