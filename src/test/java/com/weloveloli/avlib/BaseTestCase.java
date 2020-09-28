package com.weloveloli.avlib;

import com.weloveloli.avlib.service.AvServiceProvider;
import junit.framework.TestCase;

public abstract class BaseTestCase extends TestCase {
    protected AVEnvironment environment;
    protected AvServiceProvider serviceProvider;

    public BaseTestCase() {
        this.environment = new AVEnvironment();
        this.serviceProvider = new AvServiceProvider(environment);
    }

}
