package com.brandon.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ActionTypes {
    public static final String RESUME_DOWNLOAD = "RESUME_DOWNLOAD";
    public static final String PAGE_LOAD = "PAGE_LOAD";
    public static final String VIEW_SOURCE = "VIEW_SOURCE";

    public static final Set<String> VALID_TYPES = new HashSet<>(Arrays.asList(
            RESUME_DOWNLOAD, PAGE_LOAD, VIEW_SOURCE
    ));
}