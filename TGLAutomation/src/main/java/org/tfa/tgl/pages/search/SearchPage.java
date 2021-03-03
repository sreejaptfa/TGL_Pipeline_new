package org.tfa.tgl.pages.search;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.tfa.framework.utilities.testdata.TestData;
import org.tfa.tgl.utilities.web.TGLWebUtil;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.asserts.SoftAssert;

public class SearchPage {
	
	Logger log = Logger.getLogger("rootLogger");
	SoftAssert soft = new SoftAssert();
	private TGLWebUtil webUtil = TGLWebUtil.getObject();
	private TestData data = TestData.getObject();
	private static final String CLEARBTN ="Tgl_Clear_btn";



	//***********************************************************************************************************
	// We need to fix this functionality
	// TestCase - SearchPage - Step 11, 12, 13
	public boolean verifyEachFilter() {
		
		webUtil.openURL(data.getEnvironmentDataMap().get("ApplicationURL")) ;
		webUtil.waitForBrowserToLoadCompletely();
		return false;
	}
	
	//***********************************************************************************************************

	
	// This function is to verify default sort on the name field
	public boolean verifydefaultsort(){
		ArrayList<String> nameResults = new ArrayList<>();
		ArrayList<String> subNames = new ArrayList<>();
		webUtil.getDriver().navigate().refresh();
		webUtil.holdOn(5);
		webUtil.click(CLEARBTN);
		this.clickOnMoreSearchOptionsBtn();
		webUtil.setTextBoxValue("Tgl_firstname", "a");
		clickOnSearchBtn();
		List<WebElement> searchresults = webUtil.getElementsList("Tgl_SearchResultsName_TB");
		for (int i = 0; i <= searchresults.size()-1; i++) {
			nameResults.add(searchresults.get(i).getText());
			subNames.add(nameResults.get(i).substring(0,3));
		}
		return webUtil.isSorted(subNames);
	}
	// This method verify the default appyear selection 
	public boolean verifyAppYearDefaultSelection() {
		Select appYearDD= new Select(webUtil.getElement("Tgl_appyear_dd"));
		return appYearDD.getFirstSelectedOption().getText().contains("2021");
	}

	// This method is to verify ensures right fields show up on click of "more search options"
	public boolean verifymorelinkclick() {
		boolean flag = false;
		List<WebElement> extendedContainer = webUtil.getElementsList("Tgl_MoreSearchOptionsInputContainer_Content");
		for (WebElement element : extendedContainer) {
			if (!element.isDisplayed() && (!element.isEnabled()))
				soft.fail("Input container for More search options");
			else
				flag = true;
		}
		return flag;
	}
	// This method is to verify the right columns are listed in search results table
	public boolean verifyColumnHeaders(){
		boolean flag = false;
		String columnHeaders = data.getTestCaseDataMap().get("ColumnHeaders");
		List<WebElement> actualColumnHeader = webUtil.getElementsList("Tgl_ColumnHeaders_TB");
		for (int i = 0; i <= actualColumnHeader.size()-1; i++) {
			if (columnHeaders.contains(actualColumnHeader.get(i).getText())){
				flag = true;
			}
		}
		return flag;
	}

	// This method verify the details for the given record
	public boolean verifyRowIsLinked() {
		webUtil.getDriver().navigate().refresh();
		webUtil.waitForBrowserToLoadCompletely();
		clickOnSearchBtn();
		String actualApplicantName = clickFirstRowColumnOnSearchResults();
		return (webUtil.getDriver().getCurrentUrl().contains("details") && webUtil.getText("Tgl_ApplicantNameHeading_txt").contains(actualApplicantName));
	}
	
	// Enter invalid Person id and verify the error msg 
	public boolean verifyErrorMessageForPersonID() {
		webUtil.waitUntilElementVisible("Tgl_moreSearchOptionsLink", 10);
		this.clickOnMoreSearchOptionsBtn();
		webUtil.setTextBoxValue("Tgl_personid", "abc");
		return (webUtil.getElement("Tgl_SearchPIDValidation_lbl").getText().contains("Please enter a number."));
	}

	/*
	 * This function will clicks on the first value on the webtable returns
	 * Applicant Name
	 */
	public String clickFirstRowColumnOnSearchResults() {
		String getApplicantName = webUtil.getText("Tgl_FirstRowColumn_TB");
		webUtil.click("Tgl_FirstRowColumn_TB");
		webUtil.holdOn(5);
		return getApplicantName;
	}

	/*
	 * This function will clicks on remove Button
	 */
	public void clickOnRemoveBtn() {
		webUtil.click("Tgl_Remove_btn");
	}

	/*
	 * This function will clicks on Search Button
	 */
	public void clickOnSearchBtn() {
		webUtil.waitForBrowserToLoadCompletely();
		webUtil.holdOn(5);
		webUtil.click("Tgl_Search_btn");
	}

	/*
	 * This function will clicks on Award Calculator Button
	 */
	public void clickOnAwardCalculatorBtn() {
		webUtil.click("Tgl_AwardCalculator_btn");
	}

	/*
	 * This function will enter send Email Notes
	 */
	public void enterSendEmailNotes(String locatorName, String emailNotes) {
		webUtil.setTextBoxValue(locatorName, emailNotes);
	}

	/*
	 * This function will clicks on Send email Button
	 */
	public void clickOnSendEmailBtn() {
		webUtil.click("Tgl_SendEmail_Btn");
		webUtil.holdOn(5);
	}

	/*
	 * This function will clicks on Confirm Send Button
	 */
	public void clickOnConfirmSendBtn() {
		webUtil.click("Tgl_ConfirmSend_Btn");
	}

	/*
	 * This function will selects TGL Status Button
	 */
	public void selectTGLStatusDD(String locatorNameTGLStatus) {
		webUtil.waitUntilElementVisible("Tgl_Search_btn", 10);
		webUtil.click("Tgl_TGLStatus_DD");
		webUtil.click(locatorNameTGLStatus);
	}

	/*
	 * This function will clicks on More Search option button
	 */
	public void clickOnMoreSearchOptionsBtn() {
		webUtil.click("Tgl_MoreSearchOptions_Btn");
	}

	/*
	 * This function will enters the Person ID
	 */
	public void enterPersonIDAndClickOnSearchButton(String personID) {
		webUtil.waitForBrowserToLoadCompletely();
		webUtil.waitUntilElementVisible(CLEARBTN, 10);
		webUtil.click(CLEARBTN);
		this.clickOnMoreSearchOptionsBtn();
		try {
			webUtil.setTextBoxValue("Tgl_personid", personID);
			this.clickOnSearchBtn();
			this.clickFirstRowColumnOnSearchResults();
		} catch (Exception e) {
			log.info("unable to Click on the Applicant" + e);
		}
	}

	/*
	 * This function will get the Applicant id where stage = incomplete/Complete
	 */
	public String clickApplicantNameOnSearchResults() {
		String applicantId = "";
		List<WebElement> elemList = webUtil.getElementsList("Tgl_ApplicantIncomplete_Lk");
		if(elemList.isEmpty()) {
			webUtil.click(CLEARBTN);
			List<WebElement> link=webUtil.getElementsList("Home_tgl_applicationyear");
			link.get(2).click();
			selectTGLStatusDD("Tgl_Complete_LK");
	 		clickOnSearchBtn();
	 		elemList = webUtil.getElementsList("Tgl_ApplicantIncomplete_Lk");
			applicantId = elemList.get(0).getText();
	 		elemList.get(0).click();
		}else {
			applicantId = elemList.get(0).getText();
			elemList.get(0).click();
		}
		
		return applicantId;
	}

}
