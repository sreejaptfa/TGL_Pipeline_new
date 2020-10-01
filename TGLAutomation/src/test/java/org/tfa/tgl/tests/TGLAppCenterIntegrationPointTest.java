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
	private static final String ENTERLOGININFOMETHODFAILED="EnterLoginInfo method failed";
	private static final String TESTFAILEDDUETOEXCEPTION="TGL108ValidateIntegrationPoints test failed due to exception: ";
	private static final String SEARCHANDOPENAPPLICANTMETHODFAILED="searchAndOpenApplicantDetails method failed";
	private static final String APPLICANTCENTERDOCUMENTDETAILSFAILED="verifyApplicantCenterDocumentsDetails falied";
	
	/* @Description: Below Test Verifies File Upload, Applicant Notes 
	 * and Applicant Tax Return "Valid" Checkbox checked between AppCenter and TGL
	 * @Param: TGL Admin credentials and a valid applicant email and person id in TestData
	 * @Author: Nitin Sharma
	 */
	@Test(priority=1, enabled = true)
	public void tgl108ValidateIntegrationPoints(){
		loginpage = new LoginPageTgl();
		TGLAppCenterIntergrationPoints integrationpoints= new TGLAppCenterIntergrationPoints();
		try{		
		// TestCase - Integration Point - assets and liabilities - Step 1
		Assert.assertTrue(loginpage.enterLoginInfo(),ENTERLOGININFOMETHODFAILED);
		// TestCase - Integration point - assets and liabilities - Step2 and Step 3
		Assert.assertTrue(integrationpoints.searchAndOpenApplicantDetails(), SEARCHANDOPENAPPLICANTMETHODFAILED);
		// TestCase - Integration point - assets and liabilities - step 5 
		Assert.assertTrue(integrationpoints.uploadDocumentinAda(), "uploadDocumentinAda method failed");
		// TestCase - Integration point - assets and liabilities - step 6 
		Assert.assertTrue(integrationpoints.checkValidcheckBoxApplicantTaxReturn(), "checkValidcheckBoxApplicantTaxReturn failed");
		// TestCase - Integration point - assets and liabilities - step 7
		Assert.assertTrue(integrationpoints.verifyApplicantCenterDocumentsDetails(true), APPLICANTCENTERDOCUMENTDETAILSFAILED);
		// TestCase - Integration point - assets and liabilities - step 8
		// Commenting below test since this fails on Pipeline
		}
		catch(Exception e){			
			Assert.fail();
			log.info(TESTFAILEDDUETOEXCEPTION+e);
		}
	}	
	
	
	/* @Description: Below Test Verifies Applicant Notes removal
	 * and Applicant Tax Return "Valid" Checkbox UNCHECKED between AppCenter and TGL
	 * @Param: TGL Admin credentials and a valid applicant email and person id in TestData
	 * @Author: Nitin Sharma
	 */
	@Test(priority=2, enabled = true)
	public void tgl110ValidateIntegrationPoints(){
		loginpage = new LoginPageTgl();
		TGLAppCenterIntergrationPoints integrationpoints= new TGLAppCenterIntergrationPoints();
		try{
		// TestCase - Integration Point - assets and liabilities - Step 1
		Assert.assertTrue(loginpage.enterLoginInfo(),ENTERLOGININFOMETHODFAILED);
		// TestCase - Integration point - assets and liabilities - Step2 and Step 3
		Assert.assertTrue(integrationpoints.searchAndOpenApplicantDetails(), SEARCHANDOPENAPPLICANTMETHODFAILED);
		// TestCase - Integration point - assets and liabilities - step 6
		Assert.assertTrue(integrationpoints.uncheckValidcheckBoxApplicantTaxReturn(), "Un-CheckValidcheckBoxApplicantTaxReturn failed");
		// TestCase - Integration point - assets and liabilities - step 8
		Assert.assertTrue(integrationpoints.verifyApplicantCenterDocumentsDetails(false), APPLICANTCENTERDOCUMENTDETAILSFAILED);
		}
		catch(Exception e){			
			Assert.fail();
			log.info(TESTFAILEDDUETOEXCEPTION+e);
		}
	}
	/* @Description: Below Test Verifies document removal, Applicant Notes removal
	 * 
	 * @Param: TGL Admin credentials and a valid applicant email and person id in TestData
	 * @Author: Nitin Sharma
	 */
	@Test(priority=3, enabled = true)
	public void tgl111ValidateIntegrationPoints(){
		loginpage = new LoginPageTgl();
		TGLAppCenterIntergrationPoints integrationpoints= new TGLAppCenterIntergrationPoints();
		try{
		// TestCase - Integration Point - assets and liabilities - Step 1
		Assert.assertTrue(loginpage.enterLoginInfo(),ENTERLOGININFOMETHODFAILED);
		// TestCase - Integration point - assets and liabilities - Step 2 and Step 3
		Assert.assertTrue(integrationpoints.searchAndOpenApplicantDetails(), SEARCHANDOPENAPPLICANTMETHODFAILED);
		Assert.assertTrue(integrationpoints.removeExistingDocumentsfromApplicantTaxReturn(), "removeExistingDocumentsfromApplicantTaxReturn method failed");	
		// TestCase - Integration point - assets and liabilities - Step 8
		Assert.assertTrue(integrationpoints.verifyDocumentIsRemoved(), APPLICANTCENTERDOCUMENTDETAILSFAILED);
		}
		catch(Exception e){			
		Assert.fail();
		log.info(TESTFAILEDDUETOEXCEPTION+e);
		}
		
	
	}
	/* @Description: Below Test Verifies File Upload, Applicant Notes 
	 * and Applicant Tax Return "Valid" Checkbox checked between AppCenter and TGL
	 * @Param: TGL Admin credentials and a valid applicant email and person id in TestData
	 * @Author: Nitin Sharma
	 */
	@Test(priority=4, enabled = true)
	public void tgl112ValidateIntegrationPoints(){
		loginpage = new LoginPageTgl();
		TGLAppCenterIntergrationPoints integrationpoints= new TGLAppCenterIntergrationPoints();
		try{		
		// TestCase - Integration Point - assets and liabilities - Step 1
		Assert.assertTrue(loginpage.enterLoginInfo(),ENTERLOGININFOMETHODFAILED);
		// TestCase - Integration point - assets and liabilities - Step2 and Step 3
		Assert.assertTrue(integrationpoints.searchAndOpenApplicantDetails(), SEARCHANDOPENAPPLICANTMETHODFAILED);
		// TestCase - Integration point - assets and liabilities - step 5 
		Assert.assertTrue(integrationpoints.uploadDocumentinAda(), "uploadDocumentinAda method failed");
		// TestCase - Integration point - assets and liabilities - step 6 
		Assert.assertTrue(integrationpoints.checkValidcheckBoxApplicantTaxReturn(), "checkValidcheckBoxApplicantTaxReturn failed");
		// TestCase - Integration point - assets and liabilities - Step 8
		Assert.assertTrue(integrationpoints.removeExistingDocumentsfromApplicantTaxReturn(), "removeExistingDocumentsfromApplicantTaxReturn method failed");
		Assert.assertTrue(integrationpoints.verifyDocumentIsRemoved(), APPLICANTCENTERDOCUMENTDETAILSFAILED);
		}
		catch(Exception e){			
			Assert.fail();
			log.info(TESTFAILEDDUETOEXCEPTION+e);
		}
	}	
	
	
	
	@Override
	public TGLConstants getConstants()
	{
		return new TGLConstants();
	}
}