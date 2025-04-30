package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

//data is valid--login is successful--test passed-- logout needed
//data is valid--login is failed--test failed-- logout not needed
//data is invalid--login is failed--test passed---logout not needed
//data is invalid--login is successful--test failed--- logout needed

public class TC003_LoginDDT extends BaseClass {
	//as dataprovider method is in other class so have to specify class as well
	
	//add group tag step 7
	
@Test(dataProvider="LoginData", dataProviderClass=DataProviders.class,groups="datadriven")
	public void verify_LoginDDT(String email , String pwd, String exp_res)
	{

	logger.info("****Starting TC_002LoginDDT******");
		
	try {
	HomePage hp=new HomePage(driver); //this driver is coming from BaseClass
	logger.info("Clicked on My Account");
	hp.clickMyAccount();
	logger.info("Clicked on Login");
	hp.clickLogin();
			
	//create obj for LoginPage
	
	LoginPage lp=new LoginPage(driver);
	
	lp.setEmail(email);   //pass static data as this is for validating login using
	                      //valid email id nd pwd, so pass value from property file
	lp.setPassword(pwd);
	lp.clickLogin();
	
	//create obj of MyAccountPage
	
	MyAccountPage acc=new MyAccountPage(driver);
		boolean target_page= acc.isMyAcountPageExists()	;
		
		if(exp_res.equalsIgnoreCase("valid"))
		{
			if(target_page==true)
			{
				Assert.assertTrue(true);
				acc.clickLogout();
			}
			else
			{
				Assert.assertFalse(true);
				
			}
		}
		if(exp_res.equalsIgnoreCase("invalid"))
		{
			if(target_page==true)
			{
				
				acc.clickLogout();
				Assert.assertTrue(false);
				
			}
			else
			{
				Assert.assertTrue(true);
				
			}
		}
	}
	catch(Exception e)
	{
		Assert.fail();
	}

		logger.info("****Finished TC_002LoginDDT******");
	}
	
}
