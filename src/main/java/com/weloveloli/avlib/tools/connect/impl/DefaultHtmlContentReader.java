package com.weloveloli.avlib.tools.connect.impl;

import com.weloveloli.avlib.AVEnvironment;
import com.weloveloli.avlib.tools.cache.CacheProvider;
import com.weloveloli.avlib.tools.connect.HtmlContentReader;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;

import java.io.IOException;

public class DefaultHtmlContentReader implements HtmlContentReader {

    private final CacheProvider cacheProvider;
    private final AVEnvironment environment;
    private final int timeout;
    private final String ua;

    public DefaultHtmlContentReader(AVEnvironment environment) {
        this.environment = environment;
        if (environment.isEnableCache()) {
            this.cacheProvider = environment.getCacheProvider();
        } else {
            this.cacheProvider = null;
        }
        this.timeout = environment.getTimeoutInSeconds() * 1000;
        if (environment.getUa() != null) {
            this.ua = environment.getUa();
        } else {
            this.ua = HttpConnection.DEFAULT_UA;
        }

    }

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

}
