package org.tfa.tgl.tests;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.tfa.framework.core.BaseTestMethods;
import org.tfa.tgl.pages.AdditionalInformationSection;
import org.tfa.tgl.pages.LeftNavSection;
import org.tfa.tgl.pages.LoginPageTgl;
import org.tfa.tgl.utilities.web.TGLConstants;

public class LeftNavTest extends BaseTestMethods{
	LoginPageTgl loginpage;
	Logger log=Logger.getLogger("rootLogger");
	//LeftNavSection nav= new LeftNavSection();
	
	/*@Desc: This test verifies Left Nav section (Top Section), refer to testcase for more details - LeftNav - Automatable
	 *@Parameters: Login credential with admin role 
	 *@
	 *@Author: Nitin Sharma 
	 */
	@Test
	public void TGL107ValidateTopNavSection() throws Exception
	{	
		LeftNavSection nav= new LeftNavSection();	
		boolean result;
		try{			
			loginpage = new LoginPageTgl();		
			// Below Assert ensures success login
			result=loginpage.enterLoginInfo();
			Assert.assertTrue(result, "EnterloginInfo method failed");			
			// Below Assert ensures <to be added>
			Assert.assertTrue(nav.verifyTopNavSection(), "verifyAdditionalInformation method failed");								
		
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
