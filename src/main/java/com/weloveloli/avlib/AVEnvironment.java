/*
 * Copyright 2020-2020
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 *
 */

package com.weloveloli.avlib;

import java.net.Proxy;
import java.util.Collections;
import java.util.List;

/**
 * environment
 *
 * @author esfak47
 * @date 2020/09/28
 */
public class AVEnvironment {

    private String pluginPath;

    private String logLevel;

    private String cachePath;

    private boolean enableCache;

    private String ua;

    private int timeoutInSeconds;

    private String proxyType;

    private List<String> proxyList;

    private boolean proxyCheck;

    public AVEnvironment() {
        this.enableCache = false;
        this.timeoutInSeconds = 10;
        this.proxyType = "direct";
        this.proxyList = Collections.emptyList();
        this.enableCache = false;
        this.cachePath = null;
        this.proxyCheck = false;
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

    public boolean isEnableCache() {
        return enableCache;
    }

    public AVEnvironment setEnableCache(boolean enableCache) {
        this.enableCache = enableCache;
        return this;
    }

    public String getUa() {
        return ua;
    }

    public AVEnvironment setUa(String ua) {
        this.ua = ua;
        return this;
    }

    public String getProxyType() {
        return proxyType;
    }

    public AVEnvironment setProxyType(String proxyType) {
        this.proxyType = proxyType;
        return this;
    }

    public List<String> getProxyList() {
        return proxyList;
    }

    public AVEnvironment setProxyList(List<String> proxyList) {
        this.proxyList = proxyList;
        return this;
    }

    public boolean isProxyEnable() {
        return this.getProxyType() != null && (this.getProxyType().equalsIgnoreCase(Proxy.Type.SOCKS.name()) || this.getProxyType().equalsIgnoreCase(Proxy.Type.HTTP.name())) && this.getProxyList() != null && this.getProxyList().size() >= 1;
    }

    public String getCachePath() {
        return cachePath;
    }

    public AVEnvironment setCachePath(String cachePath) {
        this.cachePath = cachePath;
        return this;
    }

    public boolean isProxyCheck() {
        return proxyCheck;
    }

    public AVEnvironment setProxyCheck(boolean proxyCheck) {
        this.proxyCheck = proxyCheck;
        return this;
    }
}
