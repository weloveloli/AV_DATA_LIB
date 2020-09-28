package com.weloveloli.avlib.service.proxy;


import com.weloveloli.avlib.service.AVService;

import java.net.Proxy;

public interface ProxySelector extends AVService {

    Proxy getProxy();
}
