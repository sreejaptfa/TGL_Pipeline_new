package org.tfa.tgl.tests;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.tfa.framework.core.BaseTestMethods;
import org.tfa.tgl.pages.LoginPageTgl;
import org.tfa.tgl.utilities.web.TGLConstants;

public class TglLoginTest extends BaseTestMethods{
	
	LoginPageTgl loginpage;
	Logger log=Logger.getLogger("rootLogger");
	
	@Test
	public void tgl100ValidateTglLogin() throws Exception
	{	
		boolean result;
		try{			
			loginpage = new LoginPageTgl();
			result=loginpage.verifyInvalidLogin();
			Assert.assertTrue(result, "verifyInvalidLogin method failed");
			Assert.assertTrue(loginpage.enterLoginInfo(), "enterLoginInfo method failed");								
		
		}catch(Exception e){
			Assert.fail();	
		}
						
	}
	
	@Override
	public TGLConstants getConstants()	{
		return new TGLConstants();
	}

}
