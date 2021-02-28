package org.tfa.tgl.tests;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.tfa.framework.core.BaseTestMethods;
import org.tfa.tgl.pages.LeftNavSection;
import org.tfa.tgl.pages.common.LoginPageTgl;
import org.tfa.tgl.utilities.web.TGLConstants;
import org.tfa.tgl.utilities.web.TGLWebUtil;

public class LeftNavTest extends BaseTestMethods{
	LoginPageTgl loginpage;
	Logger log=Logger.getLogger("rootLogger");
	private TGLWebUtil webUtil = TGLWebUtil.getObject();

	/*@Desc: This test verifies Left Nav section (Top Section), refer to testcase for more details - LeftNav - Automatable
	 *@Parameters: Login credential with admin role 
	 *@
	 *@Author: Nitin Sharma 
	 */
	@Test
	public void tgl107ValidateTopNavSection() throws Exception	{	
		LeftNavSection nav= new LeftNavSection();	
		boolean result;
		try{			
			LoginPageTgl loginPage = webUtil.openLoginPage();
			
			// Below Assert ensures success login
			result=loginPage.enterLoginInfo();
			Assert.assertTrue(result, "EnterloginInfo method failed");			
			// Below Assert ensures <to be added>
			Assert.assertTrue(nav.verifyTopNavSection(), "verifyAdditionalInformation method failed");								
		}catch(Exception e){
			Assert.fail();	
			log.error(e);
		}finally{
			log.info(this.getClass().getEnclosingMethod() + " Test Execution Completion - Success!");
		}
	}
	@Override
	public TGLConstants getConstants(){
		return new TGLConstants();
	}
}
