package org.tfa.tgl.pages.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.tfa.framework.utilities.general.RandomUtil;
import org.tfa.framework.utilities.testdata.TestData;
import org.tfa.tgl.utilities.web.TGLWebUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

@SuppressWarnings({ "squid:S117","squid:S1192","squid:S1153","squid:S1854","squid:MethodCyclomaticComplexity",
	"squid:S134","squid:S135","squid:S3626","squid:S2696"})

public class SearchPage {
	
	private static final Logger log = Logger.getLogger(SearchPage.class);
	private TGLWebUtil webUtil = TGLWebUtil.getObject();
	private TestData data = TestData.getObject();
	SoftAssert soft = new SoftAssert();
	private RandomUtil randomUtil = new RandomUtil();


	private static final String CLEARBTN ="Tgl_Clear_btn";
	String intvDeadLineSelection;
	List<WebElement> intrDeadLineDD = null;
	int rndDeadLineNmb = 1;

	// TestCase - SearchPage - Step 11, 12, 13
	public boolean verifySearchFilter() {
		String statusSelection = null;
		int deadLineSize = 0; 
		int deadLineCnt = 0;
		int statusSize =0;
		int statusCnt = 0;
		boolean flag = false;
		boolean validOfferDeadline = true;
		webUtil.getDriver().navigate().to(data.getEnvironmentDataMap().get("ApplicationURL")) ;
		do {
			deadLineCnt = deadLineCnt+1;
			validOfferDeadline = true;
			webUtil.click(CLEARBTN);
			webUtil.holdOn(2);
			Map<String, String> deadLineDD = this.selectDropDownValue("Tgl_InterviewDeadlinefilter_txt", "Tgl_InterviewDeadLineSize_DD");
			intvDeadLineSelection = deadLineDD.get("SelectionType");
			deadLineSize = Integer.parseInt(deadLineDD.get("Size"));
			while (validOfferDeadline) {
					if(!intvDeadLineSelection.contains("2045")) {
					validOfferDeadline = false;
				}
			}
			log.info(deadLineSize +" no of records found with Interview Deadline:" + intvDeadLineSelection);
			if (deadLineSize >= 15) {
				flag = true;
				do {
					flag = false;
					statusCnt = statusCnt+1;
					Map<String, String> statusDD = this.selectDropDownValue("Tgl_TGLStatus_DD", "Tgl_TGLStatusSize_DD");
					statusSelection = statusDD.get("SelectionType");
					statusSize = Integer.parseInt(statusDD.get("Size"));
					if(statusCnt >= 10) {
						this.selectTGLStatusDD("Tgl_Complete_LK");
						statusSelection = "Complete";
						webUtil.click("Home_Tgl_Search2_btn");
						webUtil.holdOn(2);
						List<WebElement> searchResults = webUtil.getElementsList("Tgl_SearchResults_TB");
						statusSize = searchResults.size();

					}
					if (statusSize < 2) log.info(statusSize+" no of records searched for interview deadline : " + intvDeadLineSelection
								+ " and application status: " + statusSelection);
					if(statusSize < 2) {
						webUtil.click(CLEARBTN);
						webUtil.holdOn(1);
						webUtil.getElement("Tgl_InterviewDeadlinefilter_txt").click();
						intrDeadLineDD = webUtil.getElementsList("Tgl_InterviewDeadLineSize1_DD");
						intrDeadLineDD.get(rndDeadLineNmb).click();
						webUtil.holdOn(2);
					}else {
						flag = true;
					}
				if(statusCnt >=10) break;
				} while (statusSize<2);
			}
			if(deadLineCnt >=10) break;
		} while (deadLineSize<15);
		log.info(statusSize+" no of records searched for interview deadline : " + intvDeadLineSelection
				+ " and application status: " + statusSelection);
		Map<String, String> getSearchValues = enterSearhValues();
		String actualFullName = getSearchValues.get("ActualFullName");
		String expFullName = getSearchValues.get("ExpectedFullName");
		String actualPersonId = getSearchValues.get("ActualPersonalId");
		String expPersonId = getSearchValues.get("ExpectedPersonalId");
		if(!(actualFullName.equals(expFullName)) && !(actualPersonId.equals(expPersonId))) flag = false;
		Assert.assertEquals(actualFullName, expFullName, "Search result doesnot match for Name");
		Assert.assertEquals(actualPersonId, expPersonId, "Search result doesnot match for PersonId");
			log.info("Searched for name:" + actualFullName + " and personid:" + actualPersonId);
		return flag;
	}
	
	public Map<String, String> enterSearhValues(){
		Map<String,String> getSearchValues = new HashMap<>();
		List<WebElement> searchResults = webUtil.getElementsList("Tgl_SearchResults_TB");
		int sizeResults = searchResults.size();
		int rndResultsNmb = randomUtil.generateRandomNumber(1, sizeResults);

		String expFullName = webUtil.getDriver()
				.findElement(By.xpath("//tbody[@data-hook='results']/tr[" + String.valueOf(rndResultsNmb) + "]/td[1]/a"))
				.getText();
		String expPersonId = webUtil.getDriver()
				.findElement(By.xpath("//tbody[@data-hook='results']/tr[" + String.valueOf(rndResultsNmb) + "]/td[2]/a"))
				.getText();
		String[] splitFullName = expFullName.split(",");
		String firstName = splitFullName[1].trim();
		String lastName = splitFullName[0].trim();	
		webUtil.click("Tgl_moreSearchOptionsLink");	
		webUtil.setTextBoxValue("Tgl_firstname", firstName);
		webUtil.setTextBoxValue("Tgl_lastname", lastName);
		webUtil.setTextBoxValue("Tgl_personid", expPersonId);
		webUtil.click("Home_Tgl_Search2_btn");
		
		searchResults = webUtil.getElementsList("Tgl_firstrow_name");
		String actualFullName = searchResults.get(0).getText();
		String actualPersonId = searchResults.get(1).getText();
		
		getSearchValues.put("ActualFullName", actualFullName);
		getSearchValues.put("ExpectedFullName", expFullName);
		getSearchValues.put("ActualPersonalId", actualPersonId);
		getSearchValues.put("ExpectedPersonalId", expPersonId);
		return getSearchValues;

	}
	public Map<String, String> selectDropDownValue(String typeDD, String typeSize) {
		Map<String,String> getValuesMap = new HashMap<>();
		String typeCount = "0";
		webUtil.holdOn(2);
		webUtil.getElement(typeDD).click();
		webUtil.holdOn(2);
		List<WebElement> tglStatusDD = webUtil.getElementsList(typeSize);
		int sizeStatus = tglStatusDD.size();
		int rndStatusNmb = randomUtil.generateRandomNumber(0, sizeStatus);
		String selectionType = tglStatusDD.get(rndStatusNmb).getText();
		if (!selectionType.isEmpty()) {
			tglStatusDD.get(rndStatusNmb).click();
		}else {
			tglStatusDD.get(1).click();	
		}
		log.debug(selectionType);
		webUtil.holdOn(2);
		webUtil.click("Home_Tgl_Search2_btn");
		webUtil.holdOn(2);
		String getResultTableValue= webUtil.getElement("Tgl_SearchResultsTable_TB").getText();
		if (getResultTableValue.contains("APPLICANT")) {
			List<WebElement> searchResults = webUtil.getElementsList("Tgl_SearchResults_TB");
			typeCount = String.valueOf(searchResults.size());
		}
		getValuesMap.put("SelectionType", selectionType);
		getValuesMap.put("Size", typeCount);
		return getValuesMap;

		
	}
	// This function is to verify default sort on the name field
	public boolean verifydefaultsort(){
		boolean flag = false;
		ArrayList<String> nameResults = new ArrayList<>();
		ArrayList<String> subNames = new ArrayList<>();
		String[] searchName = {"a","e","d"};
		webUtil.getDriver().navigate().refresh();
		webUtil.waitUntilElementVisible(CLEARBTN);
		webUtil.click(CLEARBTN);
		this.clickOnMoreSearchOptionsBtn();
		
		try {
			int len = searchName.length;
			for(int i =0;i<=len-1;i++) {
				webUtil.setTextBoxValue("Tgl_firstname", searchName[i]);
				clickOnSearchBtn();
				List<WebElement> searchresults = webUtil.getElementsList("Tgl_SearchResultsName_TB");
				if (searchresults.size() > 4) {
					for (int j = 0; j <= searchresults.size()-1;j++) {
						nameResults.add(searchresults.get(j).getText());
						subNames.add(nameResults.get(j).substring(0,3));
						flag = true;
					}
					break;
				}
			}
		} catch (Exception e) {
			log.info("No records found",e);
			flag = false;
		}
		if(flag) webUtil.isSorted(subNames);
		return flag;
	}
	// This method verify the default appyear selection 
	public boolean verifyAppYearDefaultSelection() {
		Select appYearDD= new Select(webUtil.getElement("Tgl_appyear_dd"));
		return appYearDD.getFirstSelectedOption().getText().contains("2021");
	}

	public void setAppYearBlank() {
		webUtil.selectByIndex("Tgl_appyear_dd", 0);
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
		String[] columnHeaders = data.getTestCaseDataMap().get("ColumnHeaders").split(";");
		List<WebElement> actualColumnHeader = webUtil.getElementsList("Tgl_ColumnHeaders_TB");
		for (int i = 0; i <= actualColumnHeader.size()-1; i++) {
			if (actualColumnHeader.get(i).getText().equals(columnHeaders[i])) {
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
		webUtil.waitUntilElementVisible("Tgl_moreSearchOptionsLink");
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
		webUtil.waitUntilElementVisible("Tgl_Search_btn");
		webUtil.click("Tgl_Search_btn");
		webUtil.holdOn(2);
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
		webUtil.waitUntilElementVisible(CLEARBTN);
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
