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
import com.weloveloli.avlib.utils.LoggerFactory;
import org.apache.commons.lang3.RandomUtils;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * default proxy selector
 *
 * @author esfak47
 * @date 2020/09/28
 */
public class DefaultProxySelector implements ProxySelector {
    private AVEnvironment env;
    private List<Proxy> proxies;
    private Logger log = LoggerFactory.getLogger("DefaultProxySelector");

    public boolean isConnected(String name, int port) {
        if (env.isProxyCheck()) {
            Socket socket;
            try {
                socket = new Socket(name, port);
                socket.sendUrgentData(0xFF);
                log.info(String.format("%s:%d is valid", name, port));
                return true;
            } catch (Exception e) {
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
        final int nextInt = RandomUtils.nextInt(0, env.getProxyList().size());
        return proxies.get(nextInt);
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
            proxies = new LinkedList<>();
            env.getProxyList()
                    .parallelStream()
                    .map(s -> s.split(":"))
                    .filter(split -> split.length == 2)
                    .forEach(split -> {
                        final String hostname = split[0];
                        final int port = Integer.parseInt(split[1]);
                        if (isConnected(hostname, port)) {
                            InetSocketAddress address = new InetSocketAddress(hostname, port);
                            proxies.add(new Proxy(type, address));
                        }
                    });
        } else {
            this.proxies = Collections.emptyList();
        }
    }

}
