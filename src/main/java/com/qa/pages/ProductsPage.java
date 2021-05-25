package com.qa.pages;

import java.util.List;

import org.openqa.selenium.remote.RemoteWebElement;

import com.qa.resources.BaseTest;

import com.utils.TestUtils;

import io.appium.java_client.FindsByAndroidUIAutomator;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class ProductsPage extends commonTopMenu{
	TestUtils utils=new TestUtils();
	@AndroidFindBy(xpath="//android.view.ViewGroup[@content-desc=\"test-Cart drop zone\"]/android.view.ViewGroup/android.widget.TextView")
	@iOSXCUITFindBy(xpath="iOS Xpath")
	private MobileElement productsTitleTxt;
	
	
	@AndroidFindBy(xpath="(//android.widget.TextView[@content-desc=\"test-Item title\"])[1]")
	private MobileElement SLBTitle;
	
	@AndroidFindBy(xpath="(//android.widget.TextView[@content-desc=\"test-Price\"])[1]")
	private MobileElement SLBPrice;
	
	
	public String getTitle() {
		
		
		return getText(productsTitleTxt, "Title is");
	}
	
	public String getSLBTitle() {
		
		return getText(SLBTitle, "Title is");
		
		
	}
	
	public String getSLBPrice() {
		
		String price=getText(SLBPrice, "Price is");
	
		
		return price;
		
		
	}
	
	public ProductDetails clickSLBTitle() {
		
	
		click(SLBTitle, "Title is");
		
		
		
		return new  ProductDetails();
	}
	
	
	public void addToCart() throws InterruptedException {
		Thread.sleep(5000);
		
		getDriver().findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()."
				+"scrollable(true)).scrollIntoView(new UiSelector().text(\"Sauce Labs Fleece Jacket\"))"));
		boolean isFound=false;
		/*
		 * while(isFound==false) { List<MobileElement> ele=getDriver().
		 * findElementsByXPath("//android.widget.TextView[@content-desc='test-Item title']"
		 * ); for(int i=0;i<ele.size();i++) {
		 * 
		 * MobileElement me=((MobileElement) getDriver().
		 * findElementsByXPath("//android.widget.TextView[@content-desc=\"test-Item title\"]"
		 * ).get(i));
		 * 
		 * String text= me.getText(); if(text.contentEquals("Sauce Labs Onesie")) {
		 * me.click(); isFound=true; }
		 * 
		 * else {
		 * 
		 * 
		 * getDriver().findElement(MobileBy.
		 * AndroidUIAutomator("new UiScrollable(new UiSelector()."
		 * +"scrollable(true)).scrollIntoView(new UiSelector().text(\"ADD TO CART\").instance("
		 * +(i+3)+"))"));
		 * 
		 * 
		 * }  //android.widget.TextView[contains(text(),'Bike Light')]
		 * 
		 * 
		 * } }
		 */	
		//scrollToElement("\"Sauce Labs Fleece Jacket\"");
	
		
		
	}
}

