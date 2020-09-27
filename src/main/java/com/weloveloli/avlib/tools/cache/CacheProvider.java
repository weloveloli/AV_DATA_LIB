package com.weloveloli.avlib.tools.cache;

import java.nio.file.Path;

public interface CacheProvider {
    boolean contains(String name);

    String getFromCache(String name);

    Path writeCache(String name, String content);
}
