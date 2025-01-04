package me.aaronfulton.debug.logger;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.*;

public class ColorfulLogger {

    // ANSI color codes for log levels
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLUE = "\u001B[34m";    // INFO
    public static final String ANSI_RED = "\u001B[31m";     // SEVERE (ERROR)
    public static final String ANSI_YELLOW = "\u001B[33m";  // WARNING
    public static final String ANSI_GRAY = "\u001B[37m";    // DEBUG
    private static Logger logger;
    private static String pluginName;

    /**
     * Initializes the logger with a custom name.
     *
     * @param plugin The plugin instance.
     * @param name   The name of the plugin (for prefixing log messages).
     */
    public static void initialize(JavaPlugin plugin, String name) {
        logger = new CustomPluginLogger(plugin);
        pluginName = name;
        logger.setUseParentHandlers(false);

        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.ALL);

        consoleHandler.setFormatter(new Formatter() {
            @Override
            public String format(LogRecord record) {
                String levelColor = getColorByLevel(record.getLevel());
                String logType = getLogTypeByLevel(record.getLevel());
                String message = record.getMessage();
                return pluginName + " -> [" + levelColor + logType + ANSI_RESET + "] " + message + "\n";
            }
        });

        logger.addHandler(consoleHandler);
    }

    /**
     * Returns the current timestamp as a formatted string.
     */
    private static String getTimestamp() {
        return java.time.LocalTime.now().toString().substring(0, 8);  // HH:mm:ss format
    }

    /**
     * Returns the appropriate ANSI color based on the log level.
     *
     * @param level The log level.
     * @return ANSI color string.
     */
    private static String getColorByLevel(Level level) {
        if (level == Level.INFO) {
            return ANSI_BLUE;
        } else if (level == Level.WARNING) {
            return ANSI_YELLOW;
        } else if (level == Level.SEVERE) {
            return ANSI_RED;
        } else if (level == Level.FINE || level == Level.FINER || level == Level.FINEST) {
            return ANSI_GRAY;
        }
        return ANSI_RESET;
    }

    /**
     * Returns the appropriate log type string based on the log level.
     *
     * @param level The log level.
     * @return Log type string (e.g., "INFO", "WARNING", etc.).
     */
    private static String getLogTypeByLevel(Level level) {
        if (level == Level.INFO) {
            return "INFO";
        } else if (level == Level.WARNING) {
            return "WARNING";
        } else if (level == Level.SEVERE) {
            return "ERROR";
        } else if (level == Level.FINE || level == Level.FINER || level == Level.FINEST) {
            return "DEBUG";
        }
        return "UNKNOWN"; // Default log type
    }

    public static void info(String message) {
        logger.log(Level.INFO, message);
    }

    public static void warning(String message) {
        logger.log(Level.WARNING, message);
    }

    public static void error(String message) {
        logger.log(Level.SEVERE, message);
    }

    public static void debug(String message) {
        logger.log(Level.FINE, message);
    }
}
