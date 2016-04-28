package com.djk.pic.utils;

import org.apache.log4j.Logger;

import java.util.function.Supplier;

/**
 * 日志工具
 *
 * @author dujinkai
 */
public final class LogUtils {

    private LogUtils() {

    }

    /**
     * 记录info级别日志
     *
     * @param logger   日志对象
     * @param supplier 日志信息
     */
    public static void info(Logger logger, Supplier<String> supplier, Throwable t) {
        if (null != logger && logger.isInfoEnabled()) {
            logger.info(supplier.get(), t);
        }
    }

    /**
     * 记录info级别日志
     *
     * @param logger   日志对象
     * @param supplier 日志信息
     */
    public static void info(Logger logger, Supplier<String> supplier) {
        if (null != logger && logger.isInfoEnabled()) {
            logger.info(supplier.get());
        }
    }

    /**
     * 记录debug级别日志
     *
     * @param logger   日志对象
     * @param supplier 日志信息
     */
    public static void debug(Logger logger, Supplier<String> supplier, Throwable t) {
        if (null != logger && logger.isDebugEnabled()) {
            logger.debug(supplier.get(), t);
        }
    }

    /**
     * 记录debug级别日志
     *
     * @param logger   日志对象
     * @param supplier 日志信息
     */
    public static void debug(Logger logger, Supplier<String> supplier) {
        if (null != logger && logger.isDebugEnabled()) {
            logger.debug(supplier.get());
        }
    }

    /**
     * 记录warn级别日志
     *
     * @param logger   日志对象
     * @param supplier 日志信息
     */
    public static void warn(Logger logger, Supplier<String> supplier, Throwable t) {
        if (null != logger) {
            logger.warn(supplier.get(), t);
        }
    }

    /**
     * 记录warn级别日志
     *
     * @param logger   日志对象
     * @param supplier 日志信息
     */
    public static void warn(Logger logger, Supplier<String> supplier) {
        if (null != logger) {
            logger.warn(supplier.get());
        }
    }

    /**
     * 记录error级别日志
     *
     * @param logger   日志对象
     * @param supplier 日志信息
     */
    public static void error(Logger logger, Supplier<String> supplier) {
        if (null != logger) {
            logger.error(supplier.get());
        }
    }


    /**
     * 记录error级别日志
     *
     * @param logger   日志对象
     * @param supplier 日志信息
     */
    public static void error(Logger logger, Supplier<String> supplier, Throwable t) {
        if (null != logger) {
            logger.error(supplier.get(), t);
        }
    }

}
