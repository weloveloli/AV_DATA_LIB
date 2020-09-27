package com.weloveloli.avlib.tools.connect.impl;

import com.weloveloli.avlib.AVEnvironment;
import junit.framework.TestCase;

public class DefaultHtmlContentReaderTest extends TestCase {

    public void test() {

        new DefaultHtmlContentReader(new AVEnvironment().setEnableCache(false));
    }

}