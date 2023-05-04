package com.example.cinemabooker.controllers;

public final class ControllerDefaults {
    private ControllerDefaults() {

    }

    public static final int PAGE_NUMBER = 0;
    public static final String PAGE_NUMBER_AS_STRING = "0"; //Spring requires const value instead of String.valueOf(...)
    public static final int PAGE_SIZE = 5;
    public static final String PAGE_SIZE_AS_STRING = "5";
    public static final String ALL_LINK_REL = "all";
}
