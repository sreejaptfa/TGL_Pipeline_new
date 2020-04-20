package org.tfa.tgl.tests;


import org.testng.Assert;
import org.testng.annotations.Test;
import org.tfa.framework.core.BaseTestMethods;
import org.tfa.tgl.pages.LoginPageTgl;
import org.tfa.tgl.pages.SearchDetailsPageTGL;
import org.tfa.tgl.pages.SearchPageTGL;
import org.tfa.tgl.pages.SelectorPortalPage;
import org.tfa.tgl.utilities.web.TGLConstants;
import org.tfa.tgl.utilities.web.TGLWebUtil;

/**
 **************************************************************************************************************
 * @Description  : This class Validate to verify Assign link in Assignment page in SelectorPortal
 * @parent: BaseTestMethods class has been extended that has basic methods those will run before suite, before class,
 *          before method, after class, after method etc. 
 * @TestCase     :  TGL11130TestReassignmentSelectorPortalIntegrationPoint()
 * @Author: Surya
 ************************************************************************************************************** 
 */
public class ReassignmentSelectorPortalIntegrationTest extends BaseTestMethods {
	
	private LoginPageTgl loginpage;
	private TGLWebUtil webUtil=TGLWebUtil.getObject();
	private SearchPageTGL searchPage= new SearchPageTGL();
	private SearchDetailsPageTGL searchDetailsPage= new SearchDetailsPageTGL();
	private SelectorPortalPage selectorPortalPage = new SelectorPortalPage();
	String actualCalculatedTotalAmount_BeforeCalculate;
	String actualExpectedContributionAmount_BeforeCalculate;
	String actualAdjustedLoanAmount_BeforeCalculate;
	String actualAdjustedGrantAmount_BeforeCalculate;

	/**
	 **************************************************************************************************************
	 * @throws Exception 
	 * @Description  : This function is to verify Assign link in Assignment page in SelectorPortal
  	 * @Param: No Parameter
	 * @Return: No Return 
	 * @Author: Surya
	 **************************************************************************************************************
	 */
	@Test
	public void TGL11130TestReassignmentSelectorPortalIntegrationPoint() throws Exception {
		String url = testDataMap.get("SelectorPortalURL");
		String userNameSelectorPortal = testDataMap.get("SelectorPortalUserName");
		String passwordSelectorPortal = testDataMap.get("SelectorPortalPassword");
		String uploadedFileName=testDataMap.get("uploadUploadTemplateFilePath");
		String expectedAssignment=testDataMap.get("expectedAssignment");

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
		 *  Step 3 - Now Go to selector Portal Click on Admission> select Regional reassignment upload Pickup sample upload file and enter 
		 * data such as Person id( same personid) Reassignament as Y and Granted as Y and new position name such as SASPED
		 * Click on Upload
		*/
		//--> Go to Selector Portal
		webUtil.openLoginPage(url);
		selectorPortalPage.validLogin(userNameSelectorPortal, passwordSelectorPortal);
		selectorPortalPage.selectRegionalReassignmentUploadLink();

		//--> update excel with person ID and save
		selectorPortalPage.updateExcelWithPersonIDForRegReassignUploadTemplate(uploadedFileName,applicantID);
		
		//--> Click on Upload
        selectorPortalPage.chooseFileAndUploadTemplate();
		
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
		Assert.assertEquals(actualAssignmentValue,expectedAssignment, "Verify the Assignment value updated");
		
		actualCalculatedTotalAmount_BeforeCalculate = webUtil.getText("Tgl_CalculatedTotal_ST");
		actualExpectedContributionAmount_BeforeCalculate = webUtil.getText("Tgl_ExpectedContribution_ST");
		actualAdjustedLoanAmount_BeforeCalculate = webUtil.getText("Tgl_AdjustedLoanAmount_ST");
		actualAdjustedGrantAmount_BeforeCalculate = webUtil.getText("Tgl_AdjustedGrantAmount_ST");
			
		Assert.assertEquals(actualCalculatedTotalAmount_BeforeCalculate,"n/a", "Verify Calculated Total Amount not display any Calculation");
		Assert.assertEquals(actualExpectedContributionAmount_BeforeCalculate,"n/a", "Verify Expected Contribution Amount not display any Calculation");
		Assert.assertEquals(actualAdjustedLoanAmount_BeforeCalculate,"n/a", "Verify Adjusted Loan Amount not display any Calculation");
		Assert.assertEquals(actualAdjustedGrantAmount_BeforeCalculate,"n/a", "Verify Adjusted Grant Amount not display any Calculation");

		/* 
		* Step 5 - Now click on Calculate award 
		*/
		webUtil.click("Home_Tgl_Search2_btn");
		webUtil.holdOn(2);
		
		/*
		 * Step 6 - Verify Award
		 */
		actualCalculatedTotalAmount_BeforeCalculate = webUtil.getText("Tgl_CalculatedTotal_ST");
		actualExpectedContributionAmount_BeforeCalculate = webUtil.getText("Tgl_ExpectedContribution_ST");
		Assert.assertNotEquals(actualCalculatedTotalAmount_BeforeCalculate, "n/a", "Verify Calculated Total Amount is updated");
		Assert.assertNotEquals(actualExpectedContributionAmount_BeforeCalculate,"n/a", "Verify Expected Contribution Amount is updated");
		
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
