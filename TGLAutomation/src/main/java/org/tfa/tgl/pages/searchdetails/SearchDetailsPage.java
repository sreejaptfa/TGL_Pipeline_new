package org.tfa.tgl.pages.searchdetails;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.tfa.framework.utilities.general.RandomUtil;
import org.tfa.tgl.utilities.web.TGLWebUtil;

@SuppressWarnings({"squid:S3776"})
public class SearchDetailsPage {
	private TGLWebUtil webUtil = TGLWebUtil.getObject();
	private RandomUtil random = new RandomUtil();
	private static final String CHECK="Check";

	// This function will clicks on Upload TGLDouments
	public void clickOnUploadTGLDouments() {
		webUtil.click("Tgl_UploadTGLDocuments_btn");
	}

	// This function will clicks on Upload Button
	public void clickOnUploadButton() {
		webUtil.click("Tgl_Upload_btn");
		webUtil.holdOn(5);
	}

	// This function will selects the type of document
	public void clickOnTypeOfDocumentChk(String locatorName) {
		webUtil.click(locatorName);
	}

	// This function will clicks on Cancel button
	public void clickOnCancelButton(String locatorName) {
		webUtil.click(locatorName);
		webUtil.holdOn(5);
	}

	// This function will clicks on Save button
	public void clickOnSaveButton(String locatorName) {
		webUtil.click(locatorName);
		webUtil.holdOn(2);
	}

	// This function will clicks on Manually Adjust button
	public void clickOnManuallyAdjustButton() {
		webUtil.click("Tgl_ManuallyAdjust_btn");
	}

	// This function will enter loan adjustment Amount
	public void enterLoanAdjustAmount(String locatorName, String loanAmount) {
		webUtil.setTextBoxValue(locatorName, loanAmount);
	}

	// This function will enter Grant Adjustment Amount
	public void enterGrantAdjustAmount(String locatorName, String grantAmount) {
		webUtil.setTextBoxValue(locatorName, grantAmount);
	}

	// This function will enter Adjustment comments
	public void enterAdjustmentComments(String locatorName, String adjustComments) {
		webUtil.setTextBoxValue(locatorName, adjustComments);
	}

	// This function will clicks on Yes Update this Award Button
	public void clickOnYesUpdateThisAwardButton() {
		webUtil.click("Tgl_YesUpdateThisAward_btn");
		webUtil.holdOn(2);
	}

	// This function will clicks on Remove adjustment Button
	public void clickOnRemoveAdjustmentButton() {
		webUtil.click("Tgl_RemoveAdjustment_btn");
	}

	// This function will clicks on YesRemove adjustment Button
	public void clickOnYesRemoveAdjustmentButton() {
		webUtil.click("Tgl_YesRemoveAdjustment_btn");
		webUtil.holdOn(2);
	}

	// This function will clicks on TimeLine Button
	public void clickOnTimeLineButton() {
		webUtil.click("Tgl_ViewTimeLine_Btn");
		webUtil.holdOn(2);
	}

	// This function will Enter the Income and TotalNumber of Dependents values for
	// Tax Information Section returns true and false
	public void enterTotalNumberOfDependentsAndIncomeAmount(String totalDependents, String income) {
	
		WebElement element = webUtil.getElement("Tgl_TotalNoOfDependents_ED");
		element.clear();
		element.sendKeys(totalDependents);
		element.sendKeys(Keys.ENTER);
		webUtil.holdOn(5);

		element = webUtil.getElement("Tgl_Income_ED");
		element.clear();
		element.sendKeys(income);
		element.sendKeys(Keys.ENTER);
		webUtil.holdOn(5);

	}

	// This function will get and verify values from the webTable on Education Costs. section returns true and false
	public void verifyDocumentTypeList(String tbRowLocator, String tbColLocator,
			String[] expCampaignLiteMemberStatusList) {
		boolean flag = webUtil.verifyTheValueInWebTableElement(tbRowLocator, tbColLocator,
				expCampaignLiteMemberStatusList);
		if (!flag) {
			Assert.assertTrue(false, "Values are not found in WebTable");
		}
	}

	// This function will get and verify values from the webTable on Education Costs. returns true and false
	public boolean verifyDocumentTypeList(String rowLocatorName, String[] expectedValues) {
		Set<String> expectedValuesSet = new HashSet<>();
		expectedValuesSet.addAll(Arrays.asList(expectedValues));

		Map<String, String> locatorValueMap = webUtil.getLocatorValueMap(rowLocatorName);
		String locatorValue = TGLWebUtil.getLocatorValue(locatorValueMap, rowLocatorName);
		List<WebElement> tableRowValues = webUtil.getDriver().findElements(By.xpath(locatorValue));
		for (int i = 2; i <= tableRowValues.size(); i++) {
			String xPathValue = locatorValue + "[" + i + "]/label";
			String elementText = webUtil.getDriver().findElement(By.xpath(xPathValue)).getText();
			if (expectedValuesSet.contains(elementText)) {
				expectedValuesSet.remove(elementText);
			}
		}
		return expectedValuesSet.isEmpty();

	}

	// gets the Assignment value on the TGL Page
	public String getAssignmentValue(String locatorName) {
		String asignmentValue = webUtil.getText(locatorName);
		String[] arrSplit = asignmentValue.split(": ");
		return arrSplit[1];
	}

	// This function will Logout from TGL
	public void clickOnTGLSignOutLink() {
		webUtil.click("Tgl_logout");
	}

	// This function will Selects the TGL Status
	public void selectTGLStatusDD(String tglStatus) {
		webUtil.selectByVisibleText("Tgl_TGLStatusInput_DD", tglStatus);
		webUtil.holdOn(5);
	}

	// This function sets the value when it selects the status Complete and Any
	// Messages like Total # of Dependents is blank and "Status cannot be changed
	public void clickCompleteAndFixErrorMessages(String applicantID) {
		this.selectTGLStatusDD("Complete");
		webUtil.holdOn(5);
		String getErrorMessageText = webUtil.getElement("Tgl_ApplicantValidMessage_Lk").getText();
		if ((getErrorMessageText.contains("Status cannot be changed")) || (getErrorMessageText.contains("Total # of Dependents is blank"))) {
			enterTotalNumberOfDependentsAndIncomeAmount("10", "2500");
			selectCheckBoxsForObjectValid("Tgl_SelectCheckBox_chk",CHECK);
			selectTGLStatusDD("Complete");
			getErrorMessageText = webUtil.getElement("Tgl_ApplicantValidMessage_Lk").getText();
			if (getErrorMessageText.contains("Status cannot be changed to complete if Income or Total # of Dependents is blank.")){
				Assert.assertTrue(false,"PLEASE CHECK MANUALLY.... THERE ARE SOME MORE ERROR MESSAGES FOR THE PERSONID -> "
								+ applicantID);
			}
		}
	}

	// This function Verifies that the required? object isSelected on TGL Detail page, and if the Valid? 
	// checkbox is not selects it will selects the Valid? object on the page like Total # of 
	// Dependents is blank and "Status cannot be changed
	public void selectCheckBoxsForObjectValid(String locatorName, String selectValidChk) {
		Map<String, String> locatorValueMap = webUtil.getLocatorValueMap(locatorName);
		String locatorValue = TGLWebUtil.getLocatorValue(locatorValueMap, locatorName);
		List<WebElement> getValues = webUtil.getDriver().findElements(By.xpath(locatorValue));
		for (int i = 1; i <= getValues.size(); i++) {
			WebElement checkBoxRequired = webUtil.getDriver().findElement(By.xpath("(" + locatorValue + "[" + i + "]//input)[1]"));
			if (checkBoxRequired.isSelected()) {
				WebElement checkBoxValid = webUtil.getDriver().findElement(By.xpath("(" + locatorValue + "[" + i + "]//input)[2]"));
				switch (selectValidChk) {
					case "Uncheck":
						if(checkBoxValid.isSelected()) webUtil.click(checkBoxValid);
						break;
					case CHECK:
						if (!checkBoxValid.isSelected()) webUtil.click(checkBoxValid);
						break;
					default:
						break;
				}
			}
		}
	}
	
 
	// This function will click on the valid checkbox and enter the notes and return the locator element
	public Map<String, String> checkValidCheckBoxAndEnterNotes(String[] sections, String selectType) {
		String getlocatorValue;
		String checkBoxValue;
		boolean checkValue = false;
		String locatorValue = null;
		String locatorValue1 = null;
		String selectedSection = null;
		String getSectionName = null;
		String checkBoxChecked = "true";
		String enterNotes = "Checked Documentation Verified CheckBox-" + random.generateRandomNumber(5);
		Map<String, String> objectMap = new HashMap<>();
		int len = sections.length - 1;
		for (int i = 0; i <= len; i++) {
			Map<String, String> locatorValueMap = webUtil.getLocatorValueMap(sections[i]);
			locatorValue = TGLWebUtil.getLocatorValue(locatorValueMap, sections[i]);
			WebElement checkBoxRequired = webUtil.getDriver().findElement(By.xpath("(" + locatorValue + "/tbody/tr//input)[1]"));
			if (!checkBoxRequired.isSelected())
				webUtil.click(checkBoxRequired);
			if (checkBoxRequired.isSelected()) {
				WebElement checkBoxValid = webUtil.getDriver().findElement(By.xpath("(" + locatorValue + "/tbody/tr//input)[2]"));
				locatorValue1 = TGLWebUtil.getLocatorValue(locatorValueMap, sections[i]);
				switch (selectType) {
				case "UnCheck":
					if (checkBoxValid.isSelected())
						webUtil.click(checkBoxValid);
					checkBoxChecked = null;
					selectedSection = sections[i];
					getSectionName = getSectionName(selectedSection);
					enterNotes = "";
					enterTestComment(locatorValue1, enterNotes);
					checkValue = true;
					break;
				case CHECK:
					if (!checkBoxValid.isSelected()) {
						webUtil.click(checkBoxValid);
						checkBoxChecked = "true";
						selectedSection = sections[i];
						getSectionName = getSectionName(selectedSection);
						enterTestComment(locatorValue1, enterNotes);
						checkValue = true;
						break;
					} else {
						selectedSection = sections[i];
						getSectionName = getSectionName(selectedSection);
						checkValue = false;
						break;
					}
				default:
					break;
				}
			}
		}
		if (!checkValue) {
			getlocatorValue = locatorValue1;
			getSectionName = getSectionName(selectedSection);// NOSONAR
			WebElement setApplicationNotesObject = webUtil.getDriver().findElement(By.xpath("(" + getlocatorValue + "/tbody/tr//textarea)"));
			setApplicationNotesObject.clear();
			setApplicationNotesObject.sendKeys(enterNotes);
			setApplicationNotesObject.sendKeys(Keys.ENTER);
			webUtil.holdOn(10);
		}
		checkBoxValue = selectedSection;
		objectMap.put("Notes", enterNotes);
		objectMap.put("Section", checkBoxValue);
		objectMap.put("SectionName", getSectionName);
		objectMap.put("ValidCheckBox", checkBoxChecked);
		return objectMap;
	}

	//This function will returns the document section names return the getSectionName
	public String getSectionName(String name) {
		String getSectionName = null;
		if (name.equals("Tgl_PrivateLoan_Section"))
			getSectionName = "Private Loan";
		if (name.equals("Tgl_OtherLoan_Section"))
			getSectionName = "Other Loan";
		if (name.equals("Tgl_Savings_Section"))
			getSectionName = "Savings";
		if (name.equals("Tgl_Credit_Section"))
			getSectionName = "Credit Card debt";
		if (name.equals("Tgl_ApplicantTax_Section"))
			getSectionName = "Applicant's Tax Return";
		if (name.equals("Tgl_W2Income_Section"))
			getSectionName = "W-2 or Income Statement";
		if (name.equals("Tgl_ParentTax_section"))
			getSectionName = "Parent's Tax Return";
		if (name.equals("Tgl_ParentIncome_Section"))
			getSectionName = "Parent Income Statement";
		return getSectionName;
	}

	private void enterTestComment(String locatorName, String enterNotes) {
		WebElement setApplicationNotesObject = webUtil.getDriver().findElement(By.xpath("(" + locatorName + "/tbody/tr//textarea)"));
		setApplicationNotesObject.clear();
		setApplicationNotesObject.sendKeys(enterNotes);
		setApplicationNotesObject.sendKeys(Keys.ENTER);
		webUtil.holdOn(10);
	}
	
}
