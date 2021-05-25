package com.qa.pages;

import com.utils.TestUtils;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class ProductDetails extends commonTopMenu{
	TestUtils utils=new TestUtils();


	@AndroidFindBy(xpath="//android.view.ViewGroup[@content-desc=\"test-Description\"]/android.widget.TextView[1]")
	private MobileElement SLBTitle;
	
	@AndroidFindBy(xpath="//android.view.ViewGroup[@content-desc=\"test-Description\"]/android.widget.TextView[2]")
	private MobileElement SLBtxt;
	
	@AndroidFindBy(xpath="//android.widget.TextView[@content-desc=\"test-Price\"]")
	private MobileElement SLBPrice;
	
	@AndroidFindBy(accessibility="test-BACK TO PRODUCTS")
	private MobileElement backToPBtn;
	
	public String getSLBTitle() {
		
	
		return getText(SLBTitle, "Title is");
	
	}
	
	public String getSLBtxt() {

		return getText(SLBtxt, "Product text is");
		
		
	}
	
	public String getSLBPrice() {

		return getText(SLBPrice, "Price is");
		
		
	}
	
	public ProductsPage clickbackToPBtn(){
		
		click(backToPBtn, "clicking back to products");
		return new ProductsPage();
	}
	
	public ProductDetails scrollToPrice() {
		
		scrollToElement("\"test-Price\"");
		
		
		return this;
	}
	

}
