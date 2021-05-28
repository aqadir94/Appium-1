package com.qa.test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.resources.BaseTest;



import java.util.HashMap;
import java.util.Objects;

public class DeepLink {

    public static void OpenAppWith(String url){
    	BaseTest b1=new BaseTest();
        AppiumDriver driver = b1.getDriver();

        switch(Objects.requireNonNull(driver.getPlatformName())){
            case "android":
                HashMap<String, String> deepUrl = new HashMap<>();
                deepUrl.put("url", url);
                deepUrl.put("package", "com.swaglabsmobileapp");
                driver.executeScript("mobile: deepLink", deepUrl); //pass the url and apppackage as a hashmap
                break;
            case "ios":
            	//when safari opens for the first time the url bar is a button
                By urlBtn = MobileBy.iOSNsPredicateString("type == 'XCUIElementTypeButton' && name CONTAINS 'URL'");
                //when clicked it becomes a text field
                By urlFld = MobileBy.iOSNsPredicateString("type == 'XCUIElementTypeTextField' && name CONTAINS 'URL'");
                //the popup to open the app
                By openBtn = MobileBy.iOSNsPredicateString("type == 'XCUIElementTypeButton' && name CONTAINS 'Open'");
                driver.activateApp("com.apple.mobilesafari");
                WebDriverWait wait = new WebDriverWait(driver, 10);
                wait.until(ExpectedConditions.visibilityOfElementLocated(urlBtn)).click();
                wait.until(ExpectedConditions.visibilityOfElementLocated(urlFld)).sendKeys("" + url + "\uE007"); //enter the 
                //URL and hit enter through the keyboard
                wait.until(ExpectedConditions.visibilityOfElementLocated(openBtn)).click();
                break;
        }
    }
}
