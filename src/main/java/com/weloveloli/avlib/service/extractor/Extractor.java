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

package com.weloveloli.avlib.service.extractor;


import com.weloveloli.avlib.model.AvData;
import com.weloveloli.avlib.service.AVService;

import java.util.Optional;

/**
 * av data extractor
 *
 * @author esfak47
 * @date 2020/09/28
 */
public interface Extractor extends AVService {

    /**
     * get av data by number
     *
     * @param number av number
     * @return optional AvData
     */
    Optional<AvData> getData(String number);

}
