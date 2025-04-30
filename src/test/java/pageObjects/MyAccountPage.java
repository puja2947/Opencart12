package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyAccountPage extends BasePage {

	public MyAccountPage(WebDriver driver)
	{
		super(driver);
	}
	
	@FindBy( xpath="//h2[normalize-space()='My Account']")   //MyAcount Page Heading
	WebElement msgHeading;
	
	//adding logout for datadriven testing(step 6)
	@FindBy( xpath="//a[@class='list-group-item'][normalize-space()='Logout']")   
	WebElement btnLogout;
	
	//we do not do validation in page object class
	
	public boolean isMyAcountPageExists()
	{
		try
		{
			return(msgHeading.isDisplayed());
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
	public void clickLogout()
	{
		btnLogout.click();
	}
}
