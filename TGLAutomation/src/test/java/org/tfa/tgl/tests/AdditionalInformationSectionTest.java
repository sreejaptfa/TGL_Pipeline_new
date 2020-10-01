package org.tfa.tgl.tests;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.tfa.framework.core.BaseTestMethods;
import org.tfa.tgl.pages.AdditionalInformationSection;
import org.tfa.tgl.pages.LoginPageTgl;
import org.tfa.tgl.utilities.web.TGLConstants;

public class AdditionalInformationSectionTest extends BaseTestMethods  {

	LoginPageTgl loginpage;
	Logger log=Logger.getLogger("rootLogger");
	SoftAssert soft = new SoftAssert();
	
	/*@Desc: This test verifies addition information section labels
	 *@Parameters: Login credential with admin role 
	 *@
	 *@Author: Nitin Sharma 
	 */
	@Test
	public void tgl103ValidateAdditionalInformationSection() throws Exception	{	
		AdditionalInformationSection additionalinformation= new AdditionalInformationSection();
		boolean result;
		try{			
			loginpage = new LoginPageTgl();
			
			// Below Assert ensures success login
			result=loginpage.enterLoginInfo();
			soft.assertTrue(result, "EnterloginInfo method failed");	
			
			// Below Assert ensures that searched user has additional information section showing
			soft.assertTrue(additionalinformation.verifyAdditionalInformationSection(), "verifyAdditionalInformation method failed");								
		}catch(Exception e){
			soft.fail();	
			log.error(e);
		}finally{
			soft.assertAll();
			log.info(this.getClass().getEnclosingMethod() + " Test Execution Completion - Success!");
		}
	}
	@Override
	public TGLConstants getConstants()	{
		return new TGLConstants();
	}
}
