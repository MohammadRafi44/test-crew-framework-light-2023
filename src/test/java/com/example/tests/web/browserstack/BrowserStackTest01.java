/*
package com.example.tests.web.browserstack;

import java.net.URL;
import java.util.List;
import java.net.MalformedURLException;

import com.example.base.BaseTest;
import com.example.base.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import org.testng.annotations.Test;

public class BrowserStackTest01 extends BaseTest {

        @Test
        public void BrowserStackSampleTest() throws MalformedURLException {
            DesiredCapabilities caps = new DesiredCapabilities();

            // Set your access credentials
            caps.setCapability("browserstack.user", "mshaik_uVT0xe");
            caps.setCapability("browserstack.key", "tZzkCVQZgvFY2oahTyxb");

            // Set URL of the application under test
            caps.setCapability("app", "bs://696509e2469ba6f8340b9b83d3185462eff21322");

            // Specify device and os_version for testing
            caps.setCapability("device", "iPad Pro 12.9 2021");
            caps.setCapability("os_version", "14");

            // Set other BrowserStack capabilities
            caps.setCapability("project", "First Java Project");
            caps.setCapability("build", "Java iOS");
            caps.setCapability("name", "first_test");


            // Initialise the remote Webdriver using BrowserStack remote URL
            // and desired capabilities defined above
            IOSDriver<IOSElement> driver = new IOSDriver<IOSElement>(
                    new URL("http://hub-cloud.browserstack.com/wd/hub"), caps);

            DriverManager.getDriver().findElement(By.xpath("")).sendKeys("");

            */
/* Write your Custom code here *//*

            // Invoke driver.quit() after the test is done to indicate that the test is completed.
            driver.quit();

        }

}
*/
