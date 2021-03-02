package org.tfa.tgl.tests;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.tfa.framework.core.BaseTestMethods;
import org.tfa.tgl.pages.common.LoginPageTgl;
import org.tfa.tgl.utilities.web.TGLConstants;
import org.tfa.tgl.utilities.web.TGLWebUtil;

public class ValidateTglLoginFunctionality extends BaseTestMethods{
	
	Logger log=Logger.getLogger("rootLogger");
	private TGLWebUtil webUtil = TGLWebUtil.getObject();
	
	@Test
	public void tgl100TglLoginTest() throws Exception	{	
		
		String noAdminToleUserName = testDataMap.get("InvalidUserName");
		String validPassword = testDataMap.get("Login_Password");
		String validUserName = testDataMap.get("Login_UserName");
		String invalidPassword= testDataMap.get("Login_UserName");
		String wrongUserName=testDataMap.get("WrongUserName");
		
		// Step 1 - Open TGL
		LoginPageTgl loginPage = webUtil.openLoginPage();

		// Step 2 - User active but no Admin role | correct user name correct password
		Assert.assertTrue(loginPage.verifyInvalidLogin(noAdminToleUserName, validPassword), "Verify InvalidLogin method failed");
		
		// Step 3 - Right User name wrong password
		Assert.assertTrue(loginPage.verifyInvalidLogin(validUserName, invalidPassword), "Verify InvalidLogin method failed");
		
		// Step 4 - Wrong User name right password
		Assert.assertTrue(loginPage.verifyInvalidLogin(wrongUserName, validPassword), "Verify InvalidLogin method failed");
		
		// Step 5 - Right User name right password
		Assert.assertTrue(loginPage.enterLoginInfo(), "Verify Home Page landed");
						
	}
	
	@Override
	public TGLConstants getConstants()	{
		return new TGLConstants();
	}

}
