package com.qa.listeners;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.qa.reports.ExtentReport;
import com.qa.resources.BaseTest;
import com.utils.TestUtils;

import io.appium.java_client.AppiumDriver;

public class TestListener implements ITestListener{
	TestUtils utils=new TestUtils();
	@Override
	public void onTestFailure(ITestResult result) {
	
		if(result.getThrowable()!=null) /*check if there is any output */ {
		
			
	//print the failure trace to the console		
	 StringWriter sw =new StringWriter();
		 PrintWriter pw=new PrintWriter(sw);
		  result.getThrowable().printStackTrace(pw);
		
		 utils.log(sw.toString());
		 
		 BaseTest b1=new BaseTest();
		 AppiumDriver driver=b1.getDriver();
		 
		 Map<String, String> params=new  HashMap<String,String>();
		 params= result.getTestContext().getCurrentXmlTest().getAllParameters(); // get all parameters of TestNGfile
		 
		 String imagePath="Screenshots"+File.separator+params.get("platformName")+"_"+params.get("platformVersion")
		 +"_"+params.get("deviceName") +File.separator+b1.getTime()+File.separator+result.getTestClass().
		 getRealClass().getSimpleName()+File.separator+File.separator+result.getName()+".png";
		 
		
		String completeImagePath=System.getProperty("user.dir")+File.separator+imagePath;
		 
	File scr=	driver.getScreenshotAs(OutputType.FILE);
	
	//below is for extent report screenshot embedding (in the report itself)
	
	byte[] encoded = null;
	try {
		encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(scr));
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	
	
	
	
		 try {
			FileUtils.copyFile(scr,new File(imagePath));
			Reporter.log("This is the sample screenshot");
			//add text to testNG report
			Reporter.log("<a href='"+ completeImagePath + "'> <img src='"+ completeImagePath + "' height='400' width='400'/> </a>");
			//add screenshot to testNG report
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 
		 //fail the ExtentTest test and embed the screenshot to the report
		 ExtentReport.getTest().fail("Test Failed",
					MediaEntityBuilder.createScreenCaptureFromBase64String(new String(encoded, StandardCharsets.US_ASCII)).build());
			ExtentReport.getTest().fail(result.getThrowable());
		 
		 
		 
		  
	}
		
		
	}
	@Override
	public void onTestStart(ITestResult result) { // runs after @BeforeTest but before the @Test
		BaseTest b1=new BaseTest();
		
		//start the test from extent test perspective - pass method name and description
		//this method has to run before ExtentReport.getTest() null pointer exception otherwise
		ExtentReport.startTest(result.getName(), result.getMethod().getDescription())
		.assignCategory(b1.getPlatform()+"_"+b1.getDeviceName()).assignAuthor("Abdul");
		
	}
	@Override
	public void onTestSuccess(ITestResult result) {
		ExtentReport.getTest().log(Status.PASS, "Test pass");
		
	}
	@Override
	public void onTestSkipped(ITestResult result) {
		ExtentReport.getTest().log(Status.SKIP, "Test skipped");
	}
	@Override
	public void onFinish(ITestContext context) {
		ExtentReport.getReporter().flush();
	}
	

}
