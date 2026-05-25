package com.physmo.minvio.utils;

/**
 * A simple internal logger for Minvio that can be configured or silenced.
 */
public class MinvioLogger {

    public enum LogLevel {
        DEBUG,
        INFO,
        WARN,
        ERROR,
        NONE
    }

    private static LogLevel currentLevel = LogLevel.INFO;

    /**
     * Sets the global log level.
     *
     * @param level The new log level.
     */
    public static void setLogLevel(LogLevel level) {
        currentLevel = level;
    }

    /**
     * Logs a message at the INFO level.
     *
     * @param message The message to log.
     */
    public static void info(String message) {
        log(LogLevel.INFO, message);
    }

    /**
     * Logs a message at the DEBUG level.
     *
     * @param message The message to log.
     */
    public static void debug(String message) {
        log(LogLevel.DEBUG, message);
    }

    /**
     * Logs a message at the WARN level.
     *
     * @param message The message to log.
     */
    public static void warn(String message) {
        log(LogLevel.WARN, message);
    }

    /**
     * Logs a message at the ERROR level.
     *
     * @param message The message to log.
     */
    public static void error(String message) {
        log(LogLevel.ERROR, message);
    }

    /**
     * Internal log method that checks the current log level.
     *
     * @param level   The level of this message.
     * @param message The message to log.
     */
    private static void log(LogLevel level, String message) {
        if (currentLevel == LogLevel.NONE) return;
        if (level.ordinal() >= currentLevel.ordinal()) {
            if (level == LogLevel.ERROR) {
                System.err.println("[Minvio " + level + "] " + message);
            } else {
                System.out.println("[Minvio " + level + "] " + message);
            }
        }
    }
}
