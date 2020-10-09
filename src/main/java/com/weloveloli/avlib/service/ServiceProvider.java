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

package com.weloveloli.avlib.service;

import java.util.Collection;

/**
 * ServiceProvider
 *
 * @author esfak47
 * @date 2020/09/28
 */
public interface ServiceProvider {
    /**
     * get service by type
     *
     * @param clazz class type
     * @param <T>   class template
     * @return the instance of class type T
     */
    default <T extends AVService> T getService(Class<T> clazz) {
        if (clazz != null) {
            return getService(clazz.getSimpleName(), clazz);
        }
        return null;
    }

    <T extends AVService> T getService(String name, Class<T> clazz);


    <T extends AVService> Collection<T> getServices(Class<T> clazz);


}
