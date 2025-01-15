package serviceLayer;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import org.openqa.selenium.WebDriver;
import stepDefinition.WebDriverProvider;
import utility.PageObjectFactory;

import java.util.List;
import java.util.Map;

public  class BaseService {

    protected WebDriver driver;
    protected PageObjectFactory pageObjectFactory;


    // Constructor to initialize the WebDriver
    public BaseService(WebDriver driver) {
        this.driver =WebDriverProvider.getChromeDriver() ;
    }

    // Setup the PageObjectFactory
    protected void setupPageFactory() {
        if (pageObjectFactory == null) {

            this.pageObjectFactory = new PageObjectFactory(driver);
        }
    }

}
