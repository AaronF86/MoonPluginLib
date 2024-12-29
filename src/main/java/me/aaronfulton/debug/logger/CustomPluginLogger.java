package me.aaronfulton.debug.logger;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginLogger;

import java.util.logging.LogRecord;

public class CustomPluginLogger extends PluginLogger {

    /**
     * Constructs a new CustomPluginLogger with an empty prefix.
     *
     * @param plugin The plugin instance.
     */
    public CustomPluginLogger(Plugin plugin) {
        super(plugin);
    }

    @Override
    public void log(LogRecord record) {
        // Override the prefix with an empty string
        record.setMessage(record.getMessage());
        super.log(record);
    }


}
