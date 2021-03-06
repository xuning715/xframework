package com.x.framework.bootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 作为liunx守护进程入口
 */
public class Launcher extends BaseLauncher {

    private static final Logger logger = LoggerFactory.getLogger(Launcher.class);

    public static void main(String[] args) throws Exception {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("exit");
        }));

        Launcher launcher = new Launcher();
        launcher.start();
        synchronized (Launcher.class){
            Launcher.class.wait();
        }
    }


    /**
     * 启动服务
     *
     * @throws Exception
     */
    @Override
    public void start() throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        context.start();
        logger.info("services started");
    }
}
