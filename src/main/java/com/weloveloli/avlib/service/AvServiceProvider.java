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

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.weloveloli.avlib.AVEnvironment;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * av service provider
 *
 * @author esfak47
 * @date 2020/09/28
 */
public class AvServiceProvider implements ServiceProvider {

    private final Multimap<Class<? extends AVService>, ServiceDefinition> serviceDefinitionMap;
    private final Map<Class<? extends AVService>, AVService> singleton;
    private final AVEnvironment environment;

    public AvServiceProvider(AVEnvironment avEnvironment) {
        this.serviceDefinitionMap = HashMultimap.create();
        singleton = new HashMap<>();
        this.environment = avEnvironment;
    }

    @Override
    public <T extends AVService> T getService(String name, Class<T> clazz) {
        if (!serviceDefinitionMap.containsKey(clazz)) {
            return null;
        }
        final Collection<ServiceDefinition> serviceDefinitions = serviceDefinitionMap.get(clazz);
        final Optional<ServiceDefinition> first = serviceDefinitions.stream().filter(serviceDefinition -> serviceDefinition.name.equals(name)).findFirst();
        if (first.isEmpty()) {
            return null;
        }
        ServiceDefinition<T> serviceDefinition = first.get();

        if (serviceDefinition.isSingleton) {
            return (T) singleton.get(serviceDefinition.clazz);
        } else {
            try {
                final T newInstance = serviceDefinition.clazz.getDeclaredConstructor().newInstance();
                newInstance.init(environment, this);
                return newInstance;
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                return null;
            }
        }
    }

    @Override
    public <T extends AVService> Collection<T> getServices(Class<T> clazz) {
        if (!serviceDefinitionMap.containsKey(clazz)) {
            return null;
        }
        final Collection<ServiceDefinition> serviceDefinitions = serviceDefinitionMap.get(clazz);
        final AvServiceProvider avServiceProvider = this;
        return serviceDefinitions.stream().map(serviceDefinition -> {
            if (serviceDefinition.isSingleton) {
                return (T) singleton.get(serviceDefinition.clazz);
            } else {
                try {
                    final T newInstance = (T) serviceDefinition.clazz.getDeclaredConstructor().newInstance();
                    newInstance.init(environment, avServiceProvider);
                    return newInstance;
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    return null;
                }
            }
        }).filter(Objects::nonNull).collect(Collectors.toSet());
    }

    public <R extends AVService, T extends R> void registerSingleton(Class<R> rClass, Class<T> impl, String name) {
        register(rClass, new ServiceDefinition<>(true, impl, name));
    }

    public <R extends AVService, T extends R> void registerSingleton(Class<R> rClass, Class<T> impl) {
        registerSingleton(rClass, impl, rClass.getSimpleName());
    }

    public <R extends AVService, T extends R> void registerSingleton(Class<R> rClass, T impl, String name) {
        final ServiceDefinition<T> serviceDefinition = new ServiceDefinition<>(true, (Class<T>) impl.getClass(), name);
        serviceDefinitionMap.put(rClass, serviceDefinition);
        singleton.put(impl.getClass(), impl);
    }

    public <R extends AVService, T extends R> void registerSingleton(Class<R> rClass, T impl) {
        registerSingleton(rClass, impl, rClass.getSimpleName());
    }

    public <R extends AVService, T extends R> void register(Class<R> rClass, ServiceDefinition<T> definition) {
        try {
            serviceDefinitionMap.put(rClass, definition);
            final Constructor<T> declaredConstructor = definition.clazz.getDeclaredConstructor();
            if (definition.isSingleton) {
                final T newInstance = declaredConstructor.newInstance();
                newInstance.init(environment, this);
                singleton.put(newInstance.getClass(), newInstance);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }


    public static class ServiceDefinition<T extends AVService> {
        private final boolean isSingleton;
        private final Class<T> clazz;
        private final String name;

        public ServiceDefinition(boolean isSingleton, Class<T> clazz) {
            this.isSingleton = isSingleton;
            this.clazz = clazz;
            this.name = clazz.getSimpleName();
        }

        public ServiceDefinition(boolean isSingleton, Class<T> clazz, String name) {
            this.isSingleton = isSingleton;
            this.clazz = clazz;
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
