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
import org.apache.commons.lang3.RandomUtils;

import java.net.InetSocketAddress;
import java.net.Proxy;

/**
 * default proxy selector
 *
 * @author esfak47
 * @date 2020/09/28
 */
public class DefaultProxySelector implements ProxySelector {
    private AVEnvironment env;

    @Override
    public Proxy getProxy() {
        if (!env.isProxyEnable()) {
            return Proxy.NO_PROXY;
        }
        final int nextInt = RandomUtils.nextInt(0, env.getProxyList().size());
        final String s = env.getProxyList().get(nextInt);
        final String[] split = s.split(":");
        if (split.length != 2) {
            return Proxy.NO_PROXY;
        }
        final InetSocketAddress address = new InetSocketAddress(split[0], Integer.parseInt(split[1]));

        if (env.getProxyType().equalsIgnoreCase(Proxy.Type.SOCKS.name())) {
            return new Proxy(Proxy.Type.SOCKS, address);
        } else {
            return new Proxy(Proxy.Type.HTTP, address);
        }
    }

    @Override
    public void init(AVEnvironment avEnvironment, ServiceProvider serviceProvider) {
        this.env = avEnvironment;
    }
}
