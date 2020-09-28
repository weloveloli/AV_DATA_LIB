package com.weloveloli.avlib.service.connect;

import com.weloveloli.avlib.AVEnvironment;
import com.weloveloli.avlib.service.ServiceProvider;
import com.weloveloli.avlib.service.cache.CacheProvider;
import com.weloveloli.avlib.service.proxy.ProxySelector;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;

import java.io.IOException;

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
