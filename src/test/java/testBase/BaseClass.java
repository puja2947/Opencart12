package testBase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;



public class BaseClass {
	
	public static WebDriver driver;  //if not public then in test class it will not be accessible
	public Logger logger;    //log4j variable
	public Properties p;
	
	//add group annotation step 7
	@SuppressWarnings("deprecation")
	@BeforeClass(groups= {"Sanity","Regression","Master"})
	@Parameters({"os","browser"})
	public void setUp(String os, String br) throws IOException {
			
		//loading config.properties file
		
		FileReader file=new FileReader("./src//test//resources//config.properties");   //.//represents current project loaction
		p=new Properties();
		p.load(file);  //loading the properties file
		
		
		//load log4j2 file
		logger=LogManager.getLogger(this.getClass()); //this.getClass() will get the class name dynamically
		//at runtime, log4j2 file will save in variable
		
		//grid env execution setup(step 10)
		
		if(p.getProperty("execution_env").equalsIgnoreCase("remote"))//if env is remote
		{
			DesiredCapabilities capabilities=new DesiredCapabilities();
			
			//os decision
			if(os.equalsIgnoreCase("windows"))
			capabilities.setPlatform(Platform.WIN11); //capabilities.setPlatform(Platform.MAC);
			else if(os.equalsIgnoreCase("linux"))
				capabilities.setPlatform(Platform.LINUX); 
			else if(os.equalsIgnoreCase("mac"))
				capabilities.setPlatform(Platform.MAC); 
			else
				{System.out.println("No Matching os");
				return;
				}
			
			//browser  decision
			switch(br.toLowerCase())  
			{
			
			case "chrome": capabilities.setBrowserName("chrome");break;
			case "edge": capabilities.setBrowserName("MicrosoftEdge"); break;
			case "firefox": capabilities.setBrowserName("firefox"); break;
			default: System.out.println("no matching browser name...");return;
			}
			driver= new RemoteWebDriver(new URL("http://192.168.29.204:4444/wd/hub"),capabilities);
			

		}//step 10 changes end here
		
		if(p.getProperty("execution_env").equalsIgnoreCase("local"))//if env is local
		{
		
		switch(br.toLowerCase())  //cross browser , parallel testing
		{
		//now we cannot run test case, have to run xml file
		case "chrome": driver=new ChromeDriver();break;
		case "edge": driver=new EdgeDriver(); break;
		default: System.out.println("Invalid browser name...");return;
		}
		}
		
			//driver=new ChromeDriver();
			driver.manage().deleteAllCookies(); // delete all the cookies
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			driver.get(p.getProperty("appURL1"));   //reading url from properties files
			driver.manage().window().maximize();
			//driver.findElement(By.xpath("//span[normalize-space()='View Store Front']")).click();
			
		
	}
	//add group annotation step7
		
		@AfterClass(groups={"Sanity","Regression","Master"})
		public void teardown()
		{
			driver.quit();
			//driver.close();
		}
		
		
		//method to generate random string
		public String randomString()
		{
		String gen_string=	  RandomStringUtils.randomAlphabetic(5);
		return gen_string;
		}
		
		
		public String randomNumber()
		{
		String gen_Number=	  RandomStringUtils.randomNumeric(10);
		return gen_Number;
		}
		

		public String randomAlphanumeric()
		{
		String x= RandomStringUtils.randomAlphabetic(3);
		String y= RandomStringUtils.randomNumeric(3);
		return(x+y);
		//return (x+"@#"+y);
		}	

		
		//can be kepth in testcases package or create another package testBase and put it there
		// and import the package in testcase classes to use
		
		//add method for capturing screenshot on test failure step8
		
		public String captureScreen(String tname) throws IOException
		{
			String timestamp= new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		TakesScreenshot  takescreenshot= (TakesScreenshot)driver;
		File sourcefile = takescreenshot.getScreenshotAs(OutputType.FILE);
		
		String targetfilepath="/Users/amriteshkumar/eclipse-workspace/seleniumworkspace/Opencart12/screenshots"+tname+ "_"+timestamp+".png";
		File targetfile=new File(targetfilepath);
		sourcefile.renameTo(targetfile);
		return targetfilepath;
		}
		
}
