package com.and.and.pagesweb;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BasePage {

    public final Logger logger = LogManager.getLogger(BasePage.class);

/**
 * Method for getting the current executing method name
 */
public String getCurrentMethodName() {

    StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
    String methodName = stackTrace[2].getMethodName();
    return methodName;
}
}