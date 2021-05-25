package com.qa.pages;

import com.qa.resources.BaseTest;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class settingsMenu extends BaseTest {
	
	
	@AndroidFindBy(accessibility="test-LOGOUT")
	private MobileElement logout;
	
	
	
	public LoginPage clickLogout() {
		
		
		click(logout, "Clicking logout");
		return new LoginPage();
		
		
	}
	

}
