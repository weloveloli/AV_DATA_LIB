package com.weloveloli.avlib.extractor;

import com.weloveloli.avlib.AVEnvironment;
import com.weloveloli.avlib.model.AvData;

import java.util.Optional;

public interface Extractor {

    String key();

    Optional<AvData> getData(String number);

    void init(AVEnvironment environment);

}
