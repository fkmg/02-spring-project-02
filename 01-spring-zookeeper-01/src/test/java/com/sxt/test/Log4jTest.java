package com.sxt.test;

import org.apache.log4j.*;
import org.junit.Test;

import java.io.IOException;

public class Log4jTest {

    @Test
    public void testLog4j(){
        //获取Logger对象的实例
        Logger logger = Logger.getLogger(Log4jTest.class);
        //使用默认的配置信息，不需要写log4j.properties
        //BasicConfigurator.configure();
        //设置日志输出级别为WARN，这将覆盖配置文件中设置的级别，只有日志级别高于WARN的日志才输出
        //logger.setLevel(Level.ERROR);
        logger.debug("这是debug");
        logger.info("这是info");
        logger.warn("这是warn");
        logger.error("这是error");
        logger.fatal("这是fatal");

    }

    @Test
    public void testLog4jHTML(){
        //获取Logger对象的实例
        Logger logger = Logger.getLogger(Log4jTest.class);
        //使用默认的配置信息，不需要写log4j.properties
        BasicConfigurator.configure();
        //HTML格式化
        HTMLLayout layout = new HTMLLayout();

        try {
            FileAppender appender  = new FileAppender(layout,"G:\\html\\test\\log4jtest\\log4j.html",false);
            logger.addAppender(appender);
            //设置日志输出级别为WARN，这将覆盖配置文件中设置的级别，只有日志级别高于WARN的日志才输出
            logger.setLevel(Level.WARN);
            logger.debug("这是debug");
            logger.info("这是info");
            logger.warn("这是warn");
            logger.error("这是error");
            logger.fatal("这是fatal");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testLog4jSimpleLayout(){
        //获取Logger对象的实例
        Logger logger = Logger.getLogger(Log4jTest.class);
        //使用默认的配置信息，不需要写log4j.properties
        BasicConfigurator.configure();
        //SimpleLayout
        SimpleLayout layout = new SimpleLayout();

        try {
            FileAppender appender  = new FileAppender(layout,"G:\\html\\test\\log4jtest\\log4j01.txt",false);
            logger.addAppender(appender);
            //设置日志输出级别为WARN，这将覆盖配置文件中设置的级别，只有日志级别高于WARN的日志才输出
            logger.setLevel(Level.WARN);
            logger.debug("这是debug");
            logger.info("这是info");
            logger.warn("这是warn");
            logger.error("这是error");
            logger.fatal("这是fatal");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testLog4jPatternLayout(){
        //获取Logger对象的实例
        Logger logger = Logger.getLogger(Log4jTest.class);
        //使用默认的配置信息，不需要写log4j.properties
        BasicConfigurator.configure();
        //PatternLayout
        PatternLayout layout = new PatternLayout(PatternLayout.TTCC_CONVERSION_PATTERN);

        try {
            FileAppender appender  = new FileAppender(layout,"G:\\html\\test\\log4jtest\\log4j02.txt",false);
            logger.addAppender(appender);
            //设置日志输出级别为WARN，这将覆盖配置文件中设置的级别，只有日志级别高于WARN的日志才输出
            logger.setLevel(Level.WARN);
            logger.debug("这是debug");
            logger.info("这是info");
            logger.warn("这是warn");
            logger.error("这是error");
            logger.fatal("这是fatal");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
