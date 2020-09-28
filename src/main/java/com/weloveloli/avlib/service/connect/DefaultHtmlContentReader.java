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

package com.weloveloli.avlib.service.connect;

import com.weloveloli.avlib.AVEnvironment;
import com.weloveloli.avlib.service.ServiceProvider;
import com.weloveloli.avlib.service.cache.CacheProvider;
import com.weloveloli.avlib.service.proxy.ProxySelector;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;

import java.io.IOException;

/**
 * default html content reader
 *
 * @author esfak47
 * @date 2020/09/28
 */
public class DefaultHtmlContentReader implements HtmlContentReader {

    private int timeout;
    private String ua;
    private CacheProvider cacheProvider;
    private AVEnvironment environment;
    private ProxySelector proxySelector;

    @Override
    public String loadFromUrl(String url) {
        if (environment.isEnableCache()) {
            final String fromCache = cacheProvider.getFromCache(url);
            if (fromCache != null) {
                return fromCache;
            }
        }
        try {
            final String body = Jsoup.connect(url)
                    .timeout(timeout)
                    .userAgent(ua)
                    .proxy(proxySelector.getProxy())
                    .execute()
                    .body();
            if (environment.isEnableCache()) {
                cacheProvider.writeCache(url, body);
            }
            return body;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public void init(AVEnvironment avEnvironment, ServiceProvider serviceProvider) {
        this.environment = avEnvironment;
        this.cacheProvider = serviceProvider.getService(CacheProvider.class);
        this.proxySelector = serviceProvider.getService(ProxySelector.class);
        this.timeout = environment.getTimeoutInSeconds() * 1000;
        if (environment.getUa() != null) {
            this.ua = environment.getUa();
        } else {
            this.ua = HttpConnection.DEFAULT_UA;
        }
    }
}
