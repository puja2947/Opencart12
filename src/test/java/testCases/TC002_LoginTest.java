package testCases;

import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

//this is till step5
public class TC002_LoginTest extends BaseClass{

	//add group tag step 7
	
	@Test(groups={"Sanity","Master"})
	public void verify_login()
	{
	
		logger.info("****Starting TC_002LoginTest******");
		try
		{
		//create obj for HomePage
		
		HomePage hp=new HomePage(driver); //this driver is coming from BaseClass
		logger.info("Clicked on My Account");
		hp.clickMyAccount();
		logger.info("Clicked on Login");
		hp.clickLogin();
				
		//create obj for LoginPage
		
		LoginPage lp=new LoginPage(driver);
		
		lp.setEmail(p.getProperty("email"));   //pass static data as this is for validating login using
		                      //valid email id nd pwd, so pass value from property file
		lp.setPassword(p.getProperty("password"));
		lp.clickLogin();
		
		//create obj of MyAccountPage
		
		MyAccountPage acc=new MyAccountPage(driver);
			boolean target_page= acc.isMyAcountPageExists()	;
			
			Assert.assertEquals(target_page, true);
			//Assert.assertTrue(target_page);
		}
		catch(Exception e)
		{
			Assert.fail();
		}
			logger.info("****Finished TC_002LoginTest******");
	}
	
}

