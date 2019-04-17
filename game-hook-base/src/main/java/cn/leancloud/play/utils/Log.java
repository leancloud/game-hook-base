package cn.leancloud.play.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Log {
    private static final Logger logger = LoggerFactory.getLogger("GameHookLog");

    public static boolean isTraceEnabled() {
        return logger.isTraceEnabled();
    }

    public static void trace(String var1) {
        logger.trace(var1);
    }

    public static void trace(String var1, Object var2) {
        logger.trace(var1, var2);
    }

    public static void trace(String var1, Object var2, Object var3) {
        logger.trace(var1, var2, var3);
    }

    public static void trace(String var1, Object... var2) {
        logger.trace(var1, var2);
    }

    public static void trace(String var1, Throwable var2) {
        logger.trace(var1, var2);
    }

    public static boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    public static void debug(String var1) {
        logger.debug(var1);
    }

    public static void debug(String var1, Object var2){
        logger.debug(var1, var2);
    }

    public static void debug(String var1, Object var2, Object var3) {
        logger.debug(var1, var2, var3);
    }

    public static void debug(String var1, Object... var2) {
        logger.debug(var1, var2);
    }

    public static void debug(String var1, Throwable var2) {
        logger.debug(var1, var2);
    }

    public static boolean isInfoEnabled() {
        return logger.isInfoEnabled();
    }

    public static void info(String var1) {
        logger.info(var1);
    }

    public static void info(String var1, Object var2) {
        logger.info(var1, var2);
    }

    public static void info(String var1, Object var2, Object var3) {
        logger.info(var1, var2, var3);
    }

    public static void info(String var1, Object... var2) {
        logger.info(var1, var2);
    }

    public static void info(String var1, Throwable var2) {
        logger.info(var1, var2);
    }

    public static boolean isWarnEnabled() {
        return logger.isWarnEnabled();
    }

    public static void warn(String var1) {
        logger.warn(var1);
    }

    public static void warn(String var1, Object var2) {
        logger.warn(var1, var2);
    }

    public static void warn(String var1, Object... var2) {
        logger.warn(var1, var2);
    }

    public static void warn(String var1, Object var2, Object var3) {
        logger.warn(var1, var2, var3);
    }

    public static void warn(String var1, Throwable var2) {
        logger.warn(var1, var2);
    }

    public static boolean isErrorEnabled() {
        return logger.isErrorEnabled();
    }

    public static void error(String var1) {
        logger.error(var1);
    }

    public static void error(String var1, Object var2) {
        logger.error(var1, var2);
    }

    public static void error(String var1, Object var2, Object var3) {
        logger.error(var1, var2, var3);
    }

    public static void error(String var1, Object... var2) {
        logger.error(var1, var2);
    }

    public static void error(String var1, Throwable var2) {
        logger.error(var1, var2);
    }
}
