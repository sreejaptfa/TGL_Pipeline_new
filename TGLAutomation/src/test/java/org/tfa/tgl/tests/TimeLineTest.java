package org.tfa.tgl.tests;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.tfa.framework.core.BaseTestMethods;
import org.tfa.framework.utilities.general.RandomUtil;
import org.tfa.tgl.pages.SearchDetailsPageTGL;
import org.tfa.tgl.pages.SearchPageTGL;
import org.tfa.tgl.pages.common.LoginPageTgl;
import org.tfa.tgl.utilities.web.TGLConstants;
import org.tfa.tgl.utilities.web.TGLWebUtil;

public class TimeLineTest extends BaseTestMethods{

	private TGLWebUtil webUtil=TGLWebUtil.getObject();
	private RandomUtil random=new RandomUtil();
	private SearchPageTGL searchPage= new SearchPageTGL();
	private SearchDetailsPageTGL searchDetailsPage = new SearchDetailsPageTGL();
	
	/**
	 **************************************************************************************************************
	 * @Description  : This function is to verify the TimeLine Comments
  	 * @Param: No Parameter
	 * @Return: No Return 
	 * @Author: Surya
	 **************************************************************************************************************
	 */
	@Test
	public void tgl11125TestTimeLine() throws Exception {
		
		/* Step 1 - Login to TGL portal */
		LoginPageTgl loginPage = webUtil.openLoginPage();
		loginPage.enterLoginInfo();
		
		/* Step 2 - Go go to TGL search and search for applicant whose TGL app is completed in Online part 2 */
		searchPage.selectTGLStatusDD("Tgl_Complete_LK");
		searchPage.clickOnSearchBtn();
		searchPage.clickFirstRowColumnOnSearchResults();
		
		/* Step 3 - Now click on Manual Adjustment link */
		searchDetailsPage.clickOnManuallyAdjustButton();
		String enterLoanAdjustAmountValue = testDataMap.get("LoanAdjustmentAmount");
		String enterGrantAdjustAmountValue = testDataMap.get("GrantAdjustmentAmount");
		String enterAdjustmentCommentValue= random.generateRandomString(5)+random.generateRandomNumber(5);
		searchDetailsPage.enterLoanAdjustAmount("Tgl_LoanAdjustment_ED",enterLoanAdjustAmountValue);
		searchDetailsPage.enterGrantAdjustAmount("Tgl_GrantAdjustment_ED",enterGrantAdjustAmountValue);
		searchDetailsPage.enterAdjustmentComments("Tgl_AdjustmentComment_ED",enterAdjustmentCommentValue);
		searchDetailsPage.clickOnSaveButton("Tgl_AdjustmentSave_btn");
		searchDetailsPage.clickOnYesUpdateThisAwardButton();
		
		/* Step 4 - Click on Detail link and verify all the story */
		Assert.assertTrue(verifyTimeLineComments(enterAdjustmentCommentValue), "Verify Time Line added the Row");

		/* Step 5 - End Script */

	}
	//Verify the Timeline comments
	private boolean verifyTimeLineComments(String adjustmentComment) {
		boolean flag = false;
		searchDetailsPage.clickOnTimeLineButton();
		List<WebElement> totalCnt=webUtil.getElementsList("Tgl_TimeLine_TB");
		for(int i = 0; i<totalCnt.size(); i++){
			WebElement msg =  totalCnt.get(i);
			if(msg.getText().contains(adjustmentComment)) {
				flag = true;
				break;
			}
		}
		return flag;
	}
	@Override
	public TGLConstants getConstants(){
		return new TGLConstants();
	}
}
