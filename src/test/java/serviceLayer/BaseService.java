package serviceLayer;

import org.openqa.selenium.WebDriver;
import stepDefinition.WebDriverProvider;
import utility.WebPageObjectFactory;

public  class BaseService {

    protected WebDriver driver;
    protected WebPageObjectFactory webPageObjectFactory;


    // Constructor to initialize the WebDriver
    public BaseService(WebDriver driver) {
        this.driver =WebDriverProvider.getChromeDriver() ;
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
