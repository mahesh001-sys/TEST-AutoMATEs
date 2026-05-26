package com.automates.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Thread-safe singleton logger for the AutoMATEs framework.
 *
 * <p>Writes structured log lines to both stdout and {@code logs/automates.log}.</p>
 *
 * @author Banoth Mahesh Kumar
 */
public class Logger {

    private static volatile Logger instance;
    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    private static final String LOG_FILE = "logs/automates.log";

    private PrintWriter fileWriter;

    private Logger() {
        try {
            new java.io.File("logs").mkdirs();
            fileWriter = new PrintWriter(new FileWriter(LOG_FILE, true), true);
        } catch (IOException e) {
            System.err.println("[WARN] Could not open log file: " + e.getMessage());
        }
    }

    public static Logger getInstance() {
        if (instance == null) {
            synchronized (Logger.class) {
                if (instance == null) instance = new Logger();
            }
        }
        return instance;
    }

    public void info (String msg) { log("INFO ", msg); }
    public void warn (String msg) { log("WARN ", msg); }
    public void error(String msg) { log("ERROR", msg); }
    public void debug(String msg) { log("DEBUG", msg); }

    private void log(String level, String msg) {
        String line = String.format("[%s] [%s] %s",
                LocalDateTime.now().format(FMT), level, msg);
        System.out.println(line);
        if (fileWriter != null) fileWriter.println(line);
    }
}
