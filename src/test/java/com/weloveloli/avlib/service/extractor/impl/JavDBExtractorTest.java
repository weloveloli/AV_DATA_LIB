package com.weloveloli.avlib.service.extractor.impl;

import com.weloveloli.avlib.AVEnvironment;
import com.weloveloli.avlib.BaseTestCase;
import com.weloveloli.avlib.model.AvData;
import com.weloveloli.avlib.service.AvServiceProvider;
import com.weloveloli.avlib.service.connect.HtmlContentReader;
import org.mockito.Mockito;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class JavDBExtractorTest extends BaseTestCase {
    @Override
    protected void setUp() {
        HtmlContentReader reader = Mockito.mock(HtmlContentReader.class);
        Mockito.when(reader.loadFromUrl(String.format("https://javdb4.com/search?q=%s&f=all", "mum-120"))).thenReturn(getContent("javdb/testsearch.html"));
        Mockito.when(reader.loadFromUrl("https://javdb4.com/v/8VvnW?locale=zh")).thenReturn(getContent("javdb/testDetail.html"));
        serviceProvider.registerSingleton(HtmlContentReader.class, reader);
    }

    private String getContent(String url) {
        try {
            final URL resource = JavDBExtractorTest.class.getClassLoader().getResource(url);
            final Path path = Paths.get(resource.toURI());
            return Files.readString(path);
        } catch (Exception e) {
            return null;
        }

    }

    public void testGetData() {
        JavDBExtractor javDBExtractor = new JavDBExtractor();
        javDBExtractor.init(environment, serviceProvider);
        final Optional<AvData> optionalAvData = javDBExtractor.getData("mum-120");
        assertTrue(optionalAvData.isPresent());
        final AvData avData = optionalAvData.get();
        assertNotNull(avData.getNumber());
        assertNotNull(avData.getTags());
        assertNotNull(avData.getTitle());
        assertNotNull(avData.getActors());
        assertNotNull(avData.getDirectors());
        assertNotNull(avData.getTime());
        assertNotNull(avData.getMainCover());
        assertNotNull(avData.getCovers());
        assertNotNull(avData.getSource());
        assertNotNull(avData.getLabels());
        assertNotNull(avData.getOutline());
        assertNotNull(avData.getRelease());
        assertNotNull(avData.getYear());
    }

}