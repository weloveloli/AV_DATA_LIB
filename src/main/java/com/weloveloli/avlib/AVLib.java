package com.weloveloli.avlib;

public class AVLib {

    private final AVEnvironment environment;

    private AVLib() {
        environment = new AVEnvironment();
    }

    public static AVLib of() {
        return new AVLib();
    }

    public void start() {

    }


}
