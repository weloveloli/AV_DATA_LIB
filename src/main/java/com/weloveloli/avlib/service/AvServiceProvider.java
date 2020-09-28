package com.weloveloli.avlib.service;

import com.weloveloli.avlib.AVEnvironment;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class AvServiceProvider implements ServiceProvider {

    private final Map<Class, ServiceDefinition> serviceDefinitionMap;
    private final Map<Class, Object> singleton;
    private final AVEnvironment environment;

    public AvServiceProvider(AVEnvironment avEnvironment) {
        this.serviceDefinitionMap = new HashMap<>();
        singleton = new HashMap<>();
        this.environment = avEnvironment;
    }

    @Override
    public <T extends AVService> T getService(Class<T> clazz) {
        if (!serviceDefinitionMap.containsKey(clazz)) {
            return null;
        }
        final ServiceDefinition serviceDefinition = serviceDefinitionMap.get(clazz);
        if (serviceDefinition.isSingleton) {
            return (T) singleton.get(clazz);
        } else {
            try {
                final T newInstance = (T) serviceDefinition.clazz.getDeclaredConstructor().newInstance();
                newInstance.init(environment, this);
                return newInstance;
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                return null;
            }
        }
    }

    public <R extends AVService, T extends R> void registerSingleton(Class<R> rClass, Class<T> impl) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        register(rClass, new ServiceDefinition<>(true, impl));
    }

    public <R extends AVService, T extends R> void registerSingleton(Class<R> rClass, T impl) {
        final ServiceDefinition<T> serviceDefinition = new ServiceDefinition<T>(true, (Class<T>) impl.getClass());
        serviceDefinitionMap.put(rClass, serviceDefinition);
        singleton.put(rClass, impl);
    }

    public <R extends AVService, T extends R> void registerNonSingleton(Class<R> rClass, Class<T> impl) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        register(rClass, new ServiceDefinition<>(false, impl));
    }

    public <R extends AVService, T extends R> void register(Class<R> rClass, ServiceDefinition<T> definition) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        serviceDefinitionMap.put(rClass, definition);
        final Constructor<T> declaredConstructor = definition.clazz.getDeclaredConstructor();
        if (definition.isSingleton) {
            final T newInstance = declaredConstructor.newInstance();
            newInstance.init(environment, this);
            singleton.put(rClass, newInstance);
        }
    }


    public static class ServiceDefinition<T extends AVService> {
        private boolean isSingleton;
        private Class<T> clazz;

        public ServiceDefinition(boolean isSingleton, Class<T> clazz) {
            this.isSingleton = isSingleton;
            this.clazz = clazz;
        }
    }
}
