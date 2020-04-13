package com.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.net.MalformedURLException;
import java.net.URL;

public class BaseTest {
    protected WebDriver driver;


    @BeforeTest
    public  void setupDriver(final ITestContext ctx) throws MalformedURLException {

        // BROWSER => chrome / firefox
        // HUB_HOST =>  localhost / 10.0.1.2 / hostname

        String host = "localhost";
        DesiredCapabilities dc;

        if(System.getProperty("BROWSER") != null &&
                System.getProperty("BROWSER").equalsIgnoreCase("firefox")){
            dc = DesiredCapabilities.firefox();
        } else {
            dc =  DesiredCapabilities.chrome();
        }

        if (System.getProperty("HUB_HOST") != null) {
            host = System.getProperty("HUB_HOST");
        }
        
        if(driver instanceof RemoteWebDriver){
            ((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
         }

        final String testName = ctx.getCurrentXmlTest().getName();

        final String completeURL = "http://" + host + ":4444/wd/hub";
        dc.setCapability("name", testName);
        this.driver = new RemoteWebDriver(new URL(completeURL), dc);
    }

    @AfterTest
    public void quitBrowser(){
        this.driver.quit();
    }

}
