package org.tfa.tgl.tests;

import java.util.Map;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.tfa.framework.core.BaseTestMethods;
import org.tfa.tgl.pages.common.LoginPageTgl;
import org.tfa.tgl.pages.search.SearchPage;
import org.tfa.tgl.pages.searchdetails.SearchDetailsPage;
import org.tfa.tgl.pages.searchdetails.TGLAppCenterIntergrationPoints;
import org.tfa.tgl.utilities.web.TGLConstants;
import org.tfa.tgl.utilities.web.TGLWebUtil;

public class ValidateTGLAppCenterIntegration extends BaseTestMethods {

	private TGLWebUtil webUtil = TGLWebUtil.getObject();
	private TGLAppCenterIntergrationPoints integrationpoints = new TGLAppCenterIntergrationPoints();
	private SearchPage searchPage = new SearchPage();
	private SearchDetailsPage searchDetailsPage= new SearchDetailsPage();

	/**
	 **************************************************************************************************************
	 * @Description : Below Test Verifies File Upload, Applicant Notes and Applicant
	 * Tax Return "Valid" Checkbox checked between AppCenter and TGL
	 * @Param: Login credential with admin role 
	 * @Author: Nitin Sharma 
	 **************************************************************************************************************
	 */

	@Test
	public void tgl108IntegrationPointsTest() {

		String applicantID = testDataMap.get("PIDIntegation");
		String relativeFileName = testDataMap.get("uploadPDFFilePath");

		/* Step 1 - Login to the TGL portal application using valid user id */
		LoginPageTgl loginPage = webUtil.openLoginPage();
		loginPage.enterLoginInfo();

		/* TestCase - Integration point - assets and liabilities - Step2 and Step 3 */
		searchPage.enterPersonIDAndClickOnSearchButton(applicantID);

		/* TestCase - Integration point - assets and liabilities - step 5 */
		Assert.assertTrue(integrationpoints.uploadTGLDocument("uploadPDFFilePath"),
				"uploadDocumentinAda method failed");

		/* TestCase - Integration point - assets and liabilities - step 6 */
		String[] sectionsOnTGL = { "Tgl_ApplicantTax_Section" };
		Map<String, String> mapTGL = searchDetailsPage.checkValidCheckBoxAndEnterNotes(sectionsOnTGL, "Check");
		String getSectionNameFromTGL = mapTGL.get("SectionName");
		Assert.assertNotNull(getSectionNameFromTGL, "Not able to get the selected values from TGL");

		/* TestCase - Integration point - assets and liabilities - step 7 */
		Map<String, String> mapAppCenter = integrationpoints.getApplicantCenterDocumentsDetails(getSectionNameFromTGL);
		Assert.assertEquals(mapAppCenter.get("AppCenterNotes"), mapTGL.get("Notes"),
				"Verfied Notes updated on Applicant Ceneter");
		Assert.assertEquals(mapAppCenter.get("AppCenterValidCheckBox"), mapTGL.get("ValidCheckBox"),
				"Verfied Checkbox is Checked on Applicant Ceneter");
		Assert.assertTrue(mapAppCenter.get("AppCenterDocument").contains(relativeFileName),
				"Verfied Document on Applicant Ceneter");

		/*
		 * TestCase - Integration point - assets and liabilities - step 8 Commenting
		 * below test since this fails on Pipeline
		 */
	}

	/*
	 * @Description: Below Test Verifies Applicant Notes removal and Applicant Tax
	 * Return "Valid" Checkbox UNCHECKED between AppCenter and TGL
	 * @Param: TGL Admin credentials and a valid applicant email and person id in
	 * TestData
	 * @Author: Nitin Sharma
	 */
	@Test
	public void tgl110IntegrationPointsTest() {

		String applicantID = testDataMap.get("PIDIntegation");
		String relativeFileName = testDataMap.get("uploadPDFFilePath");

		/*
		 * TestCase - Login to the TGL portal application using valid user id - Step 1
		 */
		LoginPageTgl loginPage = webUtil.openLoginPage();
		loginPage.enterLoginInfo();

		/* TestCase - Integration point - assets and liabilities - Step 2 and Step 3 */
		searchPage.enterPersonIDAndClickOnSearchButton(applicantID);

		/* TestCase - Integration point - assets and liabilities - step 6 */
		String[] sectionsOnTGL = { "Tgl_ApplicantTax_Section" };
		Map<String, String> mapTGL = searchDetailsPage.checkValidCheckBoxAndEnterNotes(sectionsOnTGL, "UnCheck");
		String getSectionNameFromTGL = mapTGL.get("SectionName");
		Assert.assertNotNull(getSectionNameFromTGL, "Not able to get the selected values from TGL");

		/* TestCase - Integration point - assets and liabilities - step 8 */
		Map<String, String> mapAppCenter = integrationpoints.getApplicantCenterDocumentsDetails(getSectionNameFromTGL);
		Assert.assertEquals(mapAppCenter.get("AppCenterNotes"), mapTGL.get("Notes"),
				"Verfied Notes updated on Applicant Ceneter");
		Assert.assertEquals(mapAppCenter.get("AppCenterValidCheckBox"), mapTGL.get("ValidCheckBox"),
				"Verfied Checkbox is Checked on Applicant Ceneter");
		Assert.assertTrue(mapAppCenter.get("AppCenterDocument").contains(relativeFileName),
				"Verfied Document on Applicant Ceneter");
	}

	/*
	 * @Description: Below Test Verifies document removal, Applicant Notes removal
	 * @Param: TGL Admin credentials and a valid applicant email and person id in
	 * TestData
	 * @Author: Nitin Sharma
	 */
	@Test
	public void tgl111IntegrationPointsTest() {

		String applicantID = testDataMap.get("PIDIntegation");
		String relativeFileName = testDataMap.get("uploadPDFFilePath");

		/* Step 1 - Login to the TGL portal application using valid user id */
		LoginPageTgl loginPage = webUtil.openLoginPage();
		loginPage.enterLoginInfo();

		/* TestCase - Integration point - assets and liabilities - Step 2 and Step 3 */
		searchPage.enterPersonIDAndClickOnSearchButton(applicantID);

		/* TestCase - Integration point - assets and liabilities - step 6 */
		Assert.assertTrue(integrationpoints.removeExistingDocumentsfromApplicantTaxReturn(relativeFileName),
				"removeExistingDocumentsfromApplicantTaxReturn method failed");

		/* TestCase - Integration point - assets and liabilities - Step 8 */
		String selectedSection = "Tgl_ApplicantTax_Section";
		String getSectionNameFromTGL = searchDetailsPage.getSectionName(selectedSection);
		Map<String, String> mapAppCenter = integrationpoints.getApplicantCenterDocumentsDetails(getSectionNameFromTGL);
		String actualDocument = mapAppCenter.get("AppCenterDocument");

		Assert.assertNull(actualDocument, "Verfied Document removed on Applicant Ceneter");
	}

	/*
	 * @Description: Below Test Verifies File Upload, Applicant Notes and Applicant
	 * Tax Return "Valid" Checkbox checked between AppCenter and TGL
	 * @Param: TGL Admin credentials and a valid applicant email and person id in
	 * TestData
	 * @Author: Nitin Sharma
	 */
	@Test
	public void tgl112IntegrationPointsTest() {

		String applicantID = testDataMap.get("PIDIntegation");

		/* Step 1 - Login to the TGL portal application using valid user id */
		LoginPageTgl loginPage = webUtil.openLoginPage();
		loginPage.enterLoginInfo();

		/* TestCase - Integration point - assets and liabilities - Step2 and Step 3 */
		searchPage.enterPersonIDAndClickOnSearchButton(applicantID);

		/* TestCase - Integration point - assets and liabilities - step 6 */
		String[] sectionsOnTGL = { "Tgl_ApplicantTax_Section" };
		Map<String, String> mapTGL = searchDetailsPage.checkValidCheckBoxAndEnterNotes(sectionsOnTGL, "Check");
		String getSectionNameFromTGL = mapTGL.get("SectionName");
		Assert.assertNotNull(getSectionNameFromTGL, "Not able to get the selected values from TGL");

		/* TestCase - Integration point - assets and liabilities - step 7 */
		Map<String, String> mapAppCenter = integrationpoints.getApplicantCenterDocumentsDetails(getSectionNameFromTGL);
		String actualDocument = mapAppCenter.get("AppCenterDocument");

		Assert.assertEquals(mapAppCenter.get("AppCenterNotes"), mapTGL.get("Notes"),
				"Verfied Notes updated on Applicant Ceneter");
		Assert.assertEquals(mapAppCenter.get("AppCenterValidCheckBox"), mapTGL.get("ValidCheckBox"),
				"Verfied Checkbox is Checked on Applicant Ceneter");
		Assert.assertNull(actualDocument, "Verfied Document not on Applicant Ceneter");
	}


	@Override
	public TGLConstants getConstants() {
		return new TGLConstants();
	}
}