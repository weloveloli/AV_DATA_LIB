package com.weloveloli.avlib.service.extractor;


import com.weloveloli.avlib.model.AvData;
import com.weloveloli.avlib.service.AVService;

import java.util.Optional;

public interface Extractor extends AVService {

    Optional<AvData> getData(String number);

}
