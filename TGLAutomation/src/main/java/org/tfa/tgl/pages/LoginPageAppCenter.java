package org.tfa.tgl.pages;

import java.util.Map;
import org.apache.log4j.Logger;
import org.tfa.framework.core.WebDriverUtil;
import org.tfa.framework.utilities.testdata.TestData;




public class LoginPageAppCenter 
{

	private WebDriverUtil webUtil=WebDriverUtil.getObject();
	
	private TestData data=TestData.getObject();
	
	private static Logger logger = Logger.getLogger(WebDriverUtil.class);
	
	static int i=0,counter=0;
	
	Logger log = Logger.getLogger("rootLogger");

	
    public void openLoginPage()
    {
    	webUtil.getDriver().navigate().to("https://qamerlin.teachforamerica.org/applicant-center");
    }
	public void enterLoginInfo() throws Exception
	{
		
		webUtil.setTextBoxValueTestData("Login_UserName_ED", "Login_UserNameOnline");			
		webUtil.setTextBoxValueTestData("Login_Password_ED", "Login_PasswordOnline");
		webUtil.click("Login_SignIn");
		webUtil.holdOn(4);
	}

	
}
