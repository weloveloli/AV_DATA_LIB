package com.weloveloli.avlib.extractor.impl;

import com.google.auto.service.AutoService;
import com.weloveloli.avlib.AVEnvironment;
import com.weloveloli.avlib.extractor.Extractor;
import com.weloveloli.avlib.model.AvData;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

@AutoService(value = Extractor.class)
public class JavDBExtractor implements Extractor {

    private final Logger log = LoggerFactory.getLogger(JavDBExtractor.class);
    private AVEnvironment env;

    @Override
    public String key() {
        return "javdb";
    }

    @Override
    public Optional<AvData> getData(String number) {

        final Document rootDocument = getRootDocument(number);
        if (rootDocument == null) {
            return Optional.empty();
        }

        return Optional.empty();
    }

    protected Document getRootDocument(String number) {
        try {
            URL url = new URL(String.format("https://javdb.com/search?q=%s&f=all", number));
            return Jsoup.parse(url, (int) (env.getTimeoutInSeconds() * 1000L));
        } catch (IOException e) {
            log.error("load search result failed", e);
        }
        try {
            URL url = new URL(String.format("https://javdb4.com/search?q=%s&f=all", number));

            return Jsoup.parse(url, (int) (env.getTimeoutInSeconds() * 1000L));
        } catch (IOException e) {
            log.error("load search result failed", e);
        }
        return null;
    }

    @Override
    public void init(AVEnvironment environment) {
        this.env = environment;
    }
}
