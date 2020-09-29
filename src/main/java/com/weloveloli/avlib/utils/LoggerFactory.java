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

package com.weloveloli.avlib.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author esfak47
 * @date 2020/09/29
 */
public final class LoggerFactory {
    private static Level level = Level.INFO;

    public static void setLevel(Level level) {
        LoggerFactory.level = level;
    }

    public static Logger getLogger(String name) {
        Logger log = Logger.getLogger(name);
        log.setLevel(level);
        return log;
    }
}
