package com.qa.test;

import org.testng.asserts.SoftAssert;

import com.qa.pages.LoginPage;
import com.qa.pages.ProductDetails;
import com.qa.pages.ProductsPage;
import com.qa.resources.BaseTest;
import com.utils.TestUtils;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.time.Duration;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.openqa.selenium.Dimension;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
public class ProductTest extends BaseTest {

	LoginPage logInPage;
	ProductsPage productsPage;

	JSONObject loginUsers;
	ProductDetails productDetailsPage;
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
	  productDetailsPage=new ProductDetails();
	  utils.log().info("Current test being executed "+m.getName());
	
	  
  }
  
  @AfterMethod
  public void afterMethod() {
	  
	  productDetailsPage.logout();
	  
  }
 
  /*
  @Test
  
  
  public void productTest() {
	  //driver.activateApp("com.swaglabsmobileapp");
	  SoftAssert sa=new SoftAssert();

	  
	  
	  String prodName=productsPage.getSLBTitle();
	  utils.log().info("\n Actual Title is -"+prodName);
	  sa.assertEquals(prodName, getStrings().get("product_name"));
	  
	  String prodPrice=productsPage.getSLBPrice();
	  utils.log().info("\n Actual price is -"+prodPrice);
	  sa.assertEquals(prodPrice, getStrings().get("price"));
	 
	  sa.assertAll();
	  
  }
  
  */
  /*  
  @Test
  
  
  public void productDetailsTest()  {
	  
	  SoftAssert sa=new SoftAssert();
	  

	  
	 
	  
	  productsPage.clickSLBTitle();
	  String expProdName=getStrings().get("product_name");
	  String actProdName=productDetailsPage.getSLBTitle();
	 sa.assertEquals(expProdName, actProdName);
	  
	  
	  String expProdTxt=getStrings().get("product_text");
	  String actProdTxt=productDetailsPage.getSLBtxt();
	  sa.assertEquals(expProdTxt, actProdTxt);*/
	  
	  /*
	  TouchAction ta=new TouchAction(driver);
	  
	  Dimension d=driver.manage().window().getSize();
	  int startX=d.width/2;
	  int endX=startX;
	  int startY=(int)(d.getHeight()*0.8);
	  int endY=(int)(d.getHeight()*0.3);
	  
	  ta.press(PointOption.point(startX, startY)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(5))).
	  moveTo(PointOption.point(endX, endY)).release().perform();
	  
	  String expProdPrice=strings.get("price");
	  String actProdPrice=productDetailsPage.getSLBPrice();
	  sa.assertEquals(expProdPrice, actProdPrice);
	  */
	  
	  /*productDetailsPage.scrollToPrice();
	  sa.assertAll();*/
	  
  
  @Test
  public void test1() throws InterruptedException {
	  
	  logInPage.login(loginUsers.getJSONObject("validCredentials").getString("username"),
			  loginUsers.getJSONObject("validCredentials").getString("password") );
	  productsPage.addToCart();
	  
  }

}

