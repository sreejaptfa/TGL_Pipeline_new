package org.tfa.tgl.tests;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.tfa.framework.core.BaseTestMethods;
import org.tfa.tgl.pages.SearchDetailsPageTGL;
import org.tfa.tgl.pages.SearchPageTGL;
import org.tfa.tgl.pages.common.LoginPageTgl;
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

	private TGLWebUtil webUtil=TGLWebUtil.getObject();
	private SearchPageTGL searchPage= new SearchPageTGL();
	private SearchDetailsPageTGL searchDetailsPage = new SearchDetailsPageTGL();
	static Logger log=Logger.getLogger("rootLogger");
	private static final String TGLLOADADJUSTMENTED="Tgl_LoanAdjustment_ED";
	private static final String TGLADJUSTMENTCOMMENTED="Tgl_AdjustmentComment_ED";
	private static final String TGLGRANTADJUSTMENTED="Tgl_GrantAdjustment_ED";
	private static final String TGLADJUSTMENTSAVEBTN="Tgl_AdjustmentSave_btn";
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
	public void tgl106TestTotalAwardSection() throws Exception {
		
		/* Step 1 - Login to the TGL  portal application using valid user id < https://qamerlin.teachforamerica.org/ada */
		LoginPageTgl loginPage = webUtil.openLoginPage();
		loginPage.enterLoginInfo();
	
		/* Step 2 - Search for Personid to verify Total award section who has already cal award */
		searchPage.clickOnSearchBtn();
		searchPage.clickFirstRowColumnOnSearchResults();

		/* Step 3 - Now click on Manual Adjustment link */
		searchDetailsPage.clickOnManuallyAdjustButton();
		
		/* Step 4 - Check Editable fields */
		webUtil.isEnabled(TGLLOADADJUSTMENTED);
		webUtil.isEnabled(TGLGRANTADJUSTMENTED);
		webUtil.isEnabled(TGLADJUSTMENTCOMMENTED);
		
		/* Step 5 - Now enter only loan amount */
		enterLoanAdjustAmountValue = testDataMap.get("LoanAdjustmentAmount");
		searchDetailsPage.enterLoanAdjustAmount(TGLLOADADJUSTMENTED,enterLoanAdjustAmountValue);
		getActualValue = webUtil.getAttributeValue(TGLADJUSTMENTSAVEBTN, "disabled");
		Assert.assertEquals(getActualValue, "true","Verified Save button is not Enable");
		
		/* Step 6 - Now enter grant amount */
		enterGrantAdjustAmountValue = testDataMap.get("GrantAdjustmentAmount");
		searchDetailsPage.enterGrantAdjustAmount(TGLGRANTADJUSTMENTED,enterGrantAdjustAmountValue);
		getActualValue = webUtil.getAttributeValue(TGLADJUSTMENTSAVEBTN, "disabled");
		Assert.assertEquals(getActualValue, "true","Verified Save button is not Enable");
		
		/* Step 7 - Now enter comments */
		enterAdjustmentCommentValue = testDataMap.get("AdjustmentComment");
		searchDetailsPage.enterAdjustmentComments(TGLADJUSTMENTCOMMENTED,enterAdjustmentCommentValue);
		webUtil.isEnabled(TGLADJUSTMENTSAVEBTN);

		/* Step 8 - Verify when you click on cancel */
		searchDetailsPage.clickOnCancelButton("Tgl_AdjustmentCancel_btn");
		webUtil.isVisible("Tgl_ManuallyAdjust_btn");
		
		/* Step 9 - Click again on manual adjustment and enter all the data and click on save */
		searchDetailsPage.clickOnManuallyAdjustButton();
		searchDetailsPage.enterLoanAdjustAmount(TGLLOADADJUSTMENTED,enterLoanAdjustAmountValue);
		searchDetailsPage.enterGrantAdjustAmount(TGLGRANTADJUSTMENTED,enterGrantAdjustAmountValue);
		searchDetailsPage.enterAdjustmentComments(TGLADJUSTMENTCOMMENTED,enterAdjustmentCommentValue);
		searchDetailsPage.clickOnSaveButton(TGLADJUSTMENTSAVEBTN);
		
		String actualPromptMessage = webUtil.getText("Tgl_PromptMessage_Msg");
		String expectedPromptMessage = testDataMap.get("expectedPromptMessage");
		Assert.assertTrue(actualPromptMessage.contains(expectedPromptMessage), "Verified the PromptMessage");

		/* Step 10 - Click on No */
		searchDetailsPage.clickOnCancelButton("Tgl_CancelNo_btn");
		webUtil.isVisible(TGLADJUSTMENTSAVEBTN);
		
		/* Step 11 - Clicking on update */
		searchDetailsPage.clickOnSaveButton(TGLADJUSTMENTSAVEBTN);
		searchDetailsPage.clickOnYesUpdateThisAwardButton();
		
		/* Step 12 - Verify all the data */
		String actualAdjustedLoanAmount = webUtil.getText("Tgl_AdjustedLoanAmount_ST");
		String actualAdjustedGrantAmount = webUtil.getText("Tgl_AdjustedGrantAmount_ST");
		
		Assert.assertTrue(actualAdjustedLoanAmount.contains("200"),"Verified the Adjusted Loan");
		Assert.assertTrue(actualAdjustedGrantAmount.contains("100"),"Verified the Adjusted Grant");

		/* Step 13 - Now click on Remove Adjusted amount */
		searchDetailsPage.clickOnRemoveAdjustmentButton();
		searchDetailsPage.clickOnYesRemoveAdjustmentButton();
		Assert.assertFalse(webUtil.objectIsVisible("Tgl_RemoveAdjustment_btn"),"verified Adjustment removed");

		/* Step 14 - Now again click on Manual Adjusted link again and enter invalid loan and grant amount(such as dfdfd */
		searchDetailsPage.clickOnManuallyAdjustButton();
		enterGrantAdjustAmountValue = testDataMap.get("InValidLoanAdjustmentAmount_1");
		searchDetailsPage.enterLoanAdjustAmount(TGLLOADADJUSTMENTED,enterGrantAdjustAmountValue);
		actualValidationMessage =  webUtil.getText("Tgl_ValidationMessage_ST");
		expectedValidationMessage = testDataMap.get("ValidationMessage_1");
		Assert.assertEquals(actualValidationMessage,expectedValidationMessage ,"Verified the Document Upload error");

		/* Step 15 - Add Manual Adjustment such as it has decimal values and save the manual adj */
		enterGrantAdjustAmountValue = testDataMap.get("InValidLoanAdjustmentAmount_2");
		searchDetailsPage.enterLoanAdjustAmount(TGLLOADADJUSTMENTED,enterGrantAdjustAmountValue);
		actualValidationMessage =  webUtil.getText("Tgl_ValidationMessage_ST");
		expectedValidationMessage = testDataMap.get("ValidationMessage_2");
		Assert.assertEquals(actualValidationMessage,expectedValidationMessage ,"Verified the Document Upload error");

		/* Step 16 - End Script */
	}

	@Override
	public TGLConstants getConstants(){
		return new TGLConstants();
	}
}
