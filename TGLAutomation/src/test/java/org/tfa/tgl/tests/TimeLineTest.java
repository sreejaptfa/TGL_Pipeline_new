package org.tfa.tgl.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.tfa.framework.core.BaseTestMethods;
import org.tfa.framework.utilities.general.RandomUtil;
import org.tfa.tgl.pages.LoginPageTgl;
import org.tfa.tgl.pages.SearchDetailsPageTGL;
import org.tfa.tgl.pages.SearchPageTGL;
import org.tfa.tgl.utilities.web.TGLConstants;
import org.tfa.tgl.utilities.web.TGLWebUtil;

public class TimeLineTest extends BaseTestMethods{

	private LoginPageTgl loginpage;
	private TGLWebUtil webUtil=TGLWebUtil.getObject();
	private RandomUtil random=new RandomUtil();
	private SearchPageTGL searchPage= new SearchPageTGL();
	private SearchDetailsPageTGL searchDetailsPage = new SearchDetailsPageTGL();
	
	@Test
	public void TGL11125TestTimeLine() throws Exception {
		
		//Step - 1 -------- Login to TGL portal
		loginpage=new LoginPageTgl();
		loginpage.enterLoginInfo();
		
		//Step - 2 -------- Go go to TGL search and search for applicant whose TGL app is completed in Online part 2
		searchPage.clickOnTGLStatusDD();
		searchPage.clickOnCompleteLink();
		searchPage.clickOnSearchBtn();
		searchDetailsPage= searchPage.clickFirstRowColumnOnSearchResults();
		
		//Step - 3 -------- Now click on Manual Adjustment link
		searchDetailsPage.clickOnManuallyAdjustButton();
		String enterLoanAdjustAmountValue = testDataMap.get("LoanAdjustmentAmount");
		String enterGrantAdjustAmountValue = testDataMap.get("GrantAdjustmentAmount");
		String enterAdjustmentCommentValue= random.generateRandomString(5)+random.generateRandomNumber(5);
		searchDetailsPage.enterLoanAdjustAmount("Tgl_LoanAdjustment_ED",enterLoanAdjustAmountValue);
		searchDetailsPage.enterGrantAdjustAmount("Tgl_GrantAdjustment_ED",enterGrantAdjustAmountValue);
		searchDetailsPage.enterAdjustmentComments("Tgl_AdjustmentComment_ED",enterAdjustmentCommentValue);
		searchDetailsPage.clickOnSaveButton("Tgl_AdjustmentSave_btn");
		searchDetailsPage.clickOnYesUpdateThisAwardButton();
		
		//Step - 4 -------- Click on Detail link and verify all the story
		searchDetailsPage.clickOnTimeLineButton();
		String actualValueMsg = webUtil.getText("Tgl_TimeLine_TB");
		Assert.assertTrue(actualValueMsg.contains(enterAdjustmentCommentValue), "Verify Time Line adds the Row");
		
		//Step - 5 -------- End

	}
	@Override
	public TGLConstants getConstants(){
		return new TGLConstants();
	}
}