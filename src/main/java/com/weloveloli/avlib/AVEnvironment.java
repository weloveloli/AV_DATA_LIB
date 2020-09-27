package com.weloveloli.avlib;

public class AVEnvironment {

    private String pluginPath;

    private String logLevel;

    private int timeoutInSeconds = 10;

    public String getPluginPath() {
        return pluginPath;
    }

    public AVEnvironment setPluginPath(String pluginPath) {
        this.pluginPath = pluginPath;
        return this;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public AVEnvironment setLogLevel(String logLevel) {
        this.logLevel = logLevel;
        return this;
    }

    public int getTimeoutInSeconds() {
        return timeoutInSeconds;
    }

    public AVEnvironment setTimeoutInSeconds(int timeoutInSeconds) {
        this.timeoutInSeconds = timeoutInSeconds;
        return this;
    }
}
