package com.weloveloli.avlib.service;

import com.weloveloli.avlib.AVEnvironment;
import com.weloveloli.avlib.service.cache.CacheProvider;
import com.weloveloli.avlib.service.cache.FileCacheProvider;
import com.weloveloli.avlib.service.connect.DefaultHtmlContentReader;
import com.weloveloli.avlib.service.connect.HtmlContentReader;
import org.junit.Assert;
import org.junit.Test;

public class AvServiceProviderTest {

    @Test
    public void getSingletonService() throws Exception {
        AvServiceProvider avServiceProvider = new AvServiceProvider(new AVEnvironment());
        avServiceProvider.registerSingleton(CacheProvider.class, FileCacheProvider.class);
        avServiceProvider.registerSingleton(HtmlContentReader.class, DefaultHtmlContentReader.class);
        final HtmlContentReader reader = avServiceProvider.getService(HtmlContentReader.class);
        final HtmlContentReader reader2 = avServiceProvider.getService(HtmlContentReader.class);
        Assert.assertNotNull(reader);
        Assert.assertSame(reader, reader2);
    }

}