package org.tfa.tgl.tests;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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
	private IMPSPage IMPSpage;
	private String getErrorMessageText;
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
		selectTGLStatusDD("Complete");
		webUtil.holdOn(5);
		boolean iflag = webUtil.objectIsVisible("Tgl_StatusValidationMessage_ST");
		if (iflag) {
			getErrorMessageText = webUtil.getText("Tgl_StatusValidationMessage_ST");
			if((getErrorMessageText.contains("Status cannot be changed")) || (getErrorMessageText.contains("Total # of Dependents is blank"))){
				searchDetailsPage.enterTotalNumberOfDependentsAndIncomeAmount();
				selectCheckBoxsForObjectValid("Tgl_SelectCheckBox_chk");
				selectTGLStatusDD("Complete");
			}
		}
		boolean iflag1 = webUtil.objectIsVisible("Tgl_StatusValidationMessage_ST");	
		if (iflag1) {
			Assert.assertTrue(false,"PLEASE CHECK MANUALLY.... THERE ARE SOME MORE ERROR MESSAGES FOR THE PERSONID -> "+applicantID);
		}
		searchDetailsPage.clickOnTGLSignOutLink();
		
		/* 
		* Step 3 - Now Go to IMPS and reassign the diff region for same applicant Note: steps to reassign the region
		* Go to IMPS, Search for same applicant, Click on Applicant, Click on Assignment link, You can assign new position there
		*/
		//--> Go to IMPS
		IMPSpage = new IMPSPage();
		IMPSpage.openLoginPage(url);
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
		String getQualifiedPosition = webUtil.getText("IMPS_QualifiedFirstPosition_LK");
		String getAssignStatus = webUtil.getText("IMPS_QualifiedFirstPositionAssign_LK");
		if(getAssignStatus.equals("Unassign")){
			if(getQualifiedPosition.contains("Bay Area")){
				webUtil.click("IMPS_NewYorkAssign_LK");
			}else if(getQualifiedPosition.contains("New York")){
				webUtil.click("IMPS_BayAreaAssign_LK");
			}else if(getQualifiedPosition.contains("D.C. Region")){
				webUtil.click("IMPS_BayAreaAssign_LK");
			}else{
			webUtil.click("IMPS_DCRegionAssign_LK");
			}
		}
		selectedQualifiedPositon = webUtil.getText("IMPS_QualifiedFirstPosition_LK");
		webUtil.switchToWindowFromFrame();
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
		actualCalculatedTotalAmount_BeforeCalculate = webUtil.getText("Tgl_CalculatedTotal_ST");
		actualExpectedContributionAmount_BeforeCalculate = webUtil.getText("Tgl_ExpectedContribution_ST");
		Assert.assertNotEquals(actualCalculatedTotalAmount_BeforeCalculate, "n/a", "Verify Calculated Total Amount is updated");
		Assert.assertNotEquals(actualExpectedContributionAmount_BeforeCalculate,"n/a", "Verify Expected Contribution Amount is updated");
		
		/* 
		* Step 6 - End Script 
		*/
	}
	//gets the Assignment value on the TGL Page
	private String getAssignmentValue(){
		String asignmentValue = webUtil.getText("Tgl_Assignment_ST");
		String[] arrSplit = asignmentValue.split(": ");
		String actualAssignmentValue = arrSplit[1];
		return actualAssignmentValue;
	}
	//Selects the TGL Status
	private void selectTGLStatusDD(String tglStatus){
		webUtil.selectByVisibleText("Tgl_TGLStatusInput_DD", tglStatus);
		webUtil.holdOn(5);
	}
	//Verifies that the required? object isSelected on TGL Detail page, and if the Valid? checkbox is not selects it will 
	//selects the Valid? object on the page
	private void selectCheckBoxsForObjectValid(String locatorName){
		Map<String, String> locatorValueMap=webUtil.getLocatorValueMap(locatorName);
		String locatorValue=TGLWebUtil.getLocatorValue(locatorValueMap, locatorName);
		List<WebElement> getValues = webUtil.getDriver().findElements(By.xpath(locatorValue));
		for(int i = 1; i<=getValues.size(); i++){
			WebElement checkBox_Required = webUtil.getDriver().findElement(By.xpath("("+ locatorValue +"["+i+"]//input)[1]"));
			if(checkBox_Required.isSelected()){
				WebElement checkBox_Valid = webUtil.getDriver().findElement(By.xpath("("+ locatorValue+"["+i+"]//input)[2]"));
				if(!checkBox_Valid.isSelected()){
					webUtil.click(checkBox_Valid);
				}
			}
		}
	}
	@Override
	public TGLConstants getConstants(){
		return new TGLConstants();
	}

}
