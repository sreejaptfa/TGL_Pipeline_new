package org.tfa.tgl.pages;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.tfa.framework.core.WebDriverUtil;
import org.tfa.framework.utilities.testdata.TestData;
import org.tfa.tgl.utilities.web.TGLWebUtil;

public class TGLAppCenterIntergrationPoints extends PFactory{
	
	//private WebDriverUtil webUtil=WebDriverUtil.getObject();
	private TestData data=TestData.getObject();
	private TGLWebUtil webUtil=TGLWebUtil.getObject();
	private boolean flag;
	Logger log=Logger.getLogger("rootLogger");
	protected WebDriverWait explicitwait;
	private int size;
	private static AssetsAndLiabilitiesSection assetsandliabilitiessection;
	private static SearchDetailsPageTGL searchDetailsPage;
	private LoginPageAppCenter login;
	String downloadedFilePath;
	String tglApplicantTaxReturnCHK = "Tgl_ApplicantTaxReturn_CHK";
	String tglUploadedFileIconImg= "Tgl_UploadedFileIcon_Img";
	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	Date date;
	LocalDateTime now = LocalDateTime.now();
	
	
	public TGLAppCenterIntergrationPoints() {
		// TODO Auto-generated constructor stub
		 /*if(searchDetailsPage == null)*/ {
		 assetsandliabilitiessection = new AssetsAndLiabilitiesSection();
		 searchDetailsPage = new SearchDetailsPageTGL();
		 login = new LoginPageAppCenter();
		 date= new Date();	
		 }
	}
	
	public boolean uploadDocumentinAda() {
		boolean flag=false;
		
		// Delete all existing documents for Applicants tax return
		flag=removeExistingDocumentsfromApplicantTaxReturn();
		if (flag==false){
			return flag;
		}
		
		//  Add comments in Applicant Tax return's comment box
		webUtil.getElement("Tgl_TaxInfoAppTaxReturnAppNotes_txt").sendKeys("Test Comments"+dateFormat.format(date));
		webUtil.holdOn(3);
		
		// Click Upload TGL Documents button
		searchDetailsPage.clickOnUploadTGLDouments();
		// Check Applicant Tax Return option
		searchDetailsPage.clickOnTypeOfDocumentChk(tglApplicantTaxReturnCHK);
		// Choose the file location and click upload button
		webUtil.getElement("Tgl_ChooseFile_txt").sendKeys(System.getProperty("user.dir")+"/src/test/resources/TestData/TestPdfFile.pdf");
		webUtil.click("Tgl_ModalUpload_btn");
		webUtil.holdOn(5);
		
		return flag;
	}
	public boolean removeDocumentinAda() {
		boolean flag = false;
		
		return flag;
	}
	
	public boolean searchAndOpenApplicantDetails() {
		boolean flag=false;
		try {
		// Clear the default value of TGL App year
		webUtil.selectByIndex("Tgl_appyear_dd", 0);
		// Click More Search Options link
		webUtil.click("Tgl_moreSearchOptionsLink");
		// Maximize the window
		webUtil.getDriver().manage().window().maximize();
		// Provide PID in PersonId field
		webUtil.setTextBoxValueTestData("Tgl_personid", "PIDIntegation");
		webUtil.holdOn(2);
		// Click Search button
		webUtil.click("Home_Tgl_Search2_btn");
		webUtil.holdOn(3);
		// Click the result row
		WebElement firstrow=webUtil.getDriver().findElement(firstrownamelocator);
		firstrow.click();
		} 
		catch (Exception e) {
			flag = false;
			log.info("Exception occuring in opening applicant details");
			
		}
		flag=true;
		return flag;
	}
	
	public boolean removeExistingDocumentsfromApplicantTaxReturn() {
		boolean f = false;
		List <WebElement> documents = new ArrayList<>();
		try{
			documents = webUtil.getDriver().findElements(By.xpath("(//table[@class='documents-table'])[6]/tbody/tr"));
			//(//table[@class='documents-table'])[6]/tbody/tr[1]/td[3]/button
			for(WebElement w:documents){
				w.findElement(By.xpath("//td[3]/button")).click();
				webUtil.holdOn(3);
				webUtil.getDriver().findElement(By.xpath("((//button[@data-hook='do-remove-document'])[1])/span")).click();
			}
			f = true;
		}catch(Exception e) {
			log.info("Exception occured deleting existing documents for applicant tax return");
			f = false;
		}
		return f;
	}
	// This method is used to check applicant tax return checkbox as Valid
	public boolean checkValidcheckBoxApplicantTaxReturn() {
		boolean flag = false;
		try{
		while(!webUtil.getElement("Tgl_ApplicantstaxValid_chk").isSelected())
			webUtil.getElement("Tgl_ApplicantstaxValid_chk").click();
		} catch(Exception e) {
			flag=false;
			log.info("Exception in checking the Applicant Tax return checkbox"+e);
		}
		flag = true;
		return flag;
	}
	
	// This method is used to check applicant tax return checkbox as Valid
	public boolean uncheckValidcheckBoxApplicantTaxReturn() {
		boolean flag = false;
		try{
			while(webUtil.getElement("Tgl_ApplicantstaxValid_chk").isSelected())
				webUtil.getElement("Tgl_ApplicantstaxValid_chk").click();
			webUtil.getElement("Tgl_TaxInfoAppTaxReturnAppNotes_txt").clear();
			webUtil.getElement("Tgl_TaxInfoAppTaxReturnAppNotes_txt").sendKeys(" ");
			webUtil.holdOn(3);
			
		} catch(Exception e) {
				flag=false;
				log.info("Exception in Un-checking the Applicant Tax return checkbox"+e);
		}
			flag = true;
			return flag;
	}
	
	public boolean verifyApplicantCenterDocumentsDetails(boolean verificationCheck){
		boolean flag = false;
		try {
		login.openLoginPage();
		login.enterLoginInfo();
		// Click AppCenter TGL Funding link
		webUtil.click("AppCenter_TGLFunding_link");
		// Check if Applicant Tax return check is check in Applicant Center
		
		if(verificationCheck == true) {
			if (webUtil.getElement("AppcenterDocumentationAppTaxReturnVerified_chk").isSelected())
				flag=true;
			else {
				log.info("Applicant Center - Applicant Tax Return checkbox is not checked");
				return flag = false;
			}
			
			// Check Notes section shows correct notes from ADA application
			if (webUtil.getElement("AppcenterDocumentationAppTaxReturnComment_txt").getText().contains("Test Comments"+dateFormat.format(date)))
				flag=true;
			else {
				log.info("Applicant Notes for App tax return doesnot match");
				return flag=false;
			}// else
		}
		else {
			if (!webUtil.getElement("AppcenterDocumentationAppTaxReturnVerified_chk").isSelected())
				flag=true;
			else {
				log.info("Applicant Center - Applicant Tax Return checkbox is still checked");
				return flag = false;}	
			
			// Check Notes section shows correct notes from ADA application
			if (webUtil.getElement("AppcenterDocumentationAppTaxReturnComment_txt").getText().equals(""))
				flag=true;
			else {
				log.info("Applicant Notes for App tax return doesnot match");
				return flag=false;
			}// else
		}
			
		
		}// try
		catch (Exception e) {
			
			log.info("Exception occured while click TGL Funding link in Applicant Center "+e);
			flag = false;
		}
		
		return flag=true;
	}
	public boolean verifyDocumentIsRemoved() {
		boolean flag=false;
		
		try {
			login.openLoginPage();
			login.enterLoginInfo();
			// Click AppCenter TGL Funding link
			webUtil.click("AppCenter_TGLFunding_link");
			List <WebElement> list=new ArrayList<>();
			list = webUtil.getDriver().findElements(By.xpath("(//tbody[@data-hook='tgl-documents-uploaded'])[1]/tr"));
			if(list.size()==0)
				flag=true;
			else {
				log.info("document not removed");
				flag=false;
			}
		} catch (Exception e) {
			flag=false;
			log.info("Exception in reading document in applicant center");
		}	
		return flag;
	}
	
}














