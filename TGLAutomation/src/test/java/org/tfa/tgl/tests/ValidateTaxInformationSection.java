package org.tfa.tgl.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.tfa.framework.core.BaseTestMethods;
import org.tfa.tgl.pages.common.LoginPageTgl;
import org.tfa.tgl.pages.search.SearchPage;
import org.tfa.tgl.pages.searchdetailsection.TaxInformationSection;
import org.tfa.tgl.utilities.web.TGLConstants;
import org.tfa.tgl.utilities.web.TGLWebUtil;

public class ValidateTaxInformationSection extends BaseTestMethods {

	private TGLWebUtil webUtil = TGLWebUtil.getObject();
	private SearchPage searchPage = new SearchPage();
	private TaxInformationSection taxInfoSection = new TaxInformationSection();
	SoftAssert soft = new SoftAssert();
	
	/**
	 **************************************************************************************************************
	 * @Description : This test verifies Tax Information Section
	 * @Param: Login credential with admin role 
	 * @Author: Nitin Sharma 
	 **************************************************************************************************************
	 */
	
	@Test
	public void tgl104ValidateTaxlInformationSection() {
		
		//Step 1 - Login to the TGL portal application using valid user id <
		LoginPageTgl loginPage = webUtil.openLoginPage();
		loginPage.enterLoginInfo();
		
		//Step 2 - Click on the Search Button
		searchPage.clickOnSearchBtn();
		searchPage.clickFirstRowColumnOnSearchResults();
		
		//Step 3 - verifyTaxInformationSection
		soft.assertTrue(webUtil.verifyDocumentInformationSection("TaxInformationSection"));

		//Step 4 - Verify TaxInformation Documents - Applicant's tax return
		soft.assertTrue(webUtil.verifyDocumentInformationSection("ApplicantTaxReturn"));
		
		//Step 5 - Add test comments to TaxInformation Documents - Applicant's tax return
		String expTextComment =taxInfoSection.enterTextComment("Tgl_TaxInfoAppTaxReturnAppNotes_txt");
		String actTextComment = taxInfoSection.getTextFromElement("Tgl_TaxInfoAppTaxReturnAppNotes_txt");
		Assert.assertTrue(actTextComment.contains(expTextComment));
		
		//Step 6 - Verify Tax Information Document section - W2 or Income Statement
		soft.assertTrue(webUtil.verifyDocumentInformationSection("W2IncomeStatement"));
		
		//Step 7 - Add test comments to Tax Information Document section - W2 or Income Statement
		expTextComment =taxInfoSection.enterTextComment("Tgl_TaxInfoIncomeAppNotes_txt");
		actTextComment = taxInfoSection.getTextFromElement("Tgl_TaxInfoIncomeAppNotes_txt");
		Assert.assertTrue(actTextComment.contains(expTextComment));

		//Step 8 - Verify Tax Information Document section - Parent's Tax Return
		soft.assertTrue(webUtil.verifyDocumentInformationSection("ParentTaxReturn"));
		
		//Step 9 - Add test comments to Tax Information Document section - Parent's Tax Return
		expTextComment =taxInfoSection.enterTextComment("Tgl_TaxInfoPTRAppNotes_txt");
		actTextComment = taxInfoSection.getTextFromElement("Tgl_TaxInfoPTRAppNotes_txt");
		Assert.assertTrue(actTextComment.contains(expTextComment));
		
		//Step 10 - Verify Tax Information Document section - Parent Income Statement
		soft.assertTrue(webUtil.verifyDocumentInformationSection("ParentIncomeStatement")); 
		
		//Step 11 - Add test comments to Tax Information Document section - Parent's Tax Return
		expTextComment =taxInfoSection.enterTextComment("Tgl_TaxInfoPISAppNotes_txt");
		actTextComment = taxInfoSection.getTextFromElement("Tgl_TaxInfoPISAppNotes_txt");
		Assert.assertTrue(actTextComment.contains(expTextComment));
		
		//Step 12 - Verify HelpLinks
		soft.assertTrue(taxInfoSection.verifyHelpLinksOnTaxInformation(),"VerifyHelpLinks method failed"); 
	}

	@Override
	public TGLConstants getConstants() {
		return new TGLConstants();
	}

}
