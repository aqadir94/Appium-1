package com.qa.test;

import org.testng.annotations.Test;

import com.qa.pages.LoginPage;
import com.qa.pages.ProductsPage;
import com.qa.resources.BaseTest;
import com.utils.TestUtils;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import io.appium.java_client.android.AndroidDriver;
public class LogInTest extends BaseTest {

	LoginPage logInPage;
	ProductsPage productsPage;
	
	JSONObject loginUsers;
	TestUtils utils=new TestUtils();
	@BeforeClass
	
public void beforeClass() throws IOException {
		FileInputStream fis = null;
		try {
			
			String filePath=System.getProperty("user.dir")+"/src/main/java/com/qa/data/loginusers.json";
			fis=new FileInputStream(new File(filePath));
			JSONTokener jt=new JSONTokener(fis);
			loginUsers= new JSONObject(jt);
			
		
		}
		catch(Exception e) {
			
			e.printStackTrace();
			throw e; //throwing the exception will make sure that test fails
		}
		
		finally {
			
			if(fis!=null) {
				
				fis.close();
			}
			
		}
		

		closeApp();
		launchApp();
		
		
		
	}
	
	
	
  @BeforeMethod
  public void beforeMethod(Method m) {
	  
	  logInPage=new LoginPage();
	  productsPage=new ProductsPage();
	  utils.log().info("Current test being executed "+m.getName());
  }
 
  
  @Test
  
  
  public void invalidUserName() {
	  
	  
	  logInPage.enterUsername(loginUsers.getJSONObject("invalidUserName").getString("username"));
	  logInPage.enterPassword(loginUsers.getJSONObject("invalidUserName").getString("password"));
	  logInPage.clickLoginBtn();
	  
	  String expectedMsg=getStrings().get("err_invalid_user_or_password");
	  String errMsg=logInPage.getErrTxt();
	  utils.log().info("Error message is "+ errMsg);
	  Assert.assertEquals(expectedMsg, errMsg);
	  
	 
  }
  
  @Test
  public void invalidPassword() {
	  logInPage.enterUsername(loginUsers.getJSONObject("invalidPassword").getString("username"));
	  logInPage.enterPassword(loginUsers.getJSONObject("invalidPassword").getString("password"));
	  logInPage.clickLoginBtn();
	  
	  String expectedMsg=getStrings().get("err_invalid_user_or_password");
	  String errMsg=logInPage.getErrTxt();
	  utils.log().info("Error message is "+ errMsg);
	  Assert.assertEquals(expectedMsg, errMsg+"i");
  }
  
  @Test
  public void validCredentials() {
	  logInPage.enterUsername(loginUsers.getJSONObject("validCredentials").getString("username"));
	  logInPage.enterPassword(loginUsers.getJSONObject("validCredentials").getString("password"));
	  
	  utils.log(loginUsers.getString("name"));
	  logInPage.clickLoginBtn();
	  
	  String title=getStrings().get("product_tile");
	  String actTitle=productsPage.getTitle();
	  utils.log().info("Actual title is "+ actTitle);
	  Assert.assertEquals(actTitle, title);
	  productsPage.logout();
  }


}
