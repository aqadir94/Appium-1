package com.qa.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.Status;
import com.qa.reports.ExtentReport;
import com.utils.TestUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.FindsByAndroidUIAutomator;
import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.screenrecording.CanRecordScreen;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
public class BaseTest {
	
	//this class is inherited by all test and page object classes
	public BaseTest() {
		//initialize page factory which will be used in all page object methods
		PageFactory.initElements(new AppiumFieldDecorator(getDriver()), this);
	}
	//all global variables are thread local to make it thread safe for parrllel execution
	//prop, strings and and dateTime here doesn't have to be thread local as they don't change for each thread
	protected static ThreadLocal <AppiumDriver> driver=new ThreadLocal <AppiumDriver>();
	protected static ThreadLocal <Properties> prop=new ThreadLocal <Properties>();
	protected static ThreadLocal <HashMap<String,String>> strings=new ThreadLocal <HashMap<String,String>>();
	protected static ThreadLocal<String> platform= new ThreadLocal<String>();
	protected static ThreadLocal <String> dateTime= new ThreadLocal<String>();
	protected static ThreadLocal <String> deviceName= new ThreadLocal<String>();
	protected static TestUtils utils = new TestUtils();
	private static AppiumDriverLocalService server;
	
	
	//getters and setters must be set for all thread local variables 

	public AppiumDriver getDriver() {
		
		return driver.get();
		
	}
	
	
public void setDriver(AppiumDriver driver2) {
		
		driver.set(driver2);
		
	}
	



public String getDeviceName() {
	
	return deviceName.get();
	
}


public void setDeviceName(String deviceName2) {
	
	deviceName.set(deviceName2);
	
}




	
	public String getTime() {
		
		
		return dateTime.get();
	}
	
	public void setTime(String dateTime2) {
		
		dateTime.set(dateTime2);
		
	}
public Properties getprop() {
		
		
		return prop.get();
	}


public void setProp(Properties prop2) {
	
	
	prop.set(prop2);
}

public void setStrings(HashMap <String,String>strings2) {
	
	strings.set(strings2);
	
}


public HashMap<String,String> getStrings(){
	
	
return strings.get();	
	
}


public String getPlatform() {
	
	return platform.get();
	
}


public void setPlatform(String platform2) {
	
	platform.set(platform2);
	
}



	
	//even though each test class has it's own @BeforeMethod since they inherit BaseTest the method below 
    //order- parent b.method->child b.method->child a.method->parent a.method
	
	
//start recording the screen before every method
	@BeforeMethod
	public  void beforeMethod() {
		((CanRecordScreen) getDriver()).startRecordingScreen();
		utils.log().info("in before method");
	}
	
	//start recording and convert the file in to an mp4 	
	@AfterMethod
	public  void afterMethod(ITestResult result) throws Exception {  //ItestResult will get the test result
		String media = ((CanRecordScreen) getDriver()).stopRecordingScreen();
		utils.log().info("in after method");
		if(result.isSuccess()) { 
			
			Map <String, String> params = result.getTestContext().getCurrentXmlTest().getAllParameters();//gets all parameters passed from the testng xml file		
			String dirPath = "videos" + File.separator + params.get("platformName") + "_" + params.get("deviceName") 
			+ File.separator + getTime() + File.separator + result.getTestClass().getRealClass().getSimpleName();
			
			File videoDir = new File(dirPath);
			
			
				if(!videoDir.exists()) {
					videoDir.mkdirs();
				}	
			
			FileOutputStream stream = null;
			try {
				stream = new FileOutputStream(videoDir + File.separator + result.getName() + ".mp4");
				stream.write(Base64.decodeBase64(media));
				
			} catch (Exception e) {
				e.printStackTrace();
			} 
			
		}
		
		
			
	}
	
	
	//execution starts here
	@BeforeSuite
	public void beforeSuite() {
		ThreadContext.put("ROUTINGKEY", "ServerLogs"); //pass this routing key to log4j2 xmlfile
		server=getAppiumServerDefault(); // call the method
		server.start();
		server.clearOutPutStreams();
		utils.log().info("Appium server started");// will go in ServerLogs/server.log
	}
	
	
	@AfterSuite
public void afterSuite() {
		
		
		server.stop();
		utils.log().info("Appium server stopped"); //will go in ServerLogs/server.log
	}
	public AppiumDriverLocalService getAppiumServerDefault() {
		
		// build a new Appium service and writes Appium server logs in the file specified here
		return AppiumDriverLocalService.buildService(new AppiumServiceBuilder().withLogFile(new File("ServerLogs/server.log")));
	}
	
	@Parameters({"udid","emulator","platformName","platformVersion","deviceName","systemPort","chromeDriverPort"})
	@BeforeTest
	public void beforeTest(String udid,String emulator,String platformName,String platformVersion, String deviceName,String systemPort,String chromeDriverPort) throws Exception {
	//All global variable initializations should happen here
		
		
		FileInputStream fis=null;
		 FileInputStream fis2=null;
	setPlatform(platformName);
	setTime(utils.dateTime());
	setDeviceName(deviceName);
	URL url;
	String dir;
	AppiumDriver driver;
	Properties props=new Properties(); // will be assigned to the thread local global variable
	
	
	String strFile="logs"+File.separator+platformName+"_"+deviceName; //will be different for each thread -for log4j2
	
	
	File usrDir=new File(strFile);
	
	
	if(!usrDir.exists()) {
		usrDir.mkdirs();
	}
ThreadContext.put("ROUTINGKEY", strFile); //routing key in the log4j2 xml file is set here - different for each thread
	
		try {
			
			dir=System.getProperty("user.dir");
			fis=new FileInputStream(new File(dir+File.separator+"src"+File.separator+"main"+File.separator+"java"+File.separator+"com"+File.separator+"qa"+File.separator+"resources"+File.separator+"config.properties"));
			
			props=new Properties();
			props.load(fis);
			setProp(props);
			fis2=new FileInputStream(new File(dir+"/src/main/java/com/qa/data/strings.xml"));
			utils=new TestUtils();
			setStrings(utils.parseStringXML(fis2));
			
			
			DesiredCapabilities cap=new DesiredCapabilities(); 
			
			
			cap.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
			//cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion); //optional capability
			cap.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
			
			
			
			if(platformName.contentEquals("Android")) {
			
			cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, props.getProperty("UIAutomator2"));
			cap.setCapability(MobileCapabilityType.UDID, udid);
			
			String AndrAppURL=dir+File.separator+props.getProperty("appLocation"); 
			cap.setCapability(MobileCapabilityType.APP, AndrAppURL); // if you want to install and launch a new app
			cap.setCapability("autoGrantPermissions", true);
			//cap.setCapability(MobileCapabilityType.BROWSER_NAME, "Chrome");
			//cap.setCapability("chromedriverExecutable", "C:\\chromedriver-mobile_browser\\chromedriver.exe");
			//cap.setCapability("newCommandTimeout", 900); //how long 
			cap.setCapability("appPackage", props.getProperty("androidAppPackage"));
			cap.setCapability("appActivity", props.getProperty("androidAppActivity"));
			cap.setCapability("autoAcceptAlerts", "true"); // to accept all alerts
			if(emulator.equalsIgnoreCase("true")) { //if the emulator is true - test will run in the emulator
				cap.setCapability("avd", "Android_Pixel4");
			cap.setCapability("avdLaunchTimeout", "300000");
			//url=new URL(props.getProperty("appiumURL")+"4724/wd/hub");
			}
			else {
				
			//	url=new URL(props.getProperty("appiumURL")+"4723/wd/hub");
			}
			cap.setCapability("systemPort", systemPort);
			cap.setCapability("chromeDriverPort", chromeDriverPort);
			cap.setCapability("unlockType", "pin");
			cap.setCapability("unlockKey", "74159");
			url=new URL(props.getProperty("appiumURL")+"4723/wd/hub");
			 
			
			 driver=new AndroidDriver(url,cap);
			// String sessionID=driver.getSessionId().toString();
			}	
			
			
			else if(platformName.contentEquals("iOS")) {
				
				
				cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, props.getProperty("iOSAutomationName"));
				//cap.setCapability(MobileCapabilityType.UDID, "50584a424e443498");
				
				String iOSAppURL=dir+File.separator+props.getProperty("iOSAppLocation"); 
				cap.setCapability(MobileCapabilityType.APP, iOSAppURL); // if you want to install and launch a new app
				cap.setCapability("autoGrantPermissions", true);
	
				cap.setCapability("unlockType", "pin");
				cap.setCapability("unlockKey", "74159");
				
				url=new URL(props.getProperty("appiumURL"));
				
				 driver=new IOSDriver(url,cap);
				
				
			}
			
			else {
				
				throw new Exception("Invalid platform "+ platformName);
			}
			
			
			setDriver(driver);
			
			
		}
		
		
		
		
		
		
		catch(Exception e) {
			
			e.printStackTrace();
			
		}
		
		finally {
			
			fis.close();
			fis2.close();
			
		}
		
		
		
		
	}
	
	

	
	public void waitForVisibility(MobileElement e) {
		
		WebDriverWait wait=new WebDriverWait(driver.get(),TestUtils.WAIT);
		wait.until(ExpectedConditions.visibilityOf(e));
		
	}
	
	public void click(MobileElement e, String msg)  {
		
		waitForVisibility(e);
		utils.log().info(msg);
		ExtentReport.getTest().log(Status.INFO,msg); //make sure extent report start test method is executed before this
		//if not null pointer exception will occur
		e.click();
	}
	
	public void sendKeys(MobileElement e, String keys,String msg) {
		
		utils.log().info(msg);
		
		waitForVisibility(e);
		ExtentReport.getTest().log(Status.INFO,msg);
		e.sendKeys(keys);
		
	}
	
	public String getAttribute(MobileElement e, String attribute) {
		
		waitForVisibility(e);
		return e.getAttribute(attribute);
		
	}
	
	public String getText(MobileElement e, String msg) {// to get text in Android you need to use text and in iOS the label
		
		String txt = null;
		if(getPlatform().contentEquals("Android")) {
			
			txt= getAttribute(e,"text");
			
			
			
		}
		
		
		else if(getPlatform().contentEquals("iOS")) {
			
			txt= getAttribute(e,"label");
			
			
			
		}
		ExtentReport.getTest().log(Status.INFO,msg);
			utils.log().info(msg+ txt);
			return txt;
		
	}
	
	public void clear(MobileElement e) {
		
	waitForVisibility(e);
	e.clear();
	
	}
	
	
	public MobileElement scrollToElement(String element) {	  
		return (MobileElement) ((FindsByAndroidUIAutomator) driver.get()).findElementByAndroidUIAutomator(
				"new UiScrollable(new UiSelector()" + ".scrollable(true)).scrollIntoView("
						+ "new UiSelector().text("+element+"));");
  }
	
	public void iOSScrollToElement() {
		  RemoteWebElement element = (RemoteWebElement)getDriver().findElement(By.name("test-ADD TO CART"));
		  String elementID = element.getId();
		  HashMap<String, String> scrollObject = new HashMap<String, String>();
		  scrollObject.put("element", elementID);
//		  scrollObject.put("direction", "down");
//		  scrollObject.put("predicateString", "label == 'ADD TO CART'");
//		  scrollObject.put("name", "test-ADD TO CART");
		  scrollObject.put("toVisible", "sdfnjksdnfkld");
		  getDriver().executeScript("mobile:scroll", scrollObject);
	  }
	
	public void closeApp() {
		
		
		((InteractsWithApps)getDriver()).closeApp();
	}

	
	
	public void launchApp() {
		
		((InteractsWithApps)getDriver()).launchApp();
		
	}
	
	@AfterTest
	public void afterTest() {
		
		
		getDriver().quit();
		
	}
	
	
	
	
	
	
	
}
