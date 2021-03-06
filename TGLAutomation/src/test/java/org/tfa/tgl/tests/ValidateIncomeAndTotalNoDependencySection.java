package org.tfa.tgl.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.tfa.framework.core.BaseTestMethods;
import org.tfa.tgl.pages.common.LoginPageTgl;
import org.tfa.tgl.pages.search.SearchPage;
import org.tfa.tgl.pages.searchdetails.SearchDetailsPage;
import org.tfa.tgl.pages.searchdetailsection.IncomeAndTotalNoDependencySection;
import org.tfa.tgl.utilities.web.TGLConstants;
import org.tfa.tgl.utilities.web.TGLWebUtil;

public class ValidateIncomeAndTotalNoDependencySection extends BaseTestMethods {
	
	private TGLWebUtil webUtil = TGLWebUtil.getObject();
	private SearchPage searchPage = new SearchPage();
	private SearchDetailsPage searchDetailsPage = new SearchDetailsPage();
	private IncomeAndTotalNoDependencySection income = new IncomeAndTotalNoDependencySection();
	
	/**
	 **************************************************************************************************************
	 * @Description : This test verifies No Of Dependents section and relevant error messages 
	 * @Param: Login credential with admin role 
	 * @Return: No Return
	 * @Author: Nitin Sharma 
	 **************************************************************************************************************
	 */

	@Test
	public void tgl109IncomeAndDependencyTest() throws Exception	{	
		
		// Step 1 - Login to TGL portal 
		LoginPageTgl loginPage = webUtil.openLoginPage();
		loginPage.enterLoginInfo();
		
		webUtil.waitForBrowserToLoadCompletely();
		webUtil.waitUntilElementVisible("Tgl_Clear_btn");
		webUtil.click("Tgl_Clear_btn");
		searchPage.selectTGLStatusDD("Tgl_InComplete_LK");
		searchPage.selectTGLStatusDD("Tgl_InProgress_LK");
		searchPage.clickOnSearchBtn();
		searchPage.clickFirstRowColumnOnSearchResults();
		
		//TestCase - Enter Income and NoOf Dependent and check all Valid Checkbox and change the Status to Complete 
		//Verify there no error message Step - 3
		searchDetailsPage.enterTotalNumberOfDependentsAndIncomeAmount("10","2500");
		searchDetailsPage.selectCheckBoxsForObjectValid("Tgl_SelectCheckBox_chk","Check");
		Assert.assertTrue(income.verifyValidationMessageForStatusChange("No Message","Complete"), "Verify IncomeAndTotalNodependencySection Method failed");								
		
		//TestCase - Clear Income and No of Dependent and change the Status to Complete 
		//Verify there no error message Step - 4
		searchDetailsPage.selectTGLStatusDD("In Progress");
		searchDetailsPage.enterTotalNumberOfDependentsAndIncomeAmount(" "," ");
		Assert.assertTrue(income.verifyValidationMessageForStatusChange("Valid Message","Complete"), "Verify IncomeAndTotalNodependencySection Method failed");								

		//TestCase - Enter Income and NoOf Dependent as Zero and change the Status to Complete 
		//Verify there no error message Step - 4
		searchDetailsPage.selectTGLStatusDD("In Progress");
		searchDetailsPage.enterTotalNumberOfDependentsAndIncomeAmount("0","0");
		Assert.assertTrue(income.verifyValidationMessageForStatusChange("No Message","Complete"), "Verify IncomeAndTotalNodependencySection Method failed");								
	}	
	@Override
	public TGLConstants getConstants()	{
		return new TGLConstants();
	}

}