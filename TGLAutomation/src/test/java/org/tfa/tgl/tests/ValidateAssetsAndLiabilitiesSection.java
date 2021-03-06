package org.tfa.tgl.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.tfa.framework.core.BaseTestMethods;
import org.tfa.framework.utilities.testdata.TestData;
import org.tfa.tgl.pages.common.LoginPageTgl;
import org.tfa.tgl.pages.search.SearchPage;
import org.tfa.tgl.pages.searchdetailsection.AssetsAndLiabilitiesSection;
import org.tfa.tgl.utilities.web.TGLConstants;
import org.tfa.tgl.utilities.web.TGLWebUtil;

public class ValidateAssetsAndLiabilitiesSection extends BaseTestMethods{
	

	private SearchPage searchPage = new SearchPage();
	private AssetsAndLiabilitiesSection assetsliabilities= new AssetsAndLiabilitiesSection();
	private TestData data = TestData.getObject();
	SoftAssert soft = new SoftAssert();
	private TGLWebUtil webUtil = TGLWebUtil.getObject();
	
	/**
	 **************************************************************************************************************
	 * @Description : This test verifies Assets and Liabilities section labels
	 * @Param: Login credential with admin role
	 * @Return: No Return
	 * @Author: Nitin Sharma 
	 **************************************************************************************************************
	 */
	
	@Test
	public void tgl102AssetsAndLiabilitiesSectionTest(){	
	
		String adjustedAmount = data.getTestCaseDataMap().get("AdjustedAmount");
	
		//Step 1 - Login to TGL portal
		LoginPageTgl loginPage = webUtil.openLoginPage();
		loginPage.enterLoginInfo();
		
		//Step 2 - Click on the Search Button
		webUtil.waitUntilElementVisible("Tgl_Search_btn");
		searchPage.clickOnSearchBtn();
		
		//Step 3 - Click on the Applicant
		searchPage.clickFirstRowColumnOnSearchResults();
		
		//Step 4  - Verify Assets and Liabilities Section 
		soft.assertTrue(webUtil.verifyDocumentInformationSection("AssetsAndLiabilitiesSection"),"Assets And Liabilities Section");
	
		//Step 5  - verify "original" values dont change for Savings 
		String actOriginalValue = assetsliabilities.getOrignalValue("Tgl_SavingsOriginal_lbl");
		assetsliabilities.enterAdjustedAmount("Tgl_SavingsAdjusted_text", adjustedAmount);
		Assert.assertTrue(actOriginalValue.contains(webUtil.getElement("Tgl_SavingsOriginal_lbl").getText()));
		
		//Step 6  - verify "original" values dont change Credit card
		actOriginalValue = assetsliabilities.getOrignalValue("Tgl_CreditOriginal_lbl");
		assetsliabilities.enterAdjustedAmount("Tgl_CreditAdjusted_text", adjustedAmount);
		Assert.assertTrue(actOriginalValue.contains(webUtil.getElement("Tgl_CreditOriginal_lbl").getText()));
	}
	
	@Override
	public TGLConstants getConstants()	{
		return new TGLConstants();
	}

}
