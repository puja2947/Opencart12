package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

//Only contains constructor and constructor name should be same as class name
//and this basepage class will be extended to all other page classes
//so this is parent of all pageobject classes
//as constructor is same for all pageobject class

public class BasePage {
	
	WebDriver driver;
	
	public BasePage(WebDriver driver)
	{
		this.driver=driver;
	PageFactory.initElements(driver, this);	
	
	}

	

}
