package com.and.pagesweb;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static com.and.constant.CommonConstant.ENDED;
import static com.and.constant.CommonConstant.STARTED;

public class FaultDetailsPage extends BasePage {

    private WebDriver driver;
    private Map<String, String> faultDetailsMap = new HashMap<>();

    public FaultDetailsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Use @FindBy to locate the form element containing the tooltip attribute
    @FindBy(xpath = "//fnd-input-field[@id='FaultDetails-FaultDetailsGroup-Description']//form")
    private WebElement descriptionForm;

    public void putDescriptionValueToMap() throws InterruptedException {
        logger.info(STARTED + getCurrentMethodName());
        Thread.sleep(5000);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//fnd-input-field[@id='FaultDetails-FaultDetailsGroup-Description']//form")));
        String description = descriptionForm.getAttribute("tooltip");
        faultDetailsMap.put("Description", description);
        System.out.println(description);
        logger.info(ENDED + getCurrentMethodName());
    }

    public String getDescriptionValueFromMap() {
        logger.info(STARTED + getCurrentMethodName());
        String description = faultDetailsMap.get("Description");
        System.out.println(description);
        logger.info(ENDED + getCurrentMethodName());
        return description;
    }
}
