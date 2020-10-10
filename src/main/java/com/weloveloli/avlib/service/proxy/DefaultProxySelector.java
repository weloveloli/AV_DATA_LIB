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

package com.weloveloli.avlib.service.proxy;

import com.weloveloli.avlib.AVEnvironment;
import com.weloveloli.avlib.service.ServiceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Round-Robin Scheduling proxy selector
 *
 * @author esfak47
 * @date 2020/09/28
 */
public class DefaultProxySelector implements ProxySelector {
    private final AtomicInteger next = new AtomicInteger(0);
    private final Logger log = LoggerFactory.getLogger(DefaultProxySelector.class);
    private AVEnvironment env;
    private List<Proxy> proxies;

    public Proxy checkProxy(Proxy.Type type, String s) {
        final String[] split = s.split(":");
        if (split.length != 2) {
            return null;
        }
        final String hostname = split[0];
        final int port = Integer.parseInt(split[1]);
        if (isConnected(hostname, port)) {
            InetSocketAddress address = new InetSocketAddress(hostname, port);
            return new Proxy(type, address);
        }
        return null;
    }

    private boolean isConnected(String name, int port) {
        if (env.isProxyCheck()) {
            try (Socket socket = new Socket(name, port)) {
                socket.sendUrgentData(0xFF);
                log.debug("{}:{} is valid", name, port);
                return true;
            } catch (Exception e) {
                log.warn("{}:{} is invalid, remove from proxy list", name, port);
                return false;
            }
        }
        return true;
    }

    @Override
    public Proxy getProxy() {
        if (!env.isProxyEnable() || this.proxies.isEmpty()) {
            return Proxy.NO_PROXY;
        }
        // Round-Robin
        return proxies.get(next.getAndIncrement() % this.proxies.size());
    }

    @Override
    public void init(AVEnvironment env, ServiceProvider serviceProvider) {
        this.env = env;
        if (env.isProxyEnable()) {
            Proxy.Type type;
            if (this.env.getProxyType().equalsIgnoreCase(Proxy.Type.SOCKS.name())) {
                type = Proxy.Type.SOCKS;
            } else {
                type = Proxy.Type.HTTP;
            }
            final Set<String> collect = new HashSet<>(env.getProxyList());
            ExecutorService executorService = Executors.newFixedThreadPool(20);
            try {
                final List<Future<Proxy>> futures = executorService.invokeAll(collect.stream().map((Function<String, Callable<Proxy>>) s -> () -> checkProxy(type, s)).collect(Collectors.toList()));
                this.proxies = futures.stream().map(proxyFuture -> {
                    try {
                        return proxyFuture.get();
                    } catch (InterruptedException | ExecutionException e) {
                        log.error("check proxy failed", e);
                        Thread.currentThread().interrupt();
                        return null;
                    }
                }).filter(Objects::nonNull).collect(Collectors.toList());
                executorService.shutdown();
            } catch (InterruptedException e) {
                log.info("check proxy failed", e);
                Thread.currentThread().interrupt();
                proxies = Collections.emptyList();
            }
        } else {
            this.proxies = Collections.emptyList();
        }
    }

}
