package org.tfa.tgl.tests;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.tfa.framework.core.BaseTestMethods;
import org.tfa.tgl.pages.AdditionalInformationSection;
import org.tfa.tgl.pages.LoginPageTgl;
import org.tfa.tgl.pages.TaxInformationSection;
import org.tfa.tgl.utilities.web.TGLConstants;

public class TaxInformationSectionTest extends BaseTestMethods{

	LoginPageTgl loginpage;
	Logger log=Logger.getLogger("rootLogger");
	
	@Test
	public void TGL104ValidateTaxlInformationSection() throws Exception
	{	
		TaxInformationSection taxinfosection= new TaxInformationSection();

		
		boolean result;
		try{			
			loginpage = new LoginPageTgl();
			
			// Below Assert ensures success login
			result=loginpage.enterLoginInfo();
			Assert.assertTrue(result, "EnterloginInfo method failed");	
			
			// Below Assert ensures that searched user has additional information section showing
			Assert.assertTrue(taxinfosection.verifyTaxInformationSection(), "verifyTaxInformation method failed");								
		
		}catch(Exception e){
			
			Assert.fail();	
			log.error(e);
			//System.out.println("Exception occured:"+e);
		}finally{
			
			
			log.info(this.getClass().getEnclosingMethod() + " Test Execution Completion - Success!");
		}
	}
	
	@Override
	public TGLConstants getConstants()
	{
		return new TGLConstants();
	}
	
	
}
