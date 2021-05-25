package com.qa.pages;

import com.qa.resources.BaseTest;
import com.utils.TestUtils;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class commonTopMenu extends BaseTest {
	
	TestUtils utils=new TestUtils();

	
	@AndroidFindBy(xpath="//android.view.ViewGroup[@content-desc=\"test-Menu\"]/android.view.ViewGroup/android.widget.ImageView")
	private MobileElement settingsBtn;
	
	@AndroidFindBy(accessibility="test-LOGOUT")
	private MobileElement logoutBtn;
	
	
	public settingsMenu clickSettingsBtn() {
		
		
		click(settingsBtn,"Clicking settings button");
		return new settingsMenu();
		
	}
	
public LoginPage clickLogoutButton() {
		
		
		click(logoutBtn, "Clicking logout button");
		return new LoginPage();
		
	}


public void logout() {
	
	clickSettingsBtn();
	clickLogoutButton();
	
}

}
