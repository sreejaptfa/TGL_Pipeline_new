package org.tfa.tgl.tests;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.tfa.framework.core.BaseTestMethods;
import org.tfa.tgl.pages.LoginPageTgl;
import org.tfa.tgl.pages.TGLAppCenterIntergrationPoints;
import org.tfa.tgl.utilities.web.TGLConstants;

public class TGLAppCenterIntegrationPointTest extends BaseTestMethods{
	
	LoginPageTgl loginpage;
	Logger log=Logger.getLogger("rootLogger");
	
	/* @Description: Below Test Verifies File Upload, Applicant Notes 
	 * and Applicant Tax Return "Valid" Checkbox checked between AppCenter and TGL
	 * @Param: TGL Admin credentials and a valid applicant email and person id in TestData
	 * @Author: Nitin Sharma
	 */
	@Test(priority=1, enabled = false)
	public void TGL108ValidateIntegrationPoints(){
		LoginPageTgl loginpage = new LoginPageTgl();
		TGLAppCenterIntergrationPoints integrationpoints= new TGLAppCenterIntergrationPoints();
		try{		
		// TestCase - Integration Point - assets and liabilities - Step 1
		Assert.assertTrue(loginpage.enterLoginInfo(),"EnterLoginInfo method failed");
		// TestCase - Integration point - assets and liabilities - Step2 and Step 3
		Assert.assertTrue(integrationpoints.searchAndOpenApplicantDetails(), "searchAndOpenApplicantDetails method failed");
		// TestCase - Integration point - assets and liabilities - step 5 
		Assert.assertTrue(integrationpoints.uploadDocumentinAda(), "uploadDocumentinAda method failed");
		// TestCase - Integration point - assets and liabilities - step 6 
		Assert.assertTrue(integrationpoints.checkValidcheckBoxApplicantTaxReturn(), "checkValidcheckBoxApplicantTaxReturn failed");
		// TestCase - Integration point - assets and liabilities - step 7
		Assert.assertTrue(integrationpoints.verifyApplicantCenterDocumentsDetails(true), "verifyApplicantCenterDocumentsDetails falied");
		}
		catch(Exception e){			
			Assert.fail();
			log.info("TGL108ValidateIntegrationPoints test failed due to exception: "+e);
		}
	}	
	
	
	/* @Description: Below Test Verifies Applicant Notes removal
	 * and Applicant Tax Return "Valid" Checkbox UNCHECKED between AppCenter and TGL
	 * @Param: TGL Admin credentials and a valid applicant email and person id in TestData
	 * @Author: Nitin Sharma
	 */
	@Test(priority=2, enabled = false)
	public void TGL110ValidateIntegrationPoints(){
		LoginPageTgl loginpage = new LoginPageTgl();
		TGLAppCenterIntergrationPoints integrationpoints= new TGLAppCenterIntergrationPoints();
		try{
		// TestCase - Integration Point - assets and liabilities - Step 1
		Assert.assertTrue(loginpage.enterLoginInfo(),"EnterLoginInfo method failed");
		// TestCase - Integration point - assets and liabilities - Step2 and Step 3
		Assert.assertTrue(integrationpoints.searchAndOpenApplicantDetails(), "searchAndOpenApplicantDetails method failed");
		// TestCase - Integration point - assets and liabilities - step 6
		Assert.assertTrue(integrationpoints.uncheckValidcheckBoxApplicantTaxReturn(), "Un-CheckValidcheckBoxApplicantTaxReturn failed");
		// TestCase - Integration point - assets and liabilities - step 8
		Assert.assertTrue(integrationpoints.verifyApplicantCenterDocumentsDetails(false), "verifyApplicantCenterDocumentsDetails falied");
		}
		catch(Exception e){			
			Assert.fail();
			log.info("TGL108ValidateIntegrationPoints test failed due to exception: "+e);
		}
	}
	/* @Description: Below Test Verifies document removal, Applicant Notes removal
	 * 
	 * @Param: TGL Admin credentials and a valid applicant email and person id in TestData
	 * @Author: Nitin Sharma
	 */
	@Test(priority=3, enabled = true)
	public void TGL111ValidateIntegrationPoints(){
		LoginPageTgl loginpage = new LoginPageTgl();
		TGLAppCenterIntergrationPoints integrationpoints= new TGLAppCenterIntergrationPoints();
		try{
		// TestCase - Integration Point - assets and liabilities - Step 1
		Assert.assertTrue(loginpage.enterLoginInfo(),"EnterLoginInfo method failed");
		// TestCase - Integration point - assets and liabilities - Step 2 and Step 3
		Assert.assertTrue(integrationpoints.searchAndOpenApplicantDetails(), "searchAndOpenApplicantDetails method failed");
		Assert.assertTrue(integrationpoints.removeExistingDocumentsfromApplicantTaxReturn(), "removeExistingDocumentsfromApplicantTaxReturn method failed");	
		// TestCase - Integration point - assets and liabilities - Step 8
		Assert.assertTrue(integrationpoints.verifyDocumentIsRemoved(), "verifyApplicantCenterDocumentsDetails falied");
		}
		catch(Exception e){			
		Assert.fail();
		log.info("TGL108ValidateIntegrationPoints test failed due to exception: "+e);
		}
	}
	
	@Override
	public TGLConstants getConstants()
	{
		return new TGLConstants();
	}
}
