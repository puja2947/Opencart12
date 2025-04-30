package testCases;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.openqa.selenium.WebDriver;  //click on organize imports to remove unwanted ones
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass{

	//make all test method public so that can be accessed through out the package
	//WebDriver driver;
	
	/*@BeforeClass //moved to BaseClass
	public void setUp() throws InterruptedException {
			
			driver=new ChromeDriver();
			driver.manage().deleteAllCookies(); // delete all the cookies
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			driver.get("https://tutorialsninja.com/demo/");
			driver.manage().window().maximize();
			//driver.findElement(By.xpath("//span[normalize-space()='View Store Front']")).click();
			Thread.sleep(5000);
		}
		
		@AfterClass
		public void teardown()
		{
			driver.quit();
			//driver.close();
		}*/
		
	//add group tag step7
	
	
	@Test(groups={"Regression","Master"})
	public void verify_Account_Registration()
	{
		//generate log4j2
		try
		{
		logger.info("*******Starting TC001_AccountRegistrationTest*******");
		
		HomePage hp=new HomePage(driver);
		hp.clickMyAccount();
		logger.info("Clicked on My Account");
		
		hp.clickRegister();
		logger.info("Clicked on Register");
		
		AccountRegistrationPage regpage= new AccountRegistrationPage(driver);
		//regpage.setFirstName("amritesh");
		
		logger.info("Providing Customer details...");
		
		//generate name also randomly
		
		regpage.setFirstName(randomString().toUpperCase());
		
		//regpage.setLastName("kumar");
		
		regpage.setLastName(randomString().toUpperCase());
		
	//	regpage.setEmail("pujasoni0@gmail.com");
		
		regpage.setEmail(randomString()+"@gmail.com");  //randomly generated emailid
		                                                //by calling method randomString()
		//regpage.setTelephone("12345677");
		
		regpage.setTelephone(randomNumber());
		
		//regpage.setPassword("puja2947");
		String pwd=randomAlphanumeric();
		regpage.setPassword(pwd);
		
		//regpage.setConfirmPassword("puja2947");
		regpage.setConfirmPassword(pwd);
		regpage.setPrivacyPolicy();
		regpage.clickContinue();
		
		//validation
		
		logger.info("Validating expected message...");
		String confirmation_msg=regpage.getConfirmationMsg();
		if(confirmation_msg.equals("Your Account Has Been Created!"))
		{
			AssertJUnit.assertTrue(true);
		//Assert.assertEquals(confirmation_msg, "Your Account Has Been Created!");
		
	}
		else
		{
			logger.error("test failed...");
			logger.debug("Debug logs...");
			AssertJUnit.assertTrue(false);
		}
		}
	
	catch(Exception e)
	{
		
		AssertJUnit.fail();
	}
	//method to generate random string and number 
/*	public String randomString()  //moved to BaseClass
	{
	String gen_string=	  RandomStringUtils.randomAlphabetic(5);
	return gen_string;
	}
	
	
	public String randomNumber()    //moved to BaseClass
	{
	String gen_Number=	  RandomStringUtils.randomNumeric(10);
	return gen_Number;
	}
	

	public String randomAlphanumeric()   //moved to BaseClass
	{
	String x= RandomStringUtils.randomAlphabetic(3);
	String y= RandomStringUtils.randomNumeric(3);
	return(x+y);
	//return (x+"@#"+y);
	}*/
	
	//now the methods which are reuired for multiple test cases we will seperate them in another
	//class called base class under test class itself which will contain the common methods like
	//setup , teardown, randomnumber etc
	//so now the baseClass becomes the parent class of all test case classes, and test class will only 
	//contain test methods
		logger.info("*******Finished TC001_AccountRegistrationTest*******");
	}
}
