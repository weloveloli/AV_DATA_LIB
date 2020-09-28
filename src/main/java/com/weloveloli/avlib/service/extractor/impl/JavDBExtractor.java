package com.weloveloli.avlib.service.extractor.impl;

import com.google.auto.service.AutoService;
import com.weloveloli.avlib.AVEnvironment;
import com.weloveloli.avlib.model.AvData;
import com.weloveloli.avlib.service.ServiceProvider;
import com.weloveloli.avlib.service.connect.HtmlContentReader;
import com.weloveloli.avlib.service.extractor.Extractor;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.seimicrawler.xpath.JXDocument;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@AutoService(value = Extractor.class)
public class JavDBExtractor implements Extractor {
    public static final String JAVDB = "javdb";
    private static ThreadLocal<String> url = ThreadLocal.withInitial(() -> null);
    private HtmlContentReader htmlContentReader;
    private String baseUrl;
    @Override
    public Optional<AvData> getData(String number) {
        final JXDocument rootDocument = getRootDocument(number);
        if (rootDocument == null) {
            return Optional.empty();
        }
        AvData avData = new AvData();
        avData.setNumber(getNum(rootDocument))
                .setActors(getActors(rootDocument))
                .setDirectors(getDirector(rootDocument))
                .setMainCover(getMainCover(rootDocument))
                .setCovers(getCovers(rootDocument))
                .setTags(getTags(rootDocument))
                .setLabels(getLabels(rootDocument))
                .setTitle(getTitle(rootDocument))
                .setTime(getTime(rootDocument))
                .setRelease(getRelease(rootDocument))
                .setYear(getYeah(rootDocument))
                .setStudio(getStudio(rootDocument))
                .setOutline(getOutline(rootDocument))
                .setSeries(getSeries(rootDocument))
                .setWebSiteUrl(url.get())
                .setPreviewVideo(getPreviewVideo(rootDocument))
                .setMagnets(getMags(rootDocument))
                .setSource(JAVDB)
        ;
        return Optional.of(avData);
    }


    protected JXDocument getRootDocument(String number) {
        final Document searchDocumentInternal = this.getSearchDocumentInternal(number);
        if (searchDocumentInternal == null) {
            return null;
        }
        String url = getDetailPage(number, searchDocumentInternal);
        if (url == null) {
            return null;
        }
        JavDBExtractor.url.set(url);
        String detailPage = htmlContentReader.loadFromUrl(url);
        if (detailPage != null) {
            return JXDocument.create(detailPage);
        }
        return null;
    }

    protected String getDetailPage(String number, Document searchDocumentInternal) {
        final JXDocument jxDocument = JXDocument.create(searchDocumentInternal);
        /*
           javdb sometime returns multiple results,
            and the first elememt maybe not the one we are looking for
            iterate all candidates and find the match one
         */
        final List<Object> urls = jxDocument.sel("//*[@id=\"videos\"]/div/div/a/@href");
        final List<Object> ids = jxDocument.sel("//*[@id=\"videos\"]/div/div/a/div[contains(@class, \"uid\")]/text()");
        String url = null;
        for (int i = 0; i < ids.size(); i++) {
            final String id = (String) ids.get(i);
            if (id.equalsIgnoreCase(number)) {
                url = (String) urls.get(i);
            }
        }
        return this.baseUrl + url + "?locale=zh";
    }


    protected Document getSearchDocumentInternal(String number) {
        String s = htmlContentReader.loadFromUrl(String.format("%s/search?q=%s&f=all", baseUrl, number));
        if (s != null) {
            return Jsoup.parse(s);
        } else {
            return null;
        }
    }

    @Override
    public void init(AVEnvironment environment, ServiceProvider serviceProvider) {
        this.htmlContentReader = serviceProvider.getService(HtmlContentReader.class);
        this.baseUrl = "https://javdb4.com";
    }

    protected String getTitle(JXDocument root) {
        return root.selNOne("/body/section/div/h2/strong/text()").asString();
    }

    protected List<String> getActors(JXDocument root) {
        final List<Object> actor1 = root.sel("//strong[contains(text(),\"演員\")]/../span/text()");
        final List<Object> actor2 = root.sel("//strong[contains(text(),\"演員\")]/../span/a/text()");
        actor1.addAll(actor2);
        return actor1.stream().map(this::strip).filter(Predicate.not(String::isBlank)).collect(Collectors.toList());
    }

    protected String getStudio(JXDocument root) {
        final List<Object> data1 = root.sel("//strong[contains(text(),\"片商\")]/../span/text()");
        final List<Object> data2 = root.sel("//strong[contains(text(),\"片商\")]/../span/a/text()");
        data1.addAll(data2);
        return data1.stream().map(this::strip).filter(Predicate.not(String::isBlank)).findFirst().orElse("未知");
    }

    protected String getYeah(JXDocument root) {
        final String release = getRelease(root);
        return release != null ? release.substring(0, 4) : "未知";
    }

    protected String getOutline(JXDocument root) {
        final Optional<Object> jxNode = Optional.ofNullable(root.selOne("//*[@id=\"introduction\"]/dd/p[1]/text()"));
        return jxNode.map(this::strip).orElse("暂无");
    }

    protected String getRelease(JXDocument root) {
        final List<Object> data1 = root.sel("//strong[contains(text(),\"時間\")]/../span/text()");
        final List<Object> data2 = root.sel("//strong[contains(text(),\"時間\")]/../span/a/text()");
        data1.addAll(data2);
        return data1.stream().map(this::strip).filter(Predicate.not(String::isBlank)).findFirst().orElse("未知");
    }

    protected String getMainCover(JXDocument root) {
        Optional<String> o = Optional.ofNullable((String) root.selOne("//div[contains(@class, 'column-video-cover')]/a/img/@src"));
        return o.orElse((String) root.selOne("//div[contains(@class, 'column-video-cover')]/img/@src"));

    }

    protected String getTime(JXDocument root) {
        final List<Object> data1 = root.sel("//strong[contains(text(),\"時長\")]/../span/text()");
        final List<Object> data2 = root.sel("//strong[contains(text(),\"時長\")]/../span/a/text()");
        data1.addAll(data2);
        return data1.stream().map(this::strip).filter(Predicate.not(String::isBlank)).findFirst().orElse("未知");
    }

    protected String getNum(JXDocument root) {
        final List<Object> data1 = root.sel("//strong[contains(text(),\"番號\")]/../span/text()");
        final List<Object> data2 = root.sel("//strong[contains(text(),\"番號\")]/../span/a/text()");
        data2.addAll(data1);
        return data2.stream().map(this::strip).filter(Predicate.not(String::isBlank)).reduce(String::concat).orElse("未知");
    }

    protected List<String> getLabels(JXDocument root) {
        final List<Object> data1 = root.sel("//strong[contains(text(),\"系列\")]/../span/text()");
        final List<Object> data2 = root.sel("//strong[contains(text(),\"系列\")]/../span/a/text()");
        data1.addAll(data2);
        return data1.stream().map(this::strip).filter(Predicate.not(String::isBlank)).collect(Collectors.toList());
    }

    protected List<String> getDirector(JXDocument root) {
        final List<Object> data1 = root.sel("//strong[contains(text(),\"導演\")]/../span/text()");
        final List<Object> data2 = root.sel("//strong[contains(text(),\"導演\")]/../span/a/text()");
        data1.addAll(data2);
        return data1.stream().map(this::strip).filter(Predicate.not(String::isBlank)).collect(Collectors.toList());
    }

    protected List<String> getTags(JXDocument root) {
        final List<Object> data1 = root.sel("//strong[contains(text(),\"類別\")]/../span/text()");
        final List<Object> data2 = root.sel("//strong[contains(text(),\"類別\")]/../span/a/text()");
        data1.addAll(data2);
        return data1.stream().map(this::strip).filter(Predicate.not(String::isBlank)).collect(Collectors.toList());
    }

    protected List<String> getSeries(JXDocument root) {
        final List<Object> data1 = root.sel("//strong[contains(text(),\"系列\")]/../span/text()");
        final List<Object> data2 = root.sel("//strong[contains(text(),\"系列\")]/../span/a/text()");
        data1.addAll(data2);
        return data1.stream().map(this::strip).filter(Predicate.not(String::isBlank)).collect(Collectors.toList());
    }

    protected List<String> getMags(JXDocument root) {
        final List<Object> objects = root.sel("//td[contains(@class,'magnet-name')]/a/@href");
        return objects.stream().map(this::strip).filter(Predicate.not(String::isBlank)).collect(Collectors.toList());
    }

    protected List<String> getCovers(JXDocument root) {
        final List<Object> objects = root.sel("//div[contains(@class,'tile-images preview-images')]/a[contains(@data-fancybox, 'gallery')]/img/@data-src");

        return objects.stream().map(this::strip).filter(Predicate.not(String::isBlank)).collect(Collectors.toList());
    }

    protected String getPreviewVideo(JXDocument root) {
        return Optional.ofNullable(root.selOne("//div[contains(@class,'tile-images preview-images')]/video/source/@src")).map(this::urlFix).orElse("");
    }

    protected String strip(Object content) {
        return StringUtils.strip(content.toString(), " [',']");
    }

    protected String urlFix(Object content) {
        if (StringUtils.isBlank(content.toString())) {
            return StringUtils.EMPTY;
        }
        final String string = content.toString();
        return string.startsWith("https") ? string : "https:" + content;
    }
}
