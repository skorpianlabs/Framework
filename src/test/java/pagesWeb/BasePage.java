package pagesWeb;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BasePage {

    public static final Logger logger = LogManager.getLogger(BasePage.class);


/**
 * Method for getting the current executing method name
 */
public static String getCurrentMethodName() {

    StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
    String methodName = stackTrace[2].getMethodName();
    return methodName;
}
}