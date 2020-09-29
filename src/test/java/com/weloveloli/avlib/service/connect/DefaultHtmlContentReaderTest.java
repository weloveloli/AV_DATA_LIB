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

import com.weloveloli.avlib.BaseTestCase;
import com.weloveloli.avlib.service.proxy.DefaultProxySelector;
import com.weloveloli.avlib.service.proxy.ProxySelector;
import org.junit.Test;

import java.net.Proxy;
import java.util.List;

/**
 * @author esfak47
 * @date 2020/09/29
 */
public class DefaultHtmlContentReaderTest extends BaseTestCase {

    private List<String> sock5ProxyList;

    private ProxySelector selector;

    @Override
    public void setUp() throws Exception {
        sock5ProxyList = List.of("117.64.55.8:1080");
        environment.setProxyType(Proxy.Type.SOCKS.name()).setProxyList(sock5ProxyList).setProxyCheck(true);
        serviceProvider.registerSingleton(ProxySelector.class, DefaultProxySelector.class);
        serviceProvider.registerSingleton(HtmlContentReader.class, DefaultHtmlContentReader.class);
    }

    public void testLoadFromUrl() {
        final DefaultHtmlContentReader service = ((DefaultHtmlContentReader) serviceProvider.getService(HtmlContentReader.class));
        final String s = service.loadFromUrl("https://javdb4.com");
        assertNotNull(s);

    }
}