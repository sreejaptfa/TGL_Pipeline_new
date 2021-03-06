package org.tfa.tgl.tests;

import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.tfa.framework.core.BaseTestMethods;
import org.tfa.framework.core.JavaScriptUtil;
import org.tfa.tgl.pages.common.ApplicantCenterPage;
import org.tfa.tgl.pages.common.LoginPageTgl;
import org.tfa.tgl.pages.search.SearchPage;
import org.tfa.tgl.pages.searchdetails.SearchDetailsPage;
import org.tfa.tgl.utilities.web.TGLConstants;
import org.tfa.tgl.utilities.web.TGLWebUtil;

public class ValidateCheckBoxIntegrationOnApplicantCenter extends BaseTestMethods {

	private TGLWebUtil webUtil = TGLWebUtil.getObject();
	private SearchPage searchPage = new SearchPage();
	private ApplicantCenterPage applicantCenterPage = new ApplicantCenterPage();
	private SearchDetailsPage searchDetailsPage = new SearchDetailsPage();
	private JavaScriptUtil jsUtil = JavaScriptUtil.getObject();

	/**
	 **************************************************************************************************************
	 * @Description : This function is to verify that the Education cost works as in
	 *              Admissions-9046.
	 * @Param: No Parameter
	 * @Return: No Return
	 * @Author: Surya
	 **************************************************************************************************************
	 */

	@Test
	public void tgl121ValidCheckBoxApplicantCenterIntegrationTest(){
		String applicantID = testDataMap.get("ApplicantID");
		String navToTransFundingUrl = testDataMap.get("TransFundingUrl");

		/* 
		* Step 1 - Login to the TGL  portal application using valid user id
		*/
		LoginPageTgl loginPage = webUtil.openLoginPage();
		loginPage.enterLoginInfo();
	
		/* 
		* Step 2 - Search for the Person Id which is going to verify Tax information
		* Click on Search button.
		*/

		searchPage.enterPersonIDAndClickOnSearchButton(applicantID);
		// Clear app year value - change made to fix script <<NS 21 July 2020>>
		jsUtil.scrollDownPage(500);
		searchDetailsPage.selectTGLStatusDD("Incomplete");


		/* 
		* Step 4 - 		Click Valid check box  for any of the available doc type which has required check box checked 
		* Enter Notes for same field
		*/
		//String[] sectionsOnTGL = {"Tgl_PrivateLoan_Section","Tgl_OtherLoan_Section","Tgl_Savings_Section","Tgl_Credit_Section","Tgl_ApplicantTax_Section","Tgl_W2Income_Section","Tgl_ParentTax_section","Tgl_ParentIncome_Section"};
		String[] sectionsOnTGL = {"Tgl_ApplicantTax_Section"};
		Map<String, String> objectMap = searchDetailsPage.checkValidCheckBoxAndEnterNotes(sectionsOnTGL,"Check");
		String getSelectedDocumentSectionFromTGL = objectMap.get("Section");
		String getNotesFromTGL = objectMap.get("Notes");
		String getSectionNameFromTGL = objectMap.get("SectionName");
		String getValidCheckBoxValueFromTGL =objectMap.get("ValidCheckBox");

		/* 
		* Step 5 -  Now login to online part 2 as qamerlin.teachforamerica.org/applicant-center
		* With same login which you have checked the valid check box 
		*/
		applicantCenterPage.openLoginPage();
		applicantCenterPage.enterLoginInfo();
		webUtil.holdOn(2);
		webUtil.openURL(navToTransFundingUrl);
		webUtil.holdOn(5);
		webUtil.waitForBrowserToLoadCompletely();
				
		/* 
		* Step 7 -  Now  go to cFunding link t check TGL status for check box and notes which you selected in TG
		*/	
		Map<String, String> transitionalFundingMap = applicantCenterPage.getValuesFromApplicantCenter("AppCenter_TGLDocuments_TB",getSectionNameFromTGL);
		String getNotesFromAppCenter = transitionalFundingMap.get("TransitionalFundingNotes");
		String getValidCheckBoxValueFromAppCenter = transitionalFundingMap.get("TransitionalFundingCheckBox");
		
		Assert.assertEquals(getNotesFromAppCenter,getNotesFromTGL, "Notes updated in SelectorPortal");
		Assert.assertEquals(getValidCheckBoxValueFromAppCenter,getValidCheckBoxValueFromTGL,"Documentation Verified Checkbox is Checked in Selector Portal ");
		applicantCenterPage.clickOnLogOutLink();
		
		
		/* 
		* Step 8 -  Now come back to TGL portal and uncheck the check box and verify
		*/
		webUtil.openLoginPage();
		searchPage.enterPersonIDAndClickOnSearchButton(applicantID);
		Map<String, String> locatorValueMap=webUtil.getLocatorValueMap(getSelectedDocumentSectionFromTGL);
		String locatorValue=TGLWebUtil.getLocatorValue(locatorValueMap, getSelectedDocumentSectionFromTGL);
		WebElement checkBoxValid = webUtil.getDriver().findElement(By.xpath("("+ locatorValue+"/tbody/tr//input)[2]"));
		if(checkBoxValid.isSelected()){
			webUtil.click(checkBoxValid);
		}
		
	}
	@Override
	public TGLConstants getConstants(){
		return new TGLConstants();
	}
}
