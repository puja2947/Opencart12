package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
// this is listener class
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;


public class ExtentReportManager implements ITestListener {

	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest test;
	String repName;
	
	public void onStart(ITestContext testContext) { //testContext is which test method got executed
		
	/*	SimpleDateFormat df=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
		Date dt=new Date();
		String Currentdatetimestamp=df.format(dt);*/
		
		String timestamp =new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()); //timestamp generated
		repName= "Test-Report-"+timestamp+".html"; //generating report name with timestamp to maintain history
		
	    sparkReporter = new ExtentSparkReporter("/Users/amriteshkumar/eclipse-workspace/seleniumworkspace/Opencart12/reports/"+repName); //specify the location of report
	  sparkReporter.config().setDocumentTitle("Opencart Automation Report");  
	  sparkReporter.config().setReportName("Opencart Funtional Testing");
	  sparkReporter.config().setTheme(Theme.DARK);   //set the background black, or STANDARD for white background
	
	extent =new ExtentReports();
	extent.attachReporter(sparkReporter);
	
	//static data
	extent.setSystemInfo("Application", "Opencart");
	extent.setSystemInfo("Module", "Admin");
	extent.setSystemInfo("Sub Module", "Customer");
	extent.setSystemInfo("User Name ", System.getProperty("user.name"));  // key and value are the parameter
	extent.setSystemInfo("Environment", "QA");   //these are hard coated, dynamic will see in framework
	
	//capturing from xml file
	String os=testContext.getCurrentXmlTest().getParameter("os"); //will capture operating system name 4m
	extent.setSystemInfo("Operating System", os);                //xml file
	
	
	String browser=testContext.getCurrentXmlTest().getParameter("browser"); //will capture browser
	extent.setSystemInfo(" Browser", browser);            //name from xml file
	
	List<String> includedGroups= testContext.getCurrentXmlTest().getIncludedGroups();
	if(!includedGroups.isEmpty())
	{
	extent.setSystemInfo("Groups", includedGroups.toString());	
	}
	}


	  public void onTestSuccess(ITestResult result) {
		  
		  test=extent.createTest(result.getName()); //create a now entry in the report
		  test.assignCategory(result.getMethod().getGroups());  //to display groups in report
		  test.log(Status.PASS,result.getName()+" got successfully executed");  //update status p/f/s
	  }


	  public void onTestFailure(ITestResult result) {
		  
		  test=extent.createTest(result.getName()); //create a new entry in the report
		  test.assignCategory(result.getMethod().getGroups());
		  test.log(Status.FAIL, result.getName()+" got Failed");  //update status p/f
		  test.log(Status.INFO, result.getThrowable().getMessage()); 
		  
		  //attach screenshot of report
	 try
		 {
		  String imgPath= new BaseClass().captureScreen(result.getName());
		  test.addScreenCaptureFromPath(imgPath);
		 } 
		 catch( Exception e1)
		 {
			e1.printStackTrace(); 
		 }
	  }

	  
	  public void onTestSkipped(ITestResult result) {
	    
		  test=extent.createTest(result.getName()); //create a new entry in the report
		  test.assignCategory(result.getMethod().getGroups());
		  test.log(Status.SKIP,result.getName()+" got skipped");  //update status p/f/s
		  test.log(Status.INFO, result.getThrowable().getMessage());
	  }
	  
	
	  public void onFinish(ITestContext context) {
		    
		  extent.flush();
			  
		  //to open the report automatically
		 String pathofExtentReport="/Users/amriteshkumar/eclipse-workspace/seleniumworkspace/Opencart12/reports/"+repName;
		 File extentReport= new File(pathofExtentReport);
		 try {
			 Desktop.getDesktop().browse(extentReport.toURI());
		 }
	catch(IOException e)	 
		 {
		e.printStackTrace();
		 }
	  }
	
}
