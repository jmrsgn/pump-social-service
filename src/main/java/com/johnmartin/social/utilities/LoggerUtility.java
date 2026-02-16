package com.johnmartin.social.utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class LoggerUtility {

    private LoggerUtility() {
        // prevent instantiation
    }

    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    public static void i(Class<?> clazz, String message, Object... args) {
        getLogger(clazz).info(message, args);
    }

    public static void d(Class<?> clazz, String message, Object... args) {
        getLogger(clazz).debug(message, args);
    }

    public static void w(Class<?> clazz, String message, Object... args) {
        getLogger(clazz).warn(message, args);
    }

    public static void e(Class<?> clazz, String message, Throwable throwable) {
        getLogger(clazz).error(message, throwable);
    }

    public static void t(Class<?> clazz, String message, Object... args) {
        getLogger(clazz).trace(message, args);
    }
}
