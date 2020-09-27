package com.weloveloli.avlib.extractor;

import com.weloveloli.avlib.AVEnvironment;
import junit.framework.TestCase;

public class ExtractorFactoryTest extends TestCase {

    public void testGetExtractor() {

        final AVEnvironment environment = new AVEnvironment().setEnableCache(false);
        final Extractor javdb = ExtractorFactory.getExtractor(environment, "javdb");
        assertNotNull(javdb);
    }
}