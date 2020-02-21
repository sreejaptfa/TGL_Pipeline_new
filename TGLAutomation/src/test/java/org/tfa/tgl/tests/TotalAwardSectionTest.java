package org.tfa.tgl.tests;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.tfa.framework.core.BaseTestMethods;
import org.tfa.tgl.pages.LoginPageTgl;
import org.tfa.tgl.pages.SearchDetailsPageTGL;
import org.tfa.tgl.pages.SearchPageTGL;
import org.tfa.tgl.utilities.web.TGLConstants;
import org.tfa.tgl.utilities.web.TGLWebUtil;

/**
 **************************************************************************************************************
 * @Description  : This class Validate to verify for total award
 * @parent: BaseTestMethods class has been extended that has basic methods those will run before suite, before class,
 *          before method, after class, after method etc. 
 * @TestCase     :  TGL102TGLPortalUpload()
 * @Author: Surya
 ************************************************************************************************************** 
 */
public class TotalAwardSectionTest extends BaseTestMethods{

	private LoginPageTgl loginpage;
	private TGLWebUtil webUtil=TGLWebUtil.getObject();
	private SearchPageTGL searchPage= new SearchPageTGL();
	private SearchDetailsPageTGL searchDetailsPage = new SearchDetailsPageTGL();
	static Logger log=Logger.getLogger("rootLogger");
	
	String getActualValue;
	String enterLoanAdjustAmountValue;
	String enterGrantAdjustAmountValue;
	String enterAdjustmentCommentValue;
	String expectedValidationMessage;
	String actualValidationMessage;
	
	/**
	 **************************************************************************************************************
	 * @Description  : This function is to verify for total award
  	 * @Param: No Parameter
	 * @Return: No Return 
	 * @Author: Surya
	 **************************************************************************************************************
	 */
	
	@Test
	public void TGL106TestTotalAwardSection() throws Exception {
		
		//Step - 1 -------- Login to the TGL  portal application using valid user id < https://qamerlin.teachforamerica.org/ada
		loginpage=new LoginPageTgl();
		loginpage.enterLoginInfo();
		
		//Step - 2 -------- Search for Personid to verify Total award section who has already cal award 
		searchPage.clickOnSearchBtn();
		searchDetailsPage= searchPage.clickFirstRowColumnOnSearchResults();

		//Step - 3 -------- Now click on Manual Adjustment link
		searchDetailsPage.clickOnManuallyAdjustButton();
		
		//Step - 4 -------- Check Editable fields
		webUtil.isEnabled("Tgl_LoanAdjustment_ED");
		webUtil.isEnabled("Tgl_GrantAdjustment_ED");
		webUtil.isEnabled("Tgl_AdjustmentComment_ED");
		
		//Step - 5 -------- Now enter only loan amount
		enterLoanAdjustAmountValue = testDataMap.get("LoanAdjustmentAmount");
		searchDetailsPage.enterLoanAdjustAmount("Tgl_LoanAdjustment_ED",enterLoanAdjustAmountValue);
		getActualValue = webUtil.getAttributeValue("Tgl_AdjustmentSave_btn", "disabled");
		Assert.assertEquals(getActualValue, "true","Verified Save button is not Enable");
		
		//Step - 6 -------- Now enter grant amount
		enterGrantAdjustAmountValue = testDataMap.get("GrantAdjustmentAmount");
		searchDetailsPage.enterGrantAdjustAmount("Tgl_GrantAdjustment_ED",enterGrantAdjustAmountValue);
		getActualValue = webUtil.getAttributeValue("Tgl_AdjustmentSave_btn", "disabled");
		Assert.assertEquals(getActualValue, "true","Verified Save button is not Enable");
		
		//Step - 7 -------- Now enter comments
		enterAdjustmentCommentValue = testDataMap.get("AdjustmentComment");
		searchDetailsPage.enterAdjustmentComments("Tgl_AdjustmentComment_ED",enterAdjustmentCommentValue);
		webUtil.isEnabled("Tgl_AdjustmentSave_btn");

		//Step - 8 -------- Verify when you click on cancel 
		searchDetailsPage.clickOnCancelButton("Tgl_AdjustmentCancel_btn");
		webUtil.isVisible("Tgl_ManuallyAdjust_btn");
		
		//Step - 9 -------- Click again on manual adjustment and enter all the data and click on save
		searchDetailsPage.clickOnManuallyAdjustButton();
		searchDetailsPage.enterLoanAdjustAmount("Tgl_LoanAdjustment_ED",enterLoanAdjustAmountValue);
		searchDetailsPage.enterGrantAdjustAmount("Tgl_GrantAdjustment_ED",enterGrantAdjustAmountValue);
		searchDetailsPage.enterAdjustmentComments("Tgl_AdjustmentComment_ED",enterAdjustmentCommentValue);
		searchDetailsPage.clickOnSaveButton("Tgl_AdjustmentSave_btn");
		
		String actualPromptMessage = webUtil.getText("Tgl_PromptMessage_Msg");
		String expectedPromptMessage = testDataMap.get("expectedPromptMessage");
		Assert.assertTrue(actualPromptMessage.contains(expectedPromptMessage), "Verified the PromptMessage");

		//Step - 10 -------- Click on No
		searchDetailsPage.clickOnCancelButton("Tgl_CancelNo_btn");
		webUtil.isVisible("Tgl_AdjustmentSave_btn");
		
		//Step - 11 -------- Clicking on update
		searchDetailsPage.clickOnSaveButton("Tgl_AdjustmentSave_btn");
		searchDetailsPage.clickOnYesUpdateThisAwardButton();
		
		//Step - 12 --------  Verify all the data
		String actualAdjustedLoanAmount = webUtil.getText("Tgl_AdjustedLoanAmount_ST");
		String actualAdjustedGrantAmount = webUtil.getText("Tgl_AdjustedGrantAmount_ST");
		
		Assert.assertTrue(actualAdjustedLoanAmount.contains("200"),"Verified the Adjusted Loan");
		Assert.assertTrue(actualAdjustedGrantAmount.contains("100"),"Verified the Adjusted Grant");

		//Step - 13 --------  Now click on Remove Adjusted amount
		searchDetailsPage.clickOnRemoveAdjustmentButton();
		searchDetailsPage.clickOnYesRemoveAdjustmentButton();
		Assert.assertFalse(objectIsVisible("Tgl_RemoveAdjustment_btn"),"verified Adjustment removed");

		//Step - 14 --------  Now again click on Manual Adjusted link again and enter invalid loan and grant amount(such as dfdfd 
		searchDetailsPage.clickOnManuallyAdjustButton();
		enterGrantAdjustAmountValue = testDataMap.get("InValidLoanAdjustmentAmount_1");
		searchDetailsPage.enterLoanAdjustAmount("Tgl_LoanAdjustment_ED",enterGrantAdjustAmountValue);
		actualValidationMessage =  webUtil.getText("Tgl_ValidationMessage_ST");
		expectedValidationMessage = testDataMap.get("ValidationMessage_1");
		Assert.assertEquals(actualValidationMessage,expectedValidationMessage ,"Verified the Document Upload error");

		//Step - 15 -------- Add Manual Adjustment such as it has decimal values and save the manual adj
		enterGrantAdjustAmountValue = testDataMap.get("InValidLoanAdjustmentAmount_2");
		searchDetailsPage.enterLoanAdjustAmount("Tgl_LoanAdjustment_ED",enterGrantAdjustAmountValue);
		actualValidationMessage =  webUtil.getText("Tgl_ValidationMessage_ST");
		expectedValidationMessage = testDataMap.get("ValidationMessage_2");
		Assert.assertEquals(actualValidationMessage,expectedValidationMessage ,"Verified the Document Upload error");

		//Step - 16 -------- End
	}
	private boolean objectIsVisible(String locatorName){
		By locator=null;
		locator=webUtil.getLocatorBy(locatorName);
		try{
			if(webUtil.getDriver().findElement(locator).isEnabled()){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			return false;
		}	
	}
	@Override
	public TGLConstants getConstants(){
		return new TGLConstants();
	}
}
