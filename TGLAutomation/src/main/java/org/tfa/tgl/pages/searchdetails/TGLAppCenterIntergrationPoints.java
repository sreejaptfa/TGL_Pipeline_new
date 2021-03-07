package org.tfa.tgl.pages.searchdetails;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.apache.commons.lang3.SystemUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.tfa.framework.core.WebDriverUtil;
import org.tfa.framework.utilities.testdata.TestData;
import org.tfa.tgl.pages.common.ApplicantCenterPage;
import org.tfa.tgl.pages.search.SearchPage;
import org.tfa.tgl.utilities.web.TGLWebUtil;
import com.google.common.io.PatternFilenameFilter;

@SuppressWarnings({"squid:S4042", "squid:S1192","squid:S1854"})
public class TGLAppCenterIntergrationPoints extends WebDriverUtil {
	private SearchPage searchPage= new SearchPage();
	private SearchDetailsPage searchDetailsPage= new SearchDetailsPage();
	private ApplicantCenterPage applicantCenterPage = new ApplicantCenterPage();

	private TestData data = TestData.getObject();
	private TGLWebUtil webUtil = TGLWebUtil.getObject();
	Logger log = Logger.getLogger("rootLogger");
	String tglApplicantTaxReturnCHK = "Tgl_ApplicantTaxReturn_CHK";
	String tglUploadedFileIconImg = "Tgl_UploadedFileIcon_Img";
	String downloadFilepath = System.getProperty("user.home") + "\\downloads\\";

	public boolean removeExistingDocumentsfromApplicantTaxReturn(String fileName) {
		boolean flag = false;
		WebElement clickOnRemoveLink = webUtil.getValuesFromDocumentsWebTable("Tgl_ColTaxReturn_TB", fileName,"Remove");
		if(clickOnRemoveLink != null) {
			webUtil.click(clickOnRemoveLink);
			webUtil.holdOn(2);
			searchPage.clickOnRemoveBtn();
			webUtil.holdOn(2);
			flag = true;
		}
		return flag;
	}

	public boolean uploadTGLDocument(String uploadDocumentName) {
		boolean flag = false;
		String relativeFileName = data.getTestCaseDataMap().get("uploadPDFFilePath");
		// Delete all existing documents for Applicants tax return
		removeExistingDocumentsfromApplicantTaxReturn(relativeFileName);
		webUtil.waitForBrowserToLoadCompletely();
		webUtil.waitUntilElementVisible("Tgl_UploadTGLDocuments_btn", 10);
		searchDetailsPage.clickOnUploadTGLDouments();
		webUtil.holdOn(4);
		searchDetailsPage.clickOnTypeOfDocumentChk(tglApplicantTaxReturnCHK);
		webUtil.uploadFile(uploadDocumentName,tglUploadedFileIconImg);
		searchDetailsPage.clickOnUploadButton();
		WebElement clickOnReviewLink = webUtil.getValuesFromDocumentsWebTable("Tgl_ColTaxReturn_TB", relativeFileName,"Review");
		if(clickOnReviewLink != null)  flag = true;
		return flag;
	}

	public Map<String, String> getApplicantCenterDocumentsDetails(String sectionName) {
		String navToTransFundingUrl = data.getTestCaseDataMap().get("TransFundingUrl");
		Map<String, String> objectMap = null;
		try {
			applicantCenterPage.openLoginPage();
			applicantCenterPage.enterLoginInfo();
			webUtil.holdOn(2);
			webUtil.openURL(navToTransFundingUrl);
			webUtil.holdOn(5);
			webUtil.waitForBrowserToLoadCompletely();
			
			Map<String, String> transitionalFundingMap = applicantCenterPage.getValuesFromApplicantCenter("AppCenter_TGLDocuments_TB",sectionName);

			String getNotesFromAppCenter = transitionalFundingMap.get("TransitionalFundingNotes");
			String getValidCheckBoxValueFromAppCenter = transitionalFundingMap.get("TransitionalFundingCheckBox");
			String getDocumentFromAppCenter = transitionalFundingMap.get("TransitionalFundingDocument");
			if(getDocumentFromAppCenter.isEmpty()) getDocumentFromAppCenter =null;
			objectMap = new HashMap<>();
			objectMap.put("AppCenterNotes",getNotesFromAppCenter);
			objectMap.put("AppCenterValidCheckBox",getValidCheckBoxValueFromAppCenter);
			objectMap.put("AppCenterDocument",getDocumentFromAppCenter);
		} catch (Exception e) {
			log.info("Unable to get the values from Applicant center",e);
			objectMap = null;
		}
		return objectMap;
	}

	public boolean verifyFileDownload(){
		boolean flag = false;
		// This link is clicking Reports Tab
		try {
			final File folder = new File(downloadFilepath);
			log.info("File path is -> " + folder.getAbsolutePath());

			log.info("Find any existing report:");
			File[] files = folder.listFiles(new PatternFilenameFilter("TestPdfFile.*\\.pdf"));
			log.info("Number of existing files to be deleted: " + files.length);

			int i = 1;
			for (final File file : files) {
				log.info(i + " file(s) deleted.");
				if (!file.delete()) {
				log.info("Can't remove ( file )" + file.getAbsolutePath());
				}
			}
			// THIS CODE IS ADDED TO REMOVE ANY CORRUPT EXISTING FILES THAT COULD HAVE BEEN
			// RESULT OF HALF DOWNLOAD
			files = folder.listFiles(new PatternFilenameFilter("TestPdfFile.*\\.*download"));
			log.info("incomplete existing files to be deleted before fresh report download=> " + files.length);
			for (final File file : files) {
				if (!file.delete()) {
						log.info("Can't remove ( file )" + file.getAbsolutePath());
				}
			}
			webUtil.getDriver().findElement(By.xpath("(//tbody[@data-hook='tgl-documents-uploaded']/tr)[1]/td[3]/a"))
					.click();
			webUtil.holdOn(5);
			files = folder.listFiles(new PatternFilenameFilter("TestPdfFile.*\\.*download"));

			// invoke URL and provide location to save, search such classes
			log.info("Searching for .download file extentions at: " + folder.getAbsolutePath());
			log.info("files with .download extention here:" + files.length);
			while (files.length > 0) {
				files = folder.listFiles(new PatternFilenameFilter("TestPdfFile.*\\.*download"));
				webUtil.holdOn(2);
				log.info(".download extention file is found at:" + folder.getAbsolutePath());
			}
			files = folder.listFiles(new PatternFilenameFilter("TestPdfFile.*\\.pdf"));
			log.info("Searching for .pdf file extentions at: " + folder.getAbsolutePath());
			log.info("files with .pdf extention here:" + files.length);
			webUtil.holdOn(3);
			if (files.length > 0) {
				flag = true;
				log.info("File Downloaded");
			} else {
				flag = false;
				log.info("File not downloaded");
				log.info("file searched at path ->" + folder.getAbsolutePath());
				return flag;
			}
			// This method makes sure that file is not corrupt and it is in a readable form
			if (files[0].canRead()) {
				flag = true;
				log.info("Report is readable");
			} else {
				flag = false;
				log.info("File is unreadable");
				return flag;
			}

		} catch (Exception e) {
			flag = false;
			log.error("Exception occured while creating a file object on given location: " + e);

		}
		log.info("=====Browser Logs at the end of the verify report method=====");
		return flag;

	}

	public void setChromeProperties() {
		ChromeOptions options = new ChromeOptions();
		DesiredCapabilities capabilities = new DesiredCapabilities();
		LoggingPreferences logPrefs = new LoggingPreferences();
		logPrefs.enable(LogType.BROWSER, Level.ALL);
		capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
		capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
		capabilities.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
		options.addArguments("test-type");

		if (SystemUtils.IS_OS_LINUX) {
			options.addArguments("--headless"); // this options is for linux environment on docker
		}
		HashMap<String, Object> chromePrefs = new HashMap<>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		// Setting the file download location for Chrome
		log.info("setting chrome download path:" + downloadFilepath);
		chromePrefs.put("download.default_directory", downloadFilepath);
		options.setExperimentalOption("prefs", chromePrefs);

		options.addArguments("--no-sandbox");
		options.addArguments("--disable-dev-shm-usage");
		setDriver(new ChromeDriver(options));
		Dimension dim = new Dimension(1382, 744);
		webUtil.getDriver().manage().window().setSize(dim);
		log.info("Chrome Browser has been launched successfully");

	}

}
