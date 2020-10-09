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

package com.weloveloli.avlib;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.weloveloli.avlib.model.AvData;
import com.weloveloli.avlib.service.AvServiceProvider;
import com.weloveloli.avlib.service.cache.CacheProvider;
import com.weloveloli.avlib.service.cache.FileCacheProvider;
import com.weloveloli.avlib.service.connect.DefaultHtmlContentReader;
import com.weloveloli.avlib.service.connect.HtmlContentReader;
import com.weloveloli.avlib.service.extractor.Extractor;
import com.weloveloli.avlib.service.extractor.impl.JavDBExtractor;
import com.weloveloli.avlib.service.proxy.DefaultProxySelector;
import com.weloveloli.avlib.service.proxy.ProxySelector;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

/**
 * command line
 *
 * @author esfak47
 * @date 2020/09/28
 */
@Parameters(resourceBundle = "MessageBundle")
public final class AVCli {

    @Parameter(names = {"-v", "--verbose"}, help = true, descriptionKey = "avcli.verbose", order = 4)
    private boolean verbose;

    @Parameter(names = "--help", help = true, order = 5, descriptionKey = "avcli.help")
    private boolean help;

    @Parameter(names = "--cache", help = true, order = 2, descriptionKey = "avcli.cache")
    private boolean cache = false;

    @Parameter(names = "--cache-dir", help = true, order = 3, descriptionKey = "avcli.cacheDir")
    private String cacheDir;

    @Parameter(names = "--version", descriptionKey = "avcli.version", help = true, order = 6)
    private boolean v = false;
    private Properties properties;
    private NumberCommand numberCommand;
    private AVEnvironment environment;
    private AvServiceProvider serviceProvider;
    private String command;

    private AVCli() throws IOException {
        this.numberCommand = new NumberCommand();
        final InputStream configProperties = AVCli.class.getClassLoader().getResourceAsStream("config.properties");
        this.properties = new Properties();
        properties.load(configProperties);
        this.environment = new AVEnvironment();
        this.serviceProvider = new AvServiceProvider(environment);
    }

    public static void main(String[] args) throws Exception {
        AVCli.of().parse(args).run();
    }

    public static AVCli of() throws IOException {
        return new AVCli();
    }

    public boolean isVerbose() {
        return verbose;
    }

    public AVCli setVerbose(boolean verbose) {
        this.verbose = verbose;
        return this;
    }

    public boolean isHelp() {
        return help;
    }

    public AVCli setHelp(boolean help) {
        this.help = help;
        return this;
    }

    public boolean isCache() {
        return cache;
    }

    public AVCli setCache(boolean cache) {
        this.cache = cache;
        return this;
    }

    public String getCacheDir() {
        return cacheDir;
    }

    public AVCli setCacheDir(String cacheDir) {
        this.cacheDir = cacheDir;
        return this;
    }

    public boolean isV() {
        return v;
    }

    public AVCli setV(boolean v) {
        this.v = v;
        return this;
    }

    public Properties getProperties() {
        return properties;
    }

    public AVCli setProperties(Properties properties) {
        this.properties = properties;
        return this;
    }

    public NumberCommand getNumberCommand() {
        return numberCommand;
    }

    public AVCli setNumberCommand(NumberCommand numberCommand) {
        this.numberCommand = numberCommand;
        return this;
    }

    public AVEnvironment getEnvironment() {
        return environment;
    }

    public AVCli setEnvironment(AVEnvironment environment) {
        this.environment = environment;
        return this;
    }

    public AvServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public AVCli setServiceProvider(AvServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
        return this;
    }

    public String getCommand() {
        return command;
    }

    public AVCli setCommand(String command) {
        this.command = command;
        return this;
    }

    public AVCli parse(String[] args) {
        JCommander jCommander = JCommander.newBuilder().addObject(this)
                .addCommand("number", this.numberCommand, "n")
                .build();
        jCommander.parse(args);
        this.command = jCommander.getParsedCommand();
        if (command == null || command.isEmpty()) {
            help = true;
        }
        if (help) {
            jCommander.setProgramName("java -jar avcli.jar");
            jCommander.usage();
            return this;
        }
        if (v) {
            JCommander.getConsole().println(properties.getProperty("avcli.version"));
            return this;
        }
        if (verbose) {
            Logger.getRootLogger().setLevel(Level.DEBUG);
        }
        //TODO: add config file

        environment.setLogLevel(Logger.getRootLogger().getLevel().toString());
        if (cache) {
            environment.setEnableCache(true);
            if (StringUtils.isNoneBlank(cacheDir)) {
                environment.setCachePath(cacheDir);
            }
        }
        serviceProvider.registerSingleton(ProxySelector.class, DefaultProxySelector.class);
        serviceProvider.registerSingleton(CacheProvider.class, FileCacheProvider.class);
        serviceProvider.registerSingleton(HtmlContentReader.class, DefaultHtmlContentReader.class);
        serviceProvider.registerSingleton(Extractor.class, JavDBExtractor.class, JavDBExtractor.JAVDB);
        return this;
    }

    public void run() {
        if (this.command == null) {
            return;
        }
        final AVLib of = AVLib.of(serviceProvider, environment);
        if (this.command.equalsIgnoreCase("number")) {
            final Optional<AvData> number = of.number(numberCommand.number);
            if (number.isPresent()) {
                JCommander.getConsole().println(number.get().toString());
            } else {
                JCommander.getConsole().println(String.format("%s not found", numberCommand.number));
            }
        }
    }

    @Parameters(commandDescriptionKey = "number.command.description", resourceBundle = "MessageBundle")
    public static class NumberCommand {
        @Parameter(descriptionKey = "number.command.number", arity = 1)
        private String number;

        public NumberCommand setNumber(String number) {
            this.number = number;
            return this;
        }
    }
}
