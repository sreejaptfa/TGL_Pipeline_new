package org.tfa.tgl.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.tfa.framework.core.BaseTestMethods;
import org.tfa.tgl.pages.common.LoginPageTgl;
import org.tfa.tgl.pages.search.SearchPage;
import org.tfa.tgl.pages.searchdetails.SearchDetailsPage;
import org.tfa.tgl.utilities.web.TGLConstants;
import org.tfa.tgl.utilities.web.TGLWebUtil;

public class ValidateEducationCostSection extends BaseTestMethods {

	private TGLWebUtil webUtil = TGLWebUtil.getObject();
	private SearchPage searchPage = new SearchPage();
	private SearchDetailsPage searchDetailsPage = new SearchDetailsPage();

	/**
	 **************************************************************************************************************
	 * @throws Exception
	 * @Description : This function is to verify for Education cost works
	 * @Param: No Parameter
	 * @Return: No Return
	 * @Author: Surya
	 **************************************************************************************************************
	 */

	@Test
	public void tgl117EducationCostCalculationTest(){

		
		/* Step 1 - Login to the TGL portal application using valid user id */
		LoginPageTgl loginPage = webUtil.openLoginPage();
		loginPage.enterLoginInfo();

		
		/* Step 2 - Search for the Person Id which is going to verify Education Cost */
		webUtil.click("Tgl_TGLStatus_DD");
		webUtil.click("Tgl_Complete_LK");
		searchPage.clickOnSearchBtn();
		searchPage.clickFirstRowColumnOnSearchResults();

		/* Step 3 - Check the document type section */
		String[] expectedValues1 = { "Private Loans Amount", "Other Loans Amount" };
		String[] expectedValues2 = { "Pell Grant:", "Federal Loan Amount:", "Undergraduate University:",
				"Undergrad Degree Date:", "Total Education Cost:", "Parent Contribution:",
				"Applicant Cash Contribution:", "Parent Student Loan Amount:", "Grants and Scholarships Amount:" };
		searchDetailsPage.verifyDocumentTypeList("Tgl_SearchDetailRow_TB", "Tgl_SearchDetailCol_TB", expectedValues1);
		searchDetailsPage.verifyDocumentTypeList("Tgl_DocumenTypeSectionRow_TB", "Tgl_DocumenTypeSectionCol_TB",
				expectedValues2);

		/* Step 4 - Verify sub section for private and other loan */
		String[] expectedValues3 = { "Required?", "Valid?", "Original", "Adjusted", "Applicant Notes" };
		searchDetailsPage.verifyDocumentTypeList("Tgl_PrivateLoanSectionRow_TB", expectedValues3);
		searchDetailsPage.verifyDocumentTypeList("Tgl_OtherLoanSectionRow_TB", expectedValues3);

		/* Step 5 - Now Enter Adjusted loan and verify */
		String actualOriginalValue = webUtil.getText("Tgl_PrivateLoan_Original_ST");
		webUtil.setTextBoxValue("Tgl_PrivateLoansAmountAdjusted_ED", "100");
		String expOriginalValue = webUtil.getText("Tgl_PrivateLoan_Original_ST");
		Assert.assertEquals(actualOriginalValue, expOriginalValue, "Verify Original loan remain same");

		/* Step 6 - End Script */
	}

	@Override
	public TGLConstants getConstants() {
		return new TGLConstants();
	}
}
