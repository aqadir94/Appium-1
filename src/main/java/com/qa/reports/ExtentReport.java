package com.qa.reports;

import java.util.HashMap;
import java.util.Map;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.utils.TestUtils;

public class ExtentReport {
		TestUtils utils=new TestUtils();
     static ExtentReports extent;
    final static String file="Extent.html";
    static Map<Integer, ExtentTest> extentTestMap = new HashMap(); //ExtenTest objects of different threads will go here
    
    
    //create the test and add the configurations
    public synchronized static ExtentReports getReporter() {
        if (extent == null) {
            ExtentSparkReporter spark= new ExtentSparkReporter(file);
            spark.config().setDocumentTitle("Appium Framework");
            spark.config().setReportName("Functional tests");
            spark.config().setTheme(Theme.DARK);
            
            extent=new ExtentReports();
            extent.attachReporter(spark);
          
        }
        
        return extent;

}
    
    
    //gets the ExtentTest object for a particular thread
    public static synchronized ExtentTest getTest() {
    	System.out.println("extent get");
        return (ExtentTest) extentTestMap.get((int) (long) (Thread.currentThread().getId()));
       
    }



    //starts the test and stores the ExtentTest Object in a hashmap
    public static synchronized ExtentTest startTest(String testName, String desc) {
    	System.out.println("extent start");
        ExtentTest test = getReporter().createTest(testName, desc);
        extentTestMap.put((int) (long) (Thread.currentThread().getId()), test);

        return test;
    }
    
    
    
}
