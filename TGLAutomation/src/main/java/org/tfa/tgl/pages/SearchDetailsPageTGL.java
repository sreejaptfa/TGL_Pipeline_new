package org.tfa.tgl.pages;



import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.tfa.tgl.utilities.web.TGLWebUtil;


public class SearchDetailsPageTGL {
	private TGLWebUtil webUtil=TGLWebUtil.getObject();
	private WebElement element;
	
	/*
	 * This function will clicks on Upload TGLDouments
	 */
	public void clickOnUploadTGLDouments(){
		webUtil.click("Tgl_UploadTGLDocuments_btn");
	}
	/*
	 * This function will clicks on Upload Button
	 */
	public void clickOnUploadButton(){
		webUtil.click("Tgl_Upload_btn");
		webUtil.holdOn(5);
	}
	/*
	 * This function will selects the type of document
	 */
	public void clickOnTypeOfDocumentChk(String locatorName){
		webUtil.click(locatorName);
	}
	/*
	 * This function will clicks on Cancel button
	 */
	public void clickOnCancelButton(String locatorName){
		webUtil.click(locatorName);
		webUtil.holdOn(5);
	}
	/*
	 * This function will clicks on Save button
	 */
	public void clickOnSaveButton(String locatorName){
		webUtil.click(locatorName);
		webUtil.holdOn(2);
	}
	/*
	 * This function will clicks on Manually Adjust button
	 */
	public void clickOnManuallyAdjustButton(){
		webUtil.click("Tgl_ManuallyAdjust_btn");
	}
	/*
	 * This function will enter loan adjustment Amount
	 */
	public void enterLoanAdjustAmount(String locatorName,String loanAmount){
		webUtil.setTextBoxValue(locatorName, loanAmount);
	}
	/*
	 * This function will enter Grant Adjustment Amount
	 */
	public void enterGrantAdjustAmount(String locatorName,String grantAmount){
		webUtil.setTextBoxValue(locatorName, grantAmount);		
	}
	/*
	 * This function will enter Adjustment comments
	 */
	public void enterAdjustmentComments(String locatorName,String adjustComments){
		webUtil.setTextBoxValue(locatorName, adjustComments);
	}
	/*
	 * This function will clicks on Yes Update this Award Button
	 */
	public void clickOnYesUpdateThisAwardButton(){
		webUtil.click("Tgl_YesUpdateThisAward_btn");
		webUtil.holdOn(2);
	}
	/*
	 * This function will clicks on Remove adjustment Button
	 */
	public void clickOnRemoveAdjustmentButton(){
		webUtil.click("Tgl_RemoveAdjustment_btn");
	}
	/*
	 * This function will clicks on YesRemove adjustment Button
	 */
	public void clickOnYesRemoveAdjustmentButton(){
		webUtil.click("Tgl_YesRemoveAdjustment_btn");
		webUtil.holdOn(2);
	}
	/*
	 * This function will clicks on TimeLine Button
	 */
	public void clickOnTimeLineButton(){
		webUtil.click("Tgl_ViewTimeLine_Btn");
		webUtil.holdOn(2);
	}
	/*
	 * This function will Enter the Income and TotalNumber of Dependents values for Tax Information Section
	 * returns true and false
	 */
	public void enterTotalNumberOfDependentsAndIncomeAmount(){
		this.element = webUtil.getElement("Tgl_TotalNoOfDependents_ED");
		element.clear();
		element.sendKeys("10");
		element.sendKeys(Keys.ENTER);
		webUtil.holdOn(5);
		
		this.element = webUtil.getElement("Tgl_Income_ED");
		element.clear();
		element.sendKeys("100");
		element.sendKeys(Keys.ENTER);
		webUtil.holdOn(5);
		
	}
	
	/*
	 * This function will get and verify values from the webTable on Education Costs section 
	 * returns true and false
	 */
	public void verifyDocumentTypeList(String tbRowLocator,String tbColLocator,String[] expCampaignLiteMemberStatusList){
		boolean flag = webUtil.verifyTheValueInWebTableElement(tbRowLocator, tbColLocator, expCampaignLiteMemberStatusList);
		if(!flag){
			Assert.assertTrue(false, "Values are not found in WebTable");
		}
	}
	
	/*
	 * This function will get and verify values from the webTable on Education Costs section 
	 * returns true and false
	 */
	public boolean verifyDocumentTypeList(String rowLocatorName,String[] expectedValues){
		Set<String> expectedValuesSet = new HashSet<>();
		expectedValuesSet.addAll(Arrays.asList(expectedValues));
	
		Map<String, String> locatorValueMap=webUtil.getLocatorValueMap(rowLocatorName);
		String locatorValue=TGLWebUtil.getLocatorValue(locatorValueMap, rowLocatorName);
		List<WebElement> tableRowValues = webUtil.getDriver().findElements(By.xpath(locatorValue));
		for(int i = 2; i<=tableRowValues.size(); i++){
			String xPathValue = locatorValue+"["+i+"]/label";
			String elementText =  webUtil.getDriver().findElement(By.xpath(xPathValue)).getText();
			if(expectedValuesSet.contains(elementText))
			{
				expectedValuesSet.remove(elementText);
			}
		}
		if(!expectedValuesSet.isEmpty()) { 
			return false;
		}else{
			return true;	
		}

	}
	
	/*
	 * This function will Logout from TGL
	 */
	public void clickOnTGLSignOutLink(){
		webUtil.click("Tgl_logout");
	}
	/*
	 * This function will Selects the TGL Status
	 */
	private void selectTGLStatusDD(String tglStatus){
		webUtil.selectByVisibleText("Tgl_TGLStatusInput_DD", tglStatus);
		webUtil.holdOn(5);
	}
	/*
	 * This function sets the value when it selects the status Complete and Any Messages 
	 * like Total # of Dependents is blank and "Status cannot be changed
	 */
	public void clickCompleteAndFixErrorMessages(String applicantID){
		selectTGLStatusDD("Complete");
		webUtil.holdOn(5);
		boolean iflag = webUtil.objectIsVisible("Tgl_StatusValidationMessage_ST");
		if (iflag) {
			String getErrorMessageText = webUtil.getText("Tgl_StatusValidationMessage_ST");
			if((getErrorMessageText.contains("Status cannot be changed")) || (getErrorMessageText.contains("Total # of Dependents is blank"))){
				enterTotalNumberOfDependentsAndIncomeAmount();
				selectCheckBoxsForObjectValid("Tgl_SelectCheckBox_chk");
				selectTGLStatusDD("Complete");
			}
		}
		boolean iflag1 = webUtil.objectIsVisible("Tgl_StatusValidationMessage_ST");	
		if (iflag1) {
			Assert.assertTrue(false,"PLEASE CHECK MANUALLY.... THERE ARE SOME MORE ERROR MESSAGES FOR THE PERSONID -> "+applicantID);
		}
	}
	
	/*
	 * This function Verifies that the required? object isSelected on TGL Detail page, and if the Valid? checkbox 
	 * is not selects it will selects the Valid? object on the page
	 * like Total # of Dependents is blank and "Status cannot be changed
	 */
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
}
