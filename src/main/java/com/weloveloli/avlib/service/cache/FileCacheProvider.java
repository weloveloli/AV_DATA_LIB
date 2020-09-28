package com.weloveloli.avlib.service.cache;

import com.weloveloli.avlib.AVEnvironment;
import com.weloveloli.avlib.service.ServiceProvider;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class FileCacheProvider implements CacheProvider {

    private Path cacheDir;

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

    @Override
    public void init(AVEnvironment avEnvironment, ServiceProvider serviceProvider) {
        this.cacheDir = Optional.ofNullable(avEnvironment.getCachePath()).map(Path::of).orElse(null);
    }
}
