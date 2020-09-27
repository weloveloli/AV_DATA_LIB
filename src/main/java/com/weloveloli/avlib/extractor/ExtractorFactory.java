package com.weloveloli.avlib.extractor;

import com.weloveloli.avlib.AVEnvironment;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

public class ExtractorFactory {

    public static Map<String, Extractor> extractorMap = new HashMap<>(16);

    public static Extractor getExtractor(AVEnvironment environment, String key) {
        if (key == null) {
            return null;
        }
        if (extractorMap.containsKey(key)) {
            return extractorMap.get(key);
        }
        ServiceLoader<Extractor> extractors = ServiceLoader.load(Extractor.class);
        for (Extractor extractor : extractors) {
            if (!extractorMap.containsKey(extractor.key())) {
                extractor.init(environment);
                extractorMap.put(key, extractor);
            }
            if (key.equals(extractor.key())) {
                return extractor;
            }
        }
        return null;
    }
}
