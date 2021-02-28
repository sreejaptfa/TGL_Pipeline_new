package org.tfa.tgl.pages;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import org.apache.commons.lang3.SystemUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.tfa.framework.core.WebDriverUtil;
import org.tfa.framework.utilities.testdata.TestData;
import org.tfa.tgl.pages.common.ApplicantCenterPage;
import org.tfa.tgl.utilities.web.TGLWebUtil;

import com.google.common.io.PatternFilenameFilter;

public class TGLAppCenterIntergrationPoints extends WebDriverUtil {

	// private WebDriverUtil webUtil=WebDriverUtil.getObject();
	private TestData data = TestData.getObject();
	private TGLWebUtil webUtil = TGLWebUtil.getObject();
	private boolean flag;
	Logger log = Logger.getLogger("rootLogger");
	protected WebDriverWait explicitwait;
	private int size;
	private static AssetsAndLiabilitiesSection assetsandliabilitiessection;
	private static SearchDetailsPageTGL searchDetailsPage;
	private ApplicantCenterPage applicantCenterPage = new ApplicantCenterPage();
	protected By firstrownamelocator = By.xpath("//tbody[@data-hook='results']/tr[1]/td/a");
	String downloadedFilePath;
	String tglApplicantTaxReturnCHK = "Tgl_ApplicantTaxReturn_CHK";
	String tglUploadedFileIconImg = "Tgl_UploadedFileIcon_Img";
	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	Date date;
	LocalDateTime now = LocalDateTime.now();
	String downloadFilepath = System.getProperty("user.home") + "\\downloads\\";

	public TGLAppCenterIntergrationPoints() {
		// TODO Auto-generated constructor stub
		/* if(searchDetailsPage == null) */ {
			assetsandliabilitiessection = new AssetsAndLiabilitiesSection();
			searchDetailsPage = new SearchDetailsPageTGL();
			date = new Date();
		}
	}

	public boolean uploadDocumentinAda() {
		boolean flag = false;
		explicitwait = new WebDriverWait(webUtil.getDriver(), 30);

		// Delete all existing documents for Applicants tax return
		webUtil.holdOn(4);
		flag = removeExistingDocumentsfromApplicantTaxReturn();
		webUtil.holdOn(3);
		if (flag == false) {
			return flag;
		}
		// Add comments in Applicant Tax return's comment box
		webUtil.getElement("Tgl_TaxInfoAppTaxReturnAppNotes_txt").sendKeys("Test Comments" + dateFormat.format(date));
		webUtil.holdOn(3);
		// Click Upload TGL Documents button
		searchDetailsPage.clickOnUploadTGLDouments();
		webUtil.holdOn(4);
		// Check Applicant Tax Return option
		searchDetailsPage.clickOnTypeOfDocumentChk(tglApplicantTaxReturnCHK);
		// Choose the file location and click upload button
		webUtil.getElement("Tgl_ChooseFile_txt")
				.sendKeys(System.getProperty("user.dir") + "/src/test/resources/TestData/TestPdfFile.pdf");
		webUtil.click("Tgl_ModalUpload_btn");
		explicitwait.until(ExpectedConditions.invisibilityOf(webUtil.getElement("Tgl_ModalUpload_btn")));
		webUtil.holdOn(5);

		return flag;
	}

	public boolean removeDocumentinAda() {
		boolean flag = false;

		return flag;
	}

	public boolean searchAndOpenApplicantDetails() {
		boolean flag = false;
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
			// Clear app year value - change made to fix script <<NS 21 July 2020>>
			webUtil.selectByIndex("Tgl_appyear_dd", 0);
			webUtil.holdOn(1);
			// Click Search button
			webUtil.click("Home_Tgl_Search2_btn");
			webUtil.holdOn(3);
			// Click the result row
			WebElement firstrow = webUtil.getDriver().findElement(firstrownamelocator);
			webUtil.holdOn(3);
			firstrow.click();
			webUtil.holdOn(2);
		} catch (Exception e) {
			flag = false;
			log.info("Exception occuring in opening applicant details");
		}
		flag = true;
		return flag;
	}

	public boolean removeExistingDocumentsfromApplicantTaxReturn() {
		boolean f = false;
		List<WebElement> we = new ArrayList();
		List<WebElement> documents = new ArrayList<>();
		try {
			documents = webUtil.getDriver().findElements(By.xpath("(//table[@class='documents-table'])[6]/tbody/tr"));
			// (//table[@class='documents-table'])[6]/tbody/tr[1]/td[3]/button
			for (WebElement w : documents) {
				w.findElement(By.xpath("//td[3]/button")).click();
				webUtil.holdOn(3);
				// fetch number of Remove buttons and click the one that is visible
				we = webUtil.getDriver()
						.findElements(By.xpath("(//span[contains(text(),'Yes, remove this document')])"));
				log.info("Number of Yes remove buttons:" + we.size());

				for (WebElement w1 : we) {
					if (w1.isDisplayed() == true) {
						w1.click();
						webUtil.holdOn(2);
						break;
					}
				}
			}

			f = true;
		} catch (Exception e) {
			log.info("Exception occured deleting existing documents for applicant tax return");
			f = false;
		}
		return f;
	}

	// This method is used to check applicant tax return checkbox as Valid
	public boolean checkValidcheckBoxApplicantTaxReturn() {
		boolean flag = false;
		try {
			webUtil.getDriver().navigate().refresh();
			webUtil.holdOn(7);
			while (!webUtil.getElement("Tgl_ApplicantstaxValid_chk").isSelected())
				webUtil.getElement("Tgl_ApplicantstaxValid_chk").click();
			webUtil.holdOn(5);
		} catch (Exception e) {
			flag = false;
			log.info("Exception in checking the Applicant Tax return checkbox" + e);
		}
		flag = true;
		return flag;
	}

	// This method is used to check applicant tax return checkbox as Valid
	public boolean uncheckValidcheckBoxApplicantTaxReturn() {
		boolean flag = false;
		webUtil.holdOn(2);
		try {
			while (webUtil.getElement("Tgl_ApplicantstaxValid_chk").isSelected())
				webUtil.getElement("Tgl_ApplicantstaxValid_chk").click();
			webUtil.holdOn(2);
			webUtil.getElement("Tgl_TaxInfoAppTaxReturnAppNotes_txt").clear();
			webUtil.getElement("Tgl_TaxInfoAppTaxReturnAppNotes_txt").sendKeys(" ");
			webUtil.holdOn(3);

		} catch (Exception e) {
			flag = false;
			log.info("Exception in Un-checking the Applicant Tax return checkbox" + e);
		}
		flag = true;
		return flag;
	}

	public boolean verifyApplicantCenterDocumentsDetails(boolean verificationCheck) {
		boolean flag = false;
		try {
			applicantCenterPage.openLoginPage();
			applicantCenterPage.enterLoginInfo();
			// Click AppCenter TGL Funding link
			// webUtil.click("AppCenter_TGLFunding_link");
			webUtil.openURL("https://qamerlin.teachforamerica.org/applicant-center/#expenses/transitional-funding");
			webUtil.holdOn(7);
			webUtil.waitForBrowserToLoadCompletely();
			// Check if Applicant Tax return check is check in Applicant Center

			if (verificationCheck == true) {
				if (webUtil.getElement("AppcenterDocumentationAppTaxReturnVerified_chk").isSelected())
					flag = true;
				else {
					log.info("Applicant Center - Applicant Tax Return checkbox is not checked");
					return flag = false;
				}

				webUtil.holdOn(4);
				// Check Notes section shows correct notes from ADA application
				if (webUtil.getElement("AppcenterDocumentationAppTaxReturnComment_txt").getText()
						.contains("Test Comments" + dateFormat.format(date)))
					flag = true;
				else {
					log.info("Applicant Notes for App tax return doesnot match");
					return flag = false;
				} // else
			} else {
				if (!webUtil.getElement("AppcenterDocumentationAppTaxReturnVerified_chk").isSelected())
					flag = true;
				else {
					log.info("Applicant Center - Applicant Tax Return checkbox is still checked");
					return flag = false;
				}

				// Check Notes section shows correct notes from ADA application
				if (webUtil.getElement("AppcenterDocumentationAppTaxReturnComment_txt").getText().equals(""))
					flag = true;
				else {
					log.info("Applicant Notes for App tax return doesnot match");
					return flag = false;
				} // else
			}

		} // try
		catch (Exception e) {

			log.info("Exception occured while click TGL Funding link in Applicant Center " + e);
			flag = false;
		}

		return flag = true;
	}

	public boolean verifyDocumentIsRemoved() {
		boolean flag = false;
		try {
			applicantCenterPage.openLoginPage();
			applicantCenterPage.enterLoginInfo();
			webUtil.holdOn(3);
			// Click AppCenter TGL Funding link
			// webUtil.click("AppCenter_TGLFunding_link");
			webUtil.openURL("https://qamerlin.teachforamerica.org/applicant-center/#expenses/transitional-funding");
			webUtil.holdOn(4);
			List<WebElement> list = new ArrayList<>();
			list = webUtil.getDriver().findElements(By.xpath("(//tbody[@data-hook='tgl-documents-uploaded'])[1]/tr"));
			webUtil.holdOn(3);
			if (list.size() == 0)
				flag = true;
			else {
				log.info("document not removed");
				flag = false;
			}
		} catch (Exception e) {
			flag = false;
			log.info("Exception in reading document in applicant center");
		}
		return flag;
	}

	public boolean verifyFileDownload() throws Exception {

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
					// System.err.println( "Can't remove " + file.getAbsolutePath() );
					log.info("Can't remove ( file )" + file.getAbsolutePath());
				}
			}
			// THIS CODE IS ADDED TO REMOVE ANY CORRUPT EXISTING FILES THAT COULD HAVE BEEN
			// RESULT OF HALF DOWNLOAD
			files = folder.listFiles(new PatternFilenameFilter("TestPdfFile.*\\.*download"));
			log.info("incomplete existing files to be deleted before fresh report download=> " + files.length);
			for (final File file : files) {
				if (!file.delete()) {
					// System.err.println( "Can't remove " + file.getAbsolutePath() );
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
			// files = folder.listFiles(new
			// PatternFilenameFilter("Corps_Member__General.*/.pdf"));
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
		// logBrowserConsoleLogs();
		return flag;

	}

	public void setChromeProperties() {

		ChromeOptions options = new ChromeOptions();
		DesiredCapabilities capabilities = new DesiredCapabilities();
		LoggingPreferences logPrefs = new LoggingPreferences();
		logPrefs.enable(LogType.BROWSER, Level.ALL);
		// String downloadFilepath = "d:\\TestDownloads";
		// downloadFilepath =
		// System.getProperty("user.dir")+"/src/test/resources/TestData/";

		capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
		capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
		capabilities.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
		options.addArguments("test-type");

		if (SystemUtils.IS_OS_LINUX) {
			options.addArguments("--headless"); // this options is for linux environment on docker
		}

		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		// Setting the file download location for Chrome
		log.info("setting chrome download path:" + downloadFilepath);
		chromePrefs.put("download.default_directory", downloadFilepath);
		options.setExperimentalOption("prefs", chromePrefs);

		options.addArguments("--no-sandbox");
		options.addArguments("--disable-dev-shm-usage");
		setDriver(new ChromeDriver(options));
		Dimension dim = new Dimension(1382, 744);
		// driver.manage().window().setSize(dim);
		webUtil.getDriver().manage().window().setSize(dim);
		log.info("Chrome Browser has been launched successfully");

	}

}
