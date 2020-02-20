package org.tfa.tgl.tests;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.tfa.framework.core.BaseTestMethods;
import org.tfa.tgl.pages.AssetsAndLiabilitiesSection;
import org.tfa.tgl.pages.LoginPageTgl;
import org.tfa.tgl.utilities.web.TGLConstants;

public class AssetsAndLiabilitiesTest extends BaseTestMethods{
	
	LoginPageTgl loginpage;
	Logger log=Logger.getLogger("rootLogger");
	SoftAssert soft = new SoftAssert();
	
	/*@Desc: This test verifies Assets and Liabilities section labels
	 *@Parameters: Login credential with admin role 
	 *
	 * 
	 *@Author: Nitin Sharma 
	 */
	@Test
	public void TGL102ValidateAssetsAndLiabilitiesSection() throws Exception
	{	
		AssetsAndLiabilitiesSection assetsliabilities= new AssetsAndLiabilitiesSection();

		
		boolean result;
		try{			
			loginpage = new LoginPageTgl();
			
			//Below assert ensures success login
			result=loginpage.enterLoginInfo();		
			soft.assertTrue(result, "EnterloginInfo method failed");
			
			//Below assert ensures searched record as assets and libilities section showing as expected
			soft.assertTrue(assetsliabilities.verifyAssetAndLiabilitySection(), "verifyAssetAndLiability method failed");								
		
		}catch(Exception e){
			
			soft.fail();	
			log.error(e);
			//System.out.println("Exception occured:"+e);
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
