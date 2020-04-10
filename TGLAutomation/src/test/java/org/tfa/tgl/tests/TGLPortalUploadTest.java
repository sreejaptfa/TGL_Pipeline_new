package org.tfa.tgl.tests;


import java.util.List;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.tfa.framework.core.BaseTestMethods;
import org.tfa.tgl.pages.LoginPageTgl;
import org.tfa.tgl.pages.SearchDetailsPageTGL;
import org.tfa.tgl.pages.SearchPageTGL;
import org.tfa.tgl.utilities.web.TGLConstants;
import org.tfa.tgl.utilities.web.TGLWebUtil;


public class TGLPortalUploadTest extends BaseTestMethods {
	
	private LoginPageTgl loginpage;
	private SearchPageTGL searchPage= new SearchPageTGL();
	private SearchDetailsPageTGL searchDetailsPage = new SearchDetailsPageTGL();
	private TGLWebUtil webUtil=TGLWebUtil.getObject();
	static Logger log=Logger.getLogger("rootLogger");

	String expectedErrorMessage;
	String actualErrorMessage;
	String downloadedFilePath;
	String tglApplicantTaxReturnCHK = "Tgl_ApplicantTaxReturn_CHK";
	String tglUploadedFileIconImg= "Tgl_UploadedFileIcon_Img";
	
	/**
	 **************************************************************************************************************
	 * @Description  : This function is to verify the upload Document  
  	 * @Param: No Parameter
	 * @Return: No Return 
	 * @Author: Surya
	 **************************************************************************************************************
	 */
	@Test
	public void TGL105TestTGLPortalUpload() throws Exception {
		
		/* Step 1 - Login to TGL Portal >  Search for any applicant and than click on any applicant */
		loginpage=new LoginPageTgl();
		loginpage.enterLoginInfo();
		searchPage.clickOnSearchBtn();
		searchPage.clickFirstRowColumnOnSearchResults();
		
		/* Step 2 - Verify upload button is there to upload the doc */
		Assert.assertTrue(webUtil.isVisible("Tgl_UploadTGLDocuments_btn"));
		
		/* Step 3 - Click on upload TGL link */
		searchDetailsPage.clickOnUploadTGLDouments();
	
		/* Step 4 - Now try to cick on upload without entering any doc and selecting any check box */
		searchDetailsPage.clickOnUploadButton();
		expectedErrorMessage=testDataMap.get("errorMessage_Validation_1");
		actualErrorMessage = webUtil.getText("Tgl_validationErrorMsgWitoutEnterAnyDoc_ST");
		Assert.assertEquals(actualErrorMessage, expectedErrorMessage,"Verified the Document Upload error");
		
		expectedErrorMessage=testDataMap.get("errorMessage_Validation_2");
		actualErrorMessage = webUtil.getText("Tgl_validationErrorMsgDocType_ST");
		Assert.assertEquals(actualErrorMessage, expectedErrorMessage,"Verified the Check Box error ");

		/* Step 5 - Now Just select check box and try to upload */
		searchDetailsPage.clickOnTypeOfDocumentChk(tglApplicantTaxReturnCHK);
		searchDetailsPage.clickOnUploadButton();
		expectedErrorMessage=testDataMap.get("errorMessage_Validation_1");
		actualErrorMessage = webUtil.getText("Tgl_validationErrorMsgWitoutEnterAnyDoc_ST");
		Assert.assertEquals(actualErrorMessage, expectedErrorMessage,"Verified the Document Upload error");
		
		/* Step 6 - Now remove check box and just upload doc */
		searchDetailsPage.clickOnTypeOfDocumentChk(tglApplicantTaxReturnCHK);
		webUtil.uploadFile("uploadPDFFilePath",tglUploadedFileIconImg);
		searchDetailsPage.clickOnUploadButton();
		expectedErrorMessage=testDataMap.get("errorMessage_Validation_2");
		actualErrorMessage = webUtil.getText("Tgl_validationErrorMsgDocType_ST");
		Assert.assertEquals(actualErrorMessage, expectedErrorMessage,"Verified the Check Box error ");

		/* Step 7 - Try to upload file other than .gif, .jpg, or .pdf files */
		searchDetailsPage.clickOnTypeOfDocumentChk(tglApplicantTaxReturnCHK);
		webUtil.uploadFile("uploadDocFilePath",tglUploadedFileIconImg);
		searchDetailsPage.clickOnUploadButton();
		expectedErrorMessage=testDataMap.get("errorMessage_Validation_1");
		actualErrorMessage = webUtil.getText("Tgl_validationErrorMsgWitoutEnterAnyDoc_ST");
		Assert.assertEquals(actualErrorMessage, expectedErrorMessage,"Verified the Document Upload error");

		/* Step 8 - Verify You see valid document type to upload the file */
		List<WebElement> allDocTypeSectionList=webUtil.getElementsList("Tgl_DocumentTypeSection_ST");
		for( int j=0; j<=allDocTypeSectionList.size()-1; j++) {
			WebElement columnWe=allDocTypeSectionList.get(j);
			String actualDocumentType = columnWe.getAttribute("value");
			String expectedDocumentType = testDataMap.get("expectedDocumentType");
			Assert.assertTrue(expectedDocumentType.contains(actualDocumentType), "verified the Document Type");
		}
		
		/* Step 9 - Go to Upload Doc again and upload valid doc and check the check box and do not click on upload, rather than click on Cancel */
		searchDetailsPage.clickOnTypeOfDocumentChk(tglApplicantTaxReturnCHK);
		webUtil.uploadFile("uploadPDFFilePath",tglUploadedFileIconImg);
		searchDetailsPage.clickOnCancelButton("Tgl_Cancel_btn");
		Assert.assertFalse(!webUtil.isVisible("Tgl_UploadTGLDocuments_btn"),"verified Document Upload window cancelled");
		webUtil.holdOn(3);
		
		/* Step 10 - Go back to upload doc again and Try to enter same file for multiple doc type */
		searchDetailsPage.clickOnUploadTGLDouments();
		searchDetailsPage.clickOnTypeOfDocumentChk(tglApplicantTaxReturnCHK);
		for(int i=0; i<=2; i++){
			webUtil.uploadFile("uploadPDFFilePath",tglUploadedFileIconImg);
			Assert.assertTrue(!webUtil.isVisible("Tgl_validationErrorMsgWitoutEnterAnyDoc_ST"),"verified  no error message ");
		}
		searchDetailsPage.clickOnCancelButton("Tgl_Cancel_btn");
		
		/* Step 11 - Verify once you upload the document */
		searchDetailsPage.clickOnUploadTGLDouments();
		searchDetailsPage.clickOnTypeOfDocumentChk(tglApplicantTaxReturnCHK);
		webUtil.uploadFile("uploadPDFFilePath",tglUploadedFileIconImg);
		searchDetailsPage.clickOnUploadButton();
		webUtil.holdOn(5);
		
		/* Step 12 - Now Click on Review for doc which you uploaded */
		String uploadedFileName=testDataMap.get("uploadPDFFilePath");
		WebElement clickOnReviewLink = webUtil.getValuesFromDocumentsWebTable("Tgl_ColTaxReturn_TB", uploadedFileName,"Review");
		webUtil.click(clickOnReviewLink);
		webUtil.holdOn(5);
		webUtil.downloadFile(uploadedFileName);
	
		/* Step 13 -  Now Click on Remove the doc */
		WebElement clickOnRemoveLink = webUtil.getValuesFromDocumentsWebTable("Tgl_ColTaxReturn_TB", uploadedFileName,"Remove");
		webUtil.click(clickOnRemoveLink);
		webUtil.holdOn(2);
		searchPage.clickOnRemoveBtn();
		webUtil.holdOn(2);
		Assert.assertFalse(webUtil.objectIsVisible("Tgl_DocumentName_ST"),"verified document removed");
	
		/* Step 14 - End Script */

	}
		
	@Override
	public TGLConstants getConstants(){
		return new TGLConstants();
	}

}