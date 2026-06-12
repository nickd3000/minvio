package com.physmo.minvio.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceLock;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ResourceLock("SYSTEM_OUT_ERR")
class MinvioLoggerTest {
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @AfterEach
    void restoreGlobalState() {
        System.setOut(originalOut);
        System.setErr(originalErr);
        MinvioLogger.setLogLevel(MinvioLogger.LogLevel.INFO);
    }

    @Test
    void filtersMessagesBelowConfiguredLevelAndRoutesErrorToStderr() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayOutputStream err = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        System.setErr(new PrintStream(err));
        MinvioLogger.setLogLevel(MinvioLogger.LogLevel.WARN);

        MinvioLogger.debug("debug");
        MinvioLogger.info("info");
        MinvioLogger.warn("warn");
        MinvioLogger.error("error");

        assertEquals("[Minvio WARN] warn" + System.lineSeparator(), out.toString());
        assertEquals("[Minvio ERROR] error" + System.lineSeparator(), err.toString());
    }

    @Test
    void noneSilencesAllMessages() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayOutputStream err = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        System.setErr(new PrintStream(err));
        MinvioLogger.setLogLevel(MinvioLogger.LogLevel.NONE);

        MinvioLogger.debug("debug");
        MinvioLogger.info("info");
        MinvioLogger.warn("warn");
        MinvioLogger.error("error");

        assertEquals("", out.toString());
        assertEquals("", err.toString());
    }
}
