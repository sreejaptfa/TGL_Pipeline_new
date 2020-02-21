package org.tfa.tgl.pages;



import org.tfa.framework.core.WebDriverUtil;


public class SearchDetailsPageTGL {
	private WebDriverUtil webUtil=WebDriverUtil.getObject();
	
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
}
