package org.tfa.tgl.tests;


import java.util.List;

import org.openqa.selenium.WebElement;
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
	
	private TGLWebUtil webUtil=TGLWebUtil.getObject();
	private SearchPageTGL searchPage= new SearchPageTGL();
	private SearchDetailsPageTGL searchDetailsPage= new SearchDetailsPageTGL();
	private SelectorPortalPage selectorPortalPage = new SelectorPortalPage();
	private static final String NEWYORK="New York";

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
	public void tgl11130TestReassignmentSelectorPortalIntegrationPoint() throws Exception {
		String url = testDataMap.get("SelectorPortalURL");
		String userNameSelectorPortal = testDataMap.get("SelectorPortalUserName");
		String passwordSelectorPortal = testDataMap.get("SelectorPortalPassword");
		String uploadedFileName=testDataMap.get("uploadUploadTemplateFilePath");
		String assignmentId = null;
		/*  
		 * Step 1 - Login to TGL portal and pickup one applicant whose application is not completed  and click on applicant
		 */
		LoginPageTgl loginpage=new LoginPageTgl();
		loginpage.enterLoginInfo();
		
		searchPage.selectTGLStatusDD("Tgl_InComplete_LK");
 		searchPage.clickOnSearchBtn();
 		webUtil.holdOn(2);
		String applicantID = searchPage.clickApplicantNameOnSearchResults();
		
		if(applicantID !=null) {//NOSONAR
		}else {
			webUtil.click("Tgl_Clear_btn");
			List<WebElement> link=webUtil.getElementsList("Home_tgl_applicationyear");
			WebElement appYear =  link.get(2);
			appYear.click();
			searchPage.selectTGLStatusDD("Tgl_Complete_LK");
	 		searchPage.clickOnSearchBtn();
	 		applicantID = searchPage.clickApplicantNameOnSearchResults();
		}
		
		
		Assert.assertNotNull(applicantID, "Not returned any related data on Search results");
		webUtil.holdOn(5);
			 
		/*  
		 * Step 2 - Now complete the application but do not calculate any award , make sure applicant is assign to region
		 */
		String expAssignment=testDataMap.get("expectedAssignment");//NOSONAR
		String actualValue = getAssignmentValue();
		if(actualValue.contains("San Antonio")){
			assignmentId ="NYENGL";
			expAssignment = NEWYORK;
		}else if(actualValue.contains(NEWYORK)){
			assignmentId ="SASPED";
			expAssignment = "San Antonio";
		}else{//NOSONAR
			assignmentId ="NYENGL";
			expAssignment = NEWYORK;
		}
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
		webUtil.holdOn(5);
		selectorPortalPage.selectRegionalReassignmentUploadLink();

		//--> update excel with person ID and save
		selectorPortalPage.updateExcelWithPersonIDForRegReassignUploadTemplate(uploadedFileName,applicantID,assignmentId);
		
		//--> Click on Upload
        selectorPortalPage.chooseFileAndUploadTemplate();
		
		/*
		* Step 4 - Come back to TGL Portal, log out and log back again and check Award Calculation 
		*/
		loginpage=new LoginPageTgl();
		loginpage.enterLoginInfo();
		webUtil.waitForBrowserToLoadCompletely();
		webUtil.holdOn(10);
		webUtil.click("Tgl_Clear_btn");
		webUtil.holdOn(2);
		searchPage.clickOnMoreSearchOptionsBtn();
		searchPage.enterPersonID(applicantID);
		searchPage.clickOnSearchBtn();
		searchPage.clickFirstRowColumnOnSearchResults();
		String actualAssignmentValue = getAssignmentValue();
		Assert.assertEquals(actualAssignmentValue,expAssignment, "Verify the Assignment value updated");
		
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
