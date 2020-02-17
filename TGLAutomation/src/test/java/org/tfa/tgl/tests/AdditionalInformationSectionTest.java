package org.tfa.tgl.tests;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.tfa.framework.core.BaseTestMethods;
import org.tfa.tgl.pages.AdditionalInformationSection;
import org.tfa.tgl.pages.AssetsAndLiabilitiesSection;
import org.tfa.tgl.pages.LoginPageTgl;
import org.tfa.tgl.utilities.web.TGLConstants;

public class AdditionalInformationSectionTest extends BaseTestMethods  {

	LoginPageTgl loginpage;
	Logger log=Logger.getLogger("rootLogger");
	SoftAssert soft = new SoftAssert();
		
	@Test
	public void TGL103ValidateAdditionalInformationSection() throws Exception
	{	
		AdditionalInformationSection additionalinformation= new AdditionalInformationSection();

		
		boolean result;
		try{			
			loginpage = new LoginPageTgl();
			result=loginpage.enterLoginInfo();
			soft.assertTrue(result, "EnterloginInfo method failed");			
			soft.assertTrue(additionalinformation.verifyAdditionalInformationSection(), "verifyAdditionalInformation method failed");								
		
		}catch(Exception e){
			
			soft.fail();	
			log.error(e);
			System.out.println("Exception occured:"+e);
		}finally{
			
			soft.assertAll();
			log.info(this.getClass().getEnclosingMethod() + " Test Execution Completion - Success!");
		}
	}
	
	@Override
	public TGLConstants getConstants()
	{
		return new TGLConstants();
	}
}
