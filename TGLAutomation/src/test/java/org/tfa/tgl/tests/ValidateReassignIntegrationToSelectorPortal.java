package org.tfa.tgl.tests;


import java.util.HashMap;
import java.util.Map;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.tfa.framework.core.BaseTestMethods;
import org.tfa.framework.utilities.general.CryptoUtil;
import org.tfa.tgl.pages.common.LoginPageTgl;
import org.tfa.tgl.pages.common.SelectorPortalPage;
import org.tfa.tgl.pages.search.SearchPage;
import org.tfa.tgl.pages.searchdetails.SearchDetailsPage;
import org.tfa.tgl.utilities.web.TGLConstants;
import org.tfa.tgl.utilities.web.TGLWebUtil;

public class ValidateReassignIntegrationToSelectorPortal extends BaseTestMethods {
	
	private TGLWebUtil webUtil=TGLWebUtil.getObject();
	private SearchPage searchPage= new SearchPage();
	private SearchDetailsPage searchDetailsPage= new SearchDetailsPage();
	private SelectorPortalPage selectorPortalPage = new SelectorPortalPage();
	private static final String NEWYORK="New York";
	String assignmentId = null;


	/**
	 **************************************************************************************************************
	 * @Description  : This function is to verify Assign link in Assignment page in SelectorPortal
  	 * @Param: No Parameter
	 * @Return: No Return 
	 * @Author: Surya
	 **************************************************************************************************************
	 */
	@Test
	public void tgl120ReassignmentSelectorPortalIntegrationTest() throws Exception {
		String selectorPortalURL = testDataMap.get("SelectorPortalURL");
		String userNameSelectorPortal = testDataMap.get("SelectorPortalUserName");
		String passwordSelectorPortal = new CryptoUtil().decrypt(testDataMap.get("SelectorPortalPassword"));
		String uploadedFileName=testDataMap.get("uploadUploadTemplateFilePath");
		String expAssignment=testDataMap.get("expectedAssignment");
	
		/*  
		 * Step 1 - Login to TGL portal and pickup one applicant whose application is not completed  and click on applicant
		 */
		LoginPageTgl loginPage = webUtil.openLoginPage();
		loginPage.enterLoginInfo();
		
		//searchPage.selectTGLStatusDD("Tgl_InComplete_LK");
 		//searchPage.clickOnSearchBtn();
 		//String applicantID = searchPage.clickApplicantNameOnSearchResults();
 		String applicantID ="4111644";
 		searchPage.enterPersonIDAndClickOnSearchButton(applicantID);
 		
 		
		Assert.assertNotNull(applicantID, "Not returned any related data on Search results");
		webUtil.holdOn(5);
			 
		/*  
		 * Step 2 - Now complete the application but do not calculate any award , make sure applicant is assign to region
		 */
		String actualValue = searchDetailsPage.getAssignmentValue("Tgl_Assignment_ST");
		Map<String, String> objectMap = assignAssignmentValue(actualValue, expAssignment);
		assignmentId = objectMap.get("AssignmentId");
		expAssignment = objectMap.get("ExpAssignment");
		searchDetailsPage.clickCompleteAndFixErrorMessages(applicantID);
		searchDetailsPage.clickOnTGLSignOutLink();

		/*
		 *  Step 3 - Now Go to selector Portal Click on Admission> select Regional reassignment upload Pickup sample upload file and enter 
		 * data such as Person id( same personid) Reassignament as Y and Granted as Y and new position name such as SASPED
		 * Click on Upload
		*/
		//--> Go to Selector Portal
		webUtil.openLoginPage(selectorPortalURL);
		selectorPortalPage.validLogin(userNameSelectorPortal, passwordSelectorPortal);
		webUtil.holdOn(5);
		selectorPortalPage.selectRegionalReassignmentUploadLink();

		//--> update excel with person ID and save
		selectorPortalPage.updateExcelWithPersonIDForRegReassignUploadTemplate(uploadedFileName,applicantID,assignmentId);
		
		//--> Click on Upload
        selectorPortalPage.chooseFileAndUploadTemplate();
		
		/*
		* Step 4 - Come back to TGL Portal, log out and log back again and check Award Calculation 
		*/
        webUtil.openLoginPage();
		loginPage.enterLoginInfo();
		searchPage.enterPersonIDAndClickOnSearchButton(applicantID);
		String actualAssignmentValue = searchDetailsPage.getAssignmentValue("Tgl_Assignment_ST");
		Assert.assertEquals(actualAssignmentValue,expAssignment, "Verify the Assignment value updated");
		
		/* 
		* Step 5 - Now click on Calculate award 
		*/
		webUtil.click("Home_Tgl_Search2_btn");
		webUtil.waitUntilElementVisible("Tgl_CalculatedTotal_ST",10);
		
		/*
		 * Step 6 - Verify Award
		 */
		String actualCalculatedTotalAmountAfterCalculate = webUtil.getText("Tgl_CalculatedTotal_ST");
		String actualContributionAmountAfterCalculate = webUtil.getText("Tgl_ExpectedContribution_ST");
		Assert.assertNotEquals(actualCalculatedTotalAmountAfterCalculate, "n/a", "Verify Calculated Total Amount is updated");
		Assert.assertNotEquals(actualContributionAmountAfterCalculate,"n/a", "Verify Expected Contribution Amount is updated");
		searchDetailsPage.selectTGLStatusDD("Incomplete");

		/* 
		* Step 7 - End Script 
		*/
	}

	//Assign the Assignment value on the TGL Page
	public Map<String, String> assignAssignmentValue(String value, String expAssignment){
		Map<String, String> objectMap=new HashMap<>();
		if(value.contains("San Antonio")){
			assignmentId ="NYENGL";
			expAssignment = NEWYORK;
		}else if(value.contains(NEWYORK)){
			assignmentId ="SASPED";
			expAssignment = "San Antonio";
		}else{
			assignmentId ="NYENGL";
			expAssignment = NEWYORK;
		}
		objectMap.put("AssignmentId", assignmentId);
		objectMap.put("ExpAssignment", expAssignment);
		return objectMap;

	}
	
	@Override
	public TGLConstants getConstants(){
		return new TGLConstants();
	}
}
