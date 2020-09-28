package com.weloveloli.avlib.service.cache;



import com.weloveloli.avlib.service.AVService;

import java.nio.file.Path;

public interface CacheProvider extends AVService {
    boolean contains(String name);

    String getFromCache(String name);

    Path writeCache(String name, String content);
}
