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
	
	private TGLWebUtil webUtil=TGLWebUtil.getObject();
	private SearchPageTGL searchPage= new SearchPageTGL();
	private SearchDetailsPageTGL searchDetailsPage= new SearchDetailsPageTGL();
	private IMPSPage impsPage = new IMPSPage();
	String actualCalculatedTotalAmountBeforeCalculate;
	String actualExpectedContributionAmountBeforeCalculate;
	String actualAdjustedLoanAmountBeforeCalculate;
	String actualAdjustedGrantAmountBeforeCalculate;

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
	public void tgl11128TestReassignmentIntegrationPoint() throws Exception{
		
		String selectedQualifiedPositon;
		String url = testDataMap.get("IMPSURL");
		String userNameIMPS = testDataMap.get("IMPSUserName");
		String passwordIMPS = testDataMap.get("IMPSPassword");
		String applicantID;
		/* 
		* Step 1 - Login to TGL portal and pickup one applicant whose application is not completed  and click on applicant
		*/
		LoginPageTgl loginpage=new LoginPageTgl();
		loginpage.enterLoginInfo();
				
		searchPage.selectTGLStatusDD("Tgl_InComplete_LK");
		searchPage.clickOnSearchBtn();
		boolean iChexkFlag = webUtil.objectIsVisible("Tgl_FirstRowColumn_TB");
		if(iChexkFlag){
			applicantID = searchPage.clickFirstRowColumnOnSearchResults();
		}else{
			searchPage.selectTGLStatusDD("Tgl_Complete_LK");
			searchPage.clickOnSearchBtn();
			applicantID = searchPage.clickFirstRowColumnOnSearchResults();
			searchDetailsPage.selectTGLStatusDD("Incomplete");
			webUtil.holdOn(5);
		}
		
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
		impsPage.validLogin(userNameIMPS,passwordIMPS);
		webUtil.holdOn(5);
		impsPage.clickOnAdmissionsButton();
		
		//--> Search for same applicant
		webUtil.switchToFrameByFrameLocator("CommonMainframeWithName_Frm");
		impsPage.enterPersonID(applicantID);
		impsPage.clickOnSearchButton();
		//--> Click on Applicant
		impsPage.clickOnApplicantLink(applicantID);
		//--> Click on Assignment link 
		impsPage.clickOnAssignment();
		//--> You can assign new position there
		selectedQualifiedPositon = impsPage.assignNewQualifiedPosition();
		
		/*
		* Step 4 - Come back to TGL Portal, log out and log back again and check Award Calculation 
		*/
		loginpage=new LoginPageTgl();
		loginpage.enterLoginInfo();
		webUtil.holdOn(5);
		webUtil.click("Tgl_Clear_btn");
		searchPage.clickOnMoreSearchOptionsBtn();
		searchPage.enterPersonID(applicantID);
		searchPage.clickOnSearchBtn();
		searchPage.clickFirstRowColumnOnSearchResults();
		webUtil.holdOn(2);
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
		String actualCalculatedTotalAmountAfterCalculate = webUtil.getText("Tgl_CalculatedTotal_ST");
		String actualContributionAmountAfterCalculate = webUtil.getText("Tgl_ExpectedContribution_ST");
		Assert.assertNotEquals(actualCalculatedTotalAmountAfterCalculate, "n/a", "Verify Calculated Total Amount is updated");
		Assert.assertNotEquals(actualContributionAmountAfterCalculate,"n/a", "Verify Expected Contribution Amount is updated");
		webUtil.holdOn(5);
		searchDetailsPage.selectTGLStatusDD("Incomplete");
		
		/* 
		* Step 7 - End Script 
		*/
	}
	//gets the Assignment value on the TGL Page
	private String getAssignmentValue(){
		String asignmentValue = webUtil.getText("Tgl_Assignment_ST");
		String[] arrSplit = asignmentValue.split(": ");
		return arrSplit[1];
	}
	
	@Override
	public TGLConstants getConstants(){
		return new TGLConstants();
	}
	
}