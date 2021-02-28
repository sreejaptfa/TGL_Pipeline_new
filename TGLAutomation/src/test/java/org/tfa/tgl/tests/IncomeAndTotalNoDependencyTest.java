package org.tfa.tgl.tests;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.tfa.framework.core.BaseTestMethods;
import org.tfa.tgl.pages.IncomeAndTotalNoDependencySection;
import org.tfa.tgl.pages.common.LoginPageTgl;
import org.tfa.tgl.utilities.web.TGLConstants;
import org.tfa.tgl.utilities.web.TGLWebUtil;

public class IncomeAndTotalNoDependencyTest extends BaseTestMethods {
	
	private TGLWebUtil webUtil = TGLWebUtil.getObject();
	LoginPageTgl loginpage;
	Logger log=Logger.getLogger("rootLogger");
	SoftAssert soft = new SoftAssert();
	
	/*@Desc: This test verifies No Of Dependents section and relevant error messages 
	 *@Parameters: Login credential with admin role 
	 *
	 * 
	 *@Author: Nitin Sharma 
	 */
	@Test
	public void tgl109ValidateIncomeAndDependency() throws Exception	{	
		IncomeAndTotalNoDependencySection income = new IncomeAndTotalNoDependencySection();
		boolean result;
		try{			
			LoginPageTgl loginPage = webUtil.openLoginPage();
				
			//Below assert ensures success login
			result=loginPage.enterLoginInfo();		
			soft.assertTrue(result, "EnterloginInfo method failed");
			
			//Below assert ensures right error messages appear for No Of Dependents field section showing as expected
			soft.assertTrue(income.verifyIncomeAndTotalNodependencySection(), "verifyIncomeAndTotalNodependencySection  method failed");								
		
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