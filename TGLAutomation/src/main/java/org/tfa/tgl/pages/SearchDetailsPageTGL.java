package org.tfa.tgl.pages;



import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.tfa.tgl.utilities.web.TGLWebUtil;


public class SearchDetailsPageTGL {
	private TGLWebUtil webUtil=TGLWebUtil.getObject();
	
	public void clickOnUploadTGLDouments(){
		webUtil.click("Tgl_UploadTGLDocuments_btn");
	}
	public void clickOnUploadButton(){
		webUtil.click("Tgl_Upload_btn");
		webUtil.holdOn(5);
	}
	public void clickOnTypeOfDocumentChk(String locatorName){
		webUtil.click(locatorName);
	}
	public void clickOnCancelButton(String locatorName){
		webUtil.click(locatorName);
		webUtil.holdOn(5);
	}
	public void clickOnSaveButton(String locatorName){
		webUtil.click(locatorName);
		webUtil.holdOn(2);
	}
	public void clickOnManuallyAdjustButton(){
		webUtil.click("Tgl_ManuallyAdjust_btn");
	}
	public void enterLoanAdjustAmount(String locatorName,String loanAmount){
		webUtil.setTextBoxValue(locatorName, loanAmount);
	}
	public void enterGrantAdjustAmount(String locatorName,String grantAmount){
		webUtil.setTextBoxValue(locatorName, grantAmount);		
	}
	public void enterAdjustmentComments(String locatorName,String adjustComments){
		webUtil.setTextBoxValue(locatorName, adjustComments);
	}
	public void clickOnYesUpdateThisAwardButton(){
		webUtil.click("Tgl_YesUpdateThisAward_btn");
		webUtil.holdOn(2);
	}
	public void clickOnRemoveAdjustmentButton(){
		webUtil.click("Tgl_RemoveAdjustment_btn");
	}
	public void clickOnYesRemoveAdjustmentButton(){
		webUtil.click("Tgl_YesRemoveAdjustment_btn");
		webUtil.holdOn(2);
	}
	public void clickOnTimeLineButton(){
		webUtil.click("Tgl_ViewTimeLine_Btn");
		webUtil.holdOn(2);
	}
	public void verifyDocumentTypeList(String tbRowLocator,String tbColLocator,String[] expCampaignLiteMemberStatusList){
		boolean flag = webUtil.verifyTheValueInWebTableElement(tbRowLocator, tbColLocator, expCampaignLiteMemberStatusList);
		if(!flag){
			Assert.assertTrue(false, "Values are not found in WebTable");
		}
	}
	
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
}
