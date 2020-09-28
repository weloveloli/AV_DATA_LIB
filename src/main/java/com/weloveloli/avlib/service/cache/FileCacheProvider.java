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

package com.weloveloli.avlib.service.cache;

import com.weloveloli.avlib.AVEnvironment;
import com.weloveloli.avlib.service.ServiceProvider;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

/**
 * local file cache provider
 *
 * @author esfak47
 * @date 2020/09/28
 */
public class FileCacheProvider implements CacheProvider {

    private Path cacheDir;

    @Override
    public String getFromCache(String url) {
        final String s = DigestUtils.shaHex(url);
        try {
            final boolean exists = Files.exists(Path.of(cacheDir.toString(), s));
            return exists ? Files.readString(Path.of(cacheDir.toString(), s)) : null;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public boolean writeCache(String url, String content) {
        final String s = DigestUtils.shaHex(url);
        try {
            Files.writeString(Path.of(cacheDir.toString(), s), content);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public void init(AVEnvironment avEnvironment, ServiceProvider serviceProvider) {
        this.cacheDir = Optional.ofNullable(avEnvironment.getCachePath()).map(Path::of).orElse(null);
    }
}
