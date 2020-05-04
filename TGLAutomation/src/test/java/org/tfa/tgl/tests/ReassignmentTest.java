package org.tfa.tgl.tests;


import org.testng.Assert;
import org.testng.annotations.Test;
import org.tfa.framework.core.BaseTestMethods;
import org.tfa.tgl.pages.IMPSPage;
import org.tfa.tgl.pages.LoginPageTgl;
import org.tfa.tgl.pages.SearchDetailsPageTGL;
import org.tfa.tgl.pages.SearchPageTGL;
import org.tfa.tgl.utilities.web.TGLConstants;
import org.tfa.tgl.utilities.web.TGLWebUtil;

/**
 **************************************************************************************************************
 * @Description  : This class Validate to verify Assign link in Assignment page in IMPS
 * @parent: BaseTestMethods class has been extended that has basic methods those will run before suite, before class,
 *          before method, after class, after method etc. 
 * @TestCase     :  TGL11128TestReassignmentIntegrationPoint()
 * @Author: Surya
 ************************************************************************************************************** 
 */
public class ReassignmentTest extends BaseTestMethods {
	
	private LoginPageTgl loginpage;
	private TGLWebUtil webUtil=TGLWebUtil.getObject();
	private SearchPageTGL searchPage= new SearchPageTGL();
	private SearchDetailsPageTGL searchDetailsPage= new SearchDetailsPageTGL();
	private IMPSPage IMPSpage = new IMPSPage();
	private String selectedQualifiedPositon;
	String actualCalculatedTotalAmount_BeforeCalculate;
	String actualExpectedContributionAmount_BeforeCalculate;
	String actualAdjustedLoanAmount_BeforeCalculate;
	String actualAdjustedGrantAmount_BeforeCalculate;

	/**
	 **************************************************************************************************************
	 * @throws Exception 
	 * @Description  : This function is to verify Assign link in Assignment page in IMPS Call the TGL Calculation
	 * Only update existing calculated loan and grant amount. Tool should not create a new loan and/or grant amount.
  	 * @Param: No Parameter
	 * @Return: No Return 
	 * @Author: Surya
	 **************************************************************************************************************
	 */
	
	@Test
	public void TGL11128TestReassignmentIntegrationPoint() throws Exception{
		
		String url = testDataMap.get("IMPSURL");
		String userNameIMPS = testDataMap.get("IMPSUserName");
		String passwordIMPS = testDataMap.get("IMPSPassword");
		
		/* 
		* Step 1 - Login to TGL portal and pickup one applicant whose application is not completed  and click on applicant
		*/
		loginpage=new LoginPageTgl();
		loginpage.enterLoginInfo();
		searchPage.selectTGLStatusDD("Tgl_InComplete_LK");
		searchPage.clickOnSearchBtn();
		String applicantID = searchPage.clickFirstRowColumnOnSearchResults();
		
		/* 
		* Step 2 - Now complete the application but do not calculate any award , make sure applicant is assign to region
		*/
		searchDetailsPage.clickCompleteAndFixErrorMessages(applicantID);
		searchDetailsPage.clickOnTGLSignOutLink();
		
		/* 
		* Step 3 - Now Go to IMPS and reassign the diff region for same applicant Note: steps to reassign the region
		* Go to IMPS, Search for same applicant, Click on Applicant, Click on Assignment link, You can assign new position there
		*/
		//--> Go to IMPS
		webUtil.openLoginPage(url);
		IMPSpage.validLogin(userNameIMPS,passwordIMPS);
		IMPSpage.clickOnAdmissionsButton();
		
		//--> Search for same applicant
		webUtil.switchToFrameByFrameLocator("CommonMainframeWithName_Frm");
		IMPSpage.enterPersonID(applicantID);
		IMPSpage.clickOnSearchButton();
		//--> Click on Applicant
		IMPSpage.clickOnApplicantLink(applicantID);
		//--> Click on Assignment link 
		IMPSpage.clickOnAssignment();
		//--> You can assign new position there
		selectedQualifiedPositon = IMPSpage.assignNewQualifiedPosition();
		//IMPSpage.clickOnIMPSLogoutButton();
		
		/*
		* Step 4 - Come back to TGL Portal, log out and log back again and check Award Calculation 
		*/
		loginpage=new LoginPageTgl();
		loginpage.enterLoginInfo();
		searchPage.clickOnMoreSearchOptionsBtn();
		searchPage.enterPersonID(applicantID);
		searchPage.clickOnSearchBtn();
		searchPage.clickFirstRowColumnOnSearchResults();
		String actualAssignmentValue = getAssignmentValue();
		Assert.assertTrue(selectedQualifiedPositon.contains(actualAssignmentValue), "Verify the Assignment value updated");
		
		/* 
		* Step 5 - Now click on Calculate award 
		*/
		webUtil.click("Home_Tgl_Search2_btn");
		webUtil.holdOn(2);
		
		/*
		 * Step 6 - Verify Award
		 */
		String actualCalculatedTotalAmount_AfterCalculate = webUtil.getText("Tgl_CalculatedTotal_ST");
		String actualContributionAmount_AfterCalculate = webUtil.getText("Tgl_ExpectedContribution_ST");
		Assert.assertNotEquals(actualCalculatedTotalAmount_AfterCalculate, "n/a", "Verify Calculated Total Amount is updated");
		Assert.assertNotEquals(actualContributionAmount_AfterCalculate,"n/a", "Verify Expected Contribution Amount is updated");
		
		/* 
		* Step 7 - End Script 
		*/
	}
	//gets the Assignment value on the TGL Page
	private String getAssignmentValue(){
		String asignmentValue = webUtil.getText("Tgl_Assignment_ST");
		String[] arrSplit = asignmentValue.split(": ");
		String actualAssignmentValue = arrSplit[1];
		return actualAssignmentValue;
	}
	
	@Override
	public TGLConstants getConstants(){
		return new TGLConstants();
	}

}
