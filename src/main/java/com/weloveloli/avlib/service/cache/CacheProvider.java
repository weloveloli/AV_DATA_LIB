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


import com.weloveloli.avlib.service.AVService;

/**
 * cache provider
 *
 * @author esfak47
 * @date 2020/09/28
 */
public interface CacheProvider extends AVService {

    /**
     * get content from cache
     *
     * @param name cache key
     * @return cache content if exists otherwise null
     */
    String getFromCache(String name);

    /**
     * put content into cache
     *
     * @param name    cache key
     * @param content cache content
     * @return if the operation is success
     */
    boolean writeCache(String name, String content);
}
