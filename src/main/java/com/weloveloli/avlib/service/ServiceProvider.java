package com.weloveloli.avlib.service;

public interface ServiceProvider {
    <T extends AVService> T getService(Class<T> clazz);

}
