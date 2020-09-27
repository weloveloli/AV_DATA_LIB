package com.weloveloli.avlib.tools.cache.impl;

import com.weloveloli.avlib.tools.cache.CacheProvider;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileCacheProvider implements CacheProvider {

    private final Path cacheDir;

    public FileCacheProvider(Path cacheDir) {
        this.cacheDir = cacheDir;
    }


    @Override
    public boolean contains(String url) {
        final String s = DigestUtils.shaHex(url);
        return Files.exists(Path.of(cacheDir.toString(), s));
    }

    @Override
    public String getFromCache(String url) {
        final String s = DigestUtils.shaHex(url);
        try {
            return Files.readString(Path.of(cacheDir.toString(), s));
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public Path writeCache(String url, String content) {
        final String s = DigestUtils.shaHex(url);
        try {
            return Files.writeString(Path.of(cacheDir.toString(), s), content);
        } catch (IOException e) {
            return null;
        }
    }
}
