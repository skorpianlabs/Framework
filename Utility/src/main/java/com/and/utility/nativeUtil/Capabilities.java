package com.and.utility.nativeUtil;

import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;

public class Capabilities {
    private final DesiredCapabilities cap;

    /**
     * Method for setting the data from the property file into the capabilities
     */
    public Capabilities() throws IOException {
        cap = new DesiredCapabilities();

        cap.setCapability("platformName", DeviceUtils.getPropertyFile("PLATFORM_NAME"));
        cap.setCapability("platformVersion", DeviceUtils.getPropertyFile("PLATFORM_VERSION"));
        cap.setCapability("deviceName", DeviceUtils.getPropertyFile("DEVICE_NAME"));
        cap.setCapability("udid", DeviceUtils.getPropertyFile("UDID"));

        cap.setCapability("bundleId", DeviceUtils.getPropertyFile("BUNDLE_ID"));
        cap.setCapability("xcodeOrgId", DeviceUtils.getPropertyFile("XCODE_ORG_ID"));
        cap.setCapability("xcodeSigningId", DeviceUtils.getPropertyFile("XCODE_SIGNING_ID"));
        cap.setCapability("appium:noReset", DeviceUtils.getPropertyFile("NO_RESET"));
        cap.setCapability("appium:automationName", DeviceUtils.getPropertyFile("AUTOMATION_NAME"));

    }

    /**
     * Method for returning the data setup in the above method
     *
     * @return capabilities
     */
    public DesiredCapabilities getCapabilities() {
        return this.cap;
    }
}
