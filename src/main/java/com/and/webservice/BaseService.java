package com.and.webservice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import com.and.utility.DriverProvider;
import com.and.utility.WebPageObjectFactory;

public  class BaseService {

    protected WebDriver driver;
    protected WebPageObjectFactory webPageObjectFactory;
    protected   Logger logger = LogManager.getLogger(RaiseFaultWebService.class);


    // Constructor to initialize the WebDriver
    public BaseService(WebDriver driver) {
        this.driver = DriverProvider.getChromeDriver() ;
    }

    // Setup the PageObjectFactory
    protected void setupPageFactory() {
        if (webPageObjectFactory == null) {

            this.webPageObjectFactory = new WebPageObjectFactory(driver);
        }
    }
    public String getCurrentMethodName() {

        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String methodName = stackTrace[2].getMethodName();
        return methodName;
    }

}
