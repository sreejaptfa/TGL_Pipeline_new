package org.tfa.tgl.pages;


import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.tfa.framework.core.WebDriverUtil;
import org.tfa.framework.utilities.testdata.TestData;


public class LoginPageTgl 
{

	private WebDriverUtil webUtil;
	private TestData data;
	private boolean flag;
	Logger log;
	WebDriverWait explicitwait;
	
    public LoginPageTgl()
    {
    	
    	webUtil=WebDriverUtil.getObject();
    	data=TestData.getObject();
    	flag=false;
    	log=Logger.getLogger("rootLogger");
    	// TestCase - LoginPage - Step1
    	webUtil.openURL((String) data.getEnvironmentDataMap().get("ApplicationURL"));
    	explicitwait= new WebDriverWait(webUtil.getDriver(), 30);
    	    	
    } 
	public boolean enterLoginInfo() throws Exception
	{
		webUtil.holdOn(10);
		By homepagecontrol=By.cssSelector(".tfa-button-text");
		// TestCase - LoginPage - Step - 2
		webUtil.setTextBoxValueTestData("LoginTgl_username_ED", "Login_UserName");
		webUtil.setTextBoxValueTestData("LoginTgl_password_ED", "Login_Password");
		webUtil.click("LoginTgl_Signin_btn");
		
		explicitwait.until(ExpectedConditions.visibilityOfElementLocated(homepagecontrol));
		if (webUtil.getDriver().getCurrentUrl().contains("tgl"))
		{
			flag= true;
			log.info("Home Page landed");
		
		}
		else
		{
			flag= false;
			log.info("Home Page not found!");
			return flag;
			
		}		
		return flag;
	}
	
	public boolean verifyInvalidLogin(){
		boolean flag=false;
		
		webUtil.waitForBrowserToLoadCompletely();
		By signinbutton=By.xpath("//button[@class='btn btn-primary']");
		//explicitwait=new WebDriverWait(webUtil.getDriver(), 30);
		
		//explicitwwait.until(ExpectedConditions.visi);
		// User active but no admin role | correct username correct password
		// TestCase - LoginPage - Step4
		webUtil.setTextBoxValue("LoginTgl_username_ED", "nisharma");
		webUtil.setTextBoxValue("LoginTgl_password_ED", "password");
		webUtil.click("LoginTgl_Signin_btn");
		webUtil.waitUntilElementVisible("Tgl_invalidloginalert_lbl", 30);
		if(webUtil.getElement("Tgl_invalidloginalert_lbl").getText().contains("Invalid username or password."))
			flag=true;
		else
			return flag=false;
		

		//explicitwwait.until(ExpectedConditions.invisibilityOfElementLocated(signinbutton));
		
		// Right username wrong password
		// TestCase - LoginPage - Step3
		
		webUtil.waitUntilElementVisible("LoginTgl_Signin_btn", 30);
		
		webUtil.setTextBoxValueTestData("LoginTgl_username_ED", "Login_UserName");
		webUtil.setTextBoxValue("LoginTgl_password_ED", "wrongpassword");
		webUtil.click("LoginTgl_Signin_btn");
		webUtil.waitUntilElementVisible("Tgl_invalidloginalert_lbl", 30);
		if(webUtil.getElement("Tgl_invalidloginalert_lbl").getText().contains("Invalid username or password."))
			flag=true;
		else
			return flag=false;
		
		//explicitwwait.until(ExpectedConditions.invisibilityOfElementLocated(signinbutton));
		
		// Right username wrong password
		webUtil.holdOn(5);
		webUtil.waitUntilElementVisible("LoginTgl_Signin_btn", 30);
		// TestCase - LoginPage - Step3
		webUtil.setTextBoxValue("LoginTgl_username_ED", "wronguser");
		webUtil.setTextBoxValue("LoginTgl_password_ED", "password");
		webUtil.click("LoginTgl_Signin_btn");
		webUtil.waitUntilElementVisible("Tgl_invalidloginalert_lbl", 30);		
		if(webUtil.getElement("Tgl_invalidloginalert_lbl").getText().contains("Invalid username or password."))
				flag=true;
		else
				return flag=false;
		
		
		return flag;
		
	}
	
	
}
