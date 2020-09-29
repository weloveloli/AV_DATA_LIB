package com.weloveloli.avlib.service.proxy;

import com.weloveloli.avlib.AVEnvironment;
import com.weloveloli.avlib.BaseTestCase;

import java.io.IOException;
import java.net.Proxy;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class DefaultProxySelectorTest extends BaseTestCase {


    public void testGetProxy() {
        DefaultProxySelector defaultProxySelector = new DefaultProxySelector();
        defaultProxySelector.init(environment, serviceProvider);
        final Proxy proxy = defaultProxySelector.getProxy();
        assertSame(Proxy.NO_PROXY, proxy);
    }

    public void testGetSockProxy() {
        DefaultProxySelector defaultProxySelector = new DefaultProxySelector();
        AVEnvironment avEnvironment = new AVEnvironment().setProxyType(Proxy.Type.SOCKS.name()).setProxyList(List.of("127.0.0.1:8080"));
        defaultProxySelector.init(avEnvironment, serviceProvider);
        final Proxy proxy = defaultProxySelector.getProxy();
        assertNotNull(proxy);
        assertEquals(Proxy.Type.SOCKS, proxy.type());
        assertEquals("/127.0.0.1:8080", proxy.address().toString());
    }

    public void testGetHttpProxy() {
        DefaultProxySelector defaultProxySelector = new DefaultProxySelector();
        AVEnvironment avEnvironment = new AVEnvironment().setProxyType(Proxy.Type.HTTP.name()).setProxyList(List.of("127.0.0.1:8080"));
        defaultProxySelector.init(avEnvironment, serviceProvider);
        final Proxy proxy = defaultProxySelector.getProxy();
        assertNotNull(proxy);
        assertEquals(Proxy.Type.HTTP, proxy.type());
        assertEquals("/127.0.0.1:8080", proxy.address().toString());
    }

    public void testGetProxyWithInvalidType() {
        DefaultProxySelector defaultProxySelector = new DefaultProxySelector();
        AVEnvironment avEnvironment = new AVEnvironment().setProxyType("invalid").setProxyList(List.of("127.0.0.1:8080"));
        defaultProxySelector.init(avEnvironment, serviceProvider);
        final Proxy proxy = defaultProxySelector.getProxy();
        assertSame(Proxy.NO_PROXY, proxy);
    }

    public void testGetProxyWithInvalidProxy() {
        DefaultProxySelector defaultProxySelector = new DefaultProxySelector();
        AVEnvironment avEnvironment = new AVEnvironment().setProxyType(Proxy.Type.SOCKS.name()).setProxyList(List.of("127.0.0.1"));
        defaultProxySelector.init(avEnvironment, serviceProvider);
        final Proxy proxy = defaultProxySelector.getProxy();
        assertSame(Proxy.NO_PROXY, proxy);
    }

    public void testGetProxyWhenEmptyProxyList() {
        DefaultProxySelector defaultProxySelector = new DefaultProxySelector();
        AVEnvironment avEnvironment = new AVEnvironment().setProxyType(Proxy.Type.SOCKS.name()).setProxyList(List.of());
        defaultProxySelector.init(avEnvironment, serviceProvider);
        final Proxy proxy = defaultProxySelector.getProxy();
        assertSame(Proxy.NO_PROXY, proxy);
    }


    public void testGetProxyWithCheck() throws URISyntaxException, IOException {
        DefaultProxySelector defaultProxySelector = new DefaultProxySelector();
        final List<String> proxyList = Files.readAllLines(Path.of(Objects.requireNonNull(this.getClass().getClassLoader().getResource("proxy/sock5.txt")).toURI()));
        AVEnvironment avEnvironment = new AVEnvironment().setProxyType(Proxy.Type.SOCKS.name()).setProxyCheck(true).setProxyList(proxyList);
        defaultProxySelector.init(avEnvironment, serviceProvider);
        final Proxy proxy = defaultProxySelector.getProxy();
        assertNotNull(proxy);
        System.out.println(proxy.type());
    }
}