package com.weloveloli.avlib;

import com.weloveloli.avlib.tools.cache.CacheProvider;
import com.weloveloli.avlib.tools.cache.impl.FileCacheProvider;
import com.weloveloli.avlib.tools.connect.HtmlContentReader;
import com.weloveloli.avlib.tools.connect.impl.DefaultHtmlContentReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class AVEnvironment {

    private String pluginPath;

    private String logLevel;

    private String cachePath;

    private boolean enableCache;

    private String ua;

    private int timeoutInSeconds = 10;

    public AVEnvironment() {
        this.enableCache = false;
    }

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


    public Path getCachePath() {
        try {
            return Optional.ofNullable(this.cachePath).map(Path::of).orElse(Files.createTempDirectory("av"));
        } catch (IOException e) {
            return null;
        }
    }

    public AVEnvironment setCachePath(String cachePath) {
        this.cachePath = cachePath;
        return this;
    }

    public boolean isEnableCache() {
        return enableCache;
    }

    public AVEnvironment setEnableCache(boolean enableCache) {
        this.enableCache = enableCache;
        return this;
    }

    public HtmlContentReader getHtmlContentReader() {
        return new DefaultHtmlContentReader(this);
    }

    public CacheProvider getCacheProvider() {
        return new FileCacheProvider(this.getCachePath());
    }

    public String getUa() {
        return ua;
    }

    public AVEnvironment setUa(String ua) {
        this.ua = ua;
        return this;
    }
}
