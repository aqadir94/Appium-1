package com.qa.pages;

import com.qa.resources.BaseTest;
import com.utils.TestUtils;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class LoginPage extends BaseTest{
	
	@AndroidFindBy(accessibility="test-Username")
	private MobileElement userNameTxtFld;
	
	@AndroidFindBy(accessibility="test-Password")
	@iOSXCUITFindBy(accessibility="test-Password")// if this doesn't work use id
	private MobileElement passWordTxtFld;
	
	@AndroidFindBy(accessibility="test-LOGIN")
	@iOSXCUITFindBy(accessibility="test-Password")// if this doesn't work use id
	private MobileElement logInBtn;
	
	
	@AndroidFindBy(xpath="//android.view.ViewGroup[@content-desc=\"test-Error message\"]/android.widget.TextView")
	@iOSXCUITFindBy(accessibility="iOS Xpath")
	private MobileElement errMsg;
	TestUtils utils=new TestUtils();
	
	public LoginPage enterUsername(String userName) {
		
		userNameTxtFld.clear();
	
		sendKeys(userNameTxtFld,userName,"Entering username "+userName);
		
		return this;
	}
	
	
	
public LoginPage enterPassword(String passWord) {
	utils.log().info("\n Entering password " +passWord);
	passWordTxtFld.clear();
		sendKeys(passWordTxtFld,passWord,"Entering Password "+passWord);
		
		
		return this;
	}

public String getErrTxt() {
	String err=getText(errMsg, "Error message is -");
	
	
	
	
	return err;
	
}


public ProductsPage clickLoginBtn() {
	utils.log().info("\n Clicking on login button");
	click(logInBtn,"Clicking login button");
	
	
	return new ProductsPage();


}

public ProductsPage login(String userName, String password) {
	
	enterUsername(userName);
	enterPassword(password);
	clickLoginBtn();
	
	return new ProductsPage();
	
}


}
