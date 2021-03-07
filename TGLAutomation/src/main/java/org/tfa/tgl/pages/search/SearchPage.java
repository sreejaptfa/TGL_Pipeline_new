package org.tfa.tgl.pages.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.tfa.framework.utilities.testdata.TestData;
import org.tfa.tgl.utilities.web.TGLWebUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

@SuppressWarnings({ "squid:S117","squid:S1192","squid:S1153","squid:S1854","squid:MethodCyclomaticComplexity",
	"squid:S134","squid:S135","squid:S3626","squid:S2696"})

public class SearchPage {
	
	Logger log = Logger.getLogger("rootLogger");
	SoftAssert soft = new SoftAssert();
	private TGLWebUtil webUtil = TGLWebUtil.getObject();
	private TestData data = TestData.getObject();
	private static final String CLEARBTN ="Tgl_Clear_btn";
	WebDriverWait localwait;
	private static String randompersonid= null;
	
	//***********************************************************************************************************
	//***********************************************************************************************************
	// WE NEED TO FIX THIS FOLLOWING FUNCTIOANLITY
	// WE NEED TO FIX THIS FOLLOWING FUNCTIOANLITY
	//***********************************************************************************************************
	//***********************************************************************************************************
	//***********************************************************************************************************


	// TestCase - SearchPage - Step 11, 12, 13
	public boolean verifyEachFilter() throws InterruptedException{
		Random r = new Random();
		By firstrowfullnamelocator = By.xpath("//tbody[@data-hook='results']/tr[1]/td[1]/a");
		By firstrowpersonidlocator = By.xpath("//tbody[@data-hook='results']/tr[1]/td[2]/a");
		By searchresultstable = By.xpath("//tbody[@data-hook='results']//tr");

		boolean flag = false;
		boolean validofferdeadline = true;
		String s; 
		String deadline;
		int size = 0; 
		int i = 1;
		int random = 0;
		int randomStatus = 0;
		localwait = new WebDriverWait(webUtil.getDriver(), 15);
		String intvwdeadlineselection;
		List<WebElement> searchresults;
		String statusselection = "";
		By rowdetailxpath;
		webUtil.getDriver().navigate().to(data.getEnvironmentDataMap().get("ApplicationURL")) ;
		webUtil.waitForBrowserToLoadCompletely();
		WebElement InterviewDeadlinedd = webUtil.getElement("Tgl_InterviewDeadlinefilter_txt");
		rowdetailxpath = By.xpath("//tbody[@data-hook='results']/tr[1]");
		int exit = 0;
		do {
			validofferdeadline = true;
			webUtil.getDriver().navigate().refresh();
			webUtil.waitForBrowserToLoadCompletely();
			webUtil.selectByIndex("Tgl_appyear_dd", 0);
			webUtil.getElement("Tgl_InterviewDeadlinefilter_txt").click();
			while (validofferdeadline) {
				random = r.nextInt(5);
				random++;
				Thread.sleep(500);
				InterviewDeadlinedd = webUtil.getDriver().findElement(
						By.xpath("//div[@class='selectize-dropdown-content']/div[" + String.valueOf(random) + "]"));
				if (!InterviewDeadlinedd.getText().contains("2045"))
					validofferdeadline = false;
			}
			InterviewDeadlinedd.click();
			Thread.sleep(500);
			webUtil.click("Home_Tgl_Search2_btn");
			intvwdeadlineselection = webUtil.getDriver()
					.findElement(By.xpath("//div[@class='selectize-control multi']/div/div"))
					.getAttribute("data-value");
			try {
				localwait.until(ExpectedConditions.visibilityOfElementLocated(rowdetailxpath));
				searchresults = webUtil.getDriver().findElements(searchresultstable);
				size = searchresults.size();
				if (size < 2)
					continue;
			} catch (Exception e) {
				log.info("No records found with Interview Deadline:" + intvwdeadlineselection,e);
				exit++;
				if (exit > 8) {
					log.info("No data (or Insufficiet data) found against the available deadlines");
					return false;
				}
				continue;
			}
			int sizefirstfilter = size;
			if (sizefirstfilter > 15) {
				do {
					webUtil.getElement("Tgl_TGLStatus_txt").click();
					Thread.sleep(500);
					randomStatus = r.nextInt(5);
					randomStatus++;
					webUtil.getDriver().findElement(By.xpath("(//div[@class='selectize-dropdown-content'])[2]/div["
							+ String.valueOf(randomStatus) + "]")).click();
					Thread.sleep(500);
					webUtil.click("Home_Tgl_Search2_btn");
					statusselection = webUtil.getDriver()
							.findElement(By.xpath(
									"(//div[@class='selectize-input items not-full has-options has-items'])[2]/div"))
							.getText();
					try {
						size = 0;
						searchresults = webUtil.getDriver().findElements(searchresultstable);
						size = searchresults.size();
						if (size < 2) {
							log.info(size + " no of records searched for interview deadline : " + intvwdeadlineselection
									+ " and application status: " + statusselection);
						} else
							break;

					} catch (Exception e) {
						log.info("Exception occured in results for status selection:" + statusselection,e);
					}
					webUtil.getDriver().navigate().refresh();
					webUtil.waitForBrowserToLoadCompletely();
					webUtil.selectByIndex("Tgl_appyear_dd", 0);
					webUtil.getElement("Tgl_InterviewDeadlinefilter_txt").click();
					InterviewDeadlinedd = webUtil.getDriver().findElement(
							By.xpath("//div[@class='selectize-dropdown-content']/div[" + String.valueOf(random) + "]"));
					InterviewDeadlinedd.click();
					Thread.sleep(500);
					webUtil.waitForBrowserToLoadCompletely();
				} while (size < 2);
			} else {
				webUtil.waitForBrowserToLoadCompletely();
				webUtil.getElement("Tgl_TGLStatus_txt").click();
				Thread.sleep(500);
				random = r.nextInt(5);
				random++;
				webUtil.getDriver()
						.findElement(By.xpath(
								"(//div[@class='selectize-dropdown-content'])[2]/div[" + String.valueOf(random) + "]"))
						.click();
				Thread.sleep(500);
				webUtil.click("Home_Tgl_Search2_btn");
				statusselection = webUtil.getDriver()
						.findElement(By
								.xpath("(//div[@class='selectize-input items not-full has-options has-items'])[2]/div"))
						.getText();
				try {
					size = 0;
					searchresults = webUtil.getDriver().findElements(searchresultstable);
					size = searchresults.size();
					if (size < 2) {
						log.info("Less than two ro no records for Status:" + statusselection + " and deadline"
								+ intvwdeadlineselection);
						continue;
					}
				} catch (Exception e) {
					log.info("Exception occured in results for status selection:" + statusselection,e);
					continue;
				}
			}

		} while (size < 2);
		for (i = 1; i <= size; i++) {
			WebElement rowstatus = webUtil.getDriver()
					.findElement(By.xpath("//tbody[@data-hook='results']/tr[" + String.valueOf(i) + "]/td[3]/a"));
			WebElement rowdeadline = webUtil.getDriver()
					.findElement(By.xpath("//tbody[@data-hook='results']/tr[" + String.valueOf(i) + "]/td[4]/a"));
			s = rowstatus.getText();
			deadline = rowdeadline.getText();

			if ("MANAGER REVIEW".equals(statusselection))
				statusselection = "MGRREVIEW";
			if (s.contains(statusselection) && deadline.contains(intvwdeadlineselection))
				flag = true;
			else
				flag = false;

		}

		log.info(size + " no of records searched for interview deadline : " + intvwdeadlineselection
				+ " and application status: " + statusselection);
		random = r.nextInt(size);
		random++;
		String fullname = webUtil.getDriver()
				.findElement(By.xpath("//tbody[@data-hook='results']/tr[" + String.valueOf(random) + "]/td[1]/a"))
				.getText();
		String personid = webUtil.getDriver()
				.findElement(By.xpath("//tbody[@data-hook='results']/tr[" + String.valueOf(random) + "]/td[2]/a"))
				.getText();
		String[] name = fullname.split(",");
		name[0] = name[0].trim();
		name[1] = name[1].trim();
		webUtil.click("Tgl_moreSearchOptionsLink");
		webUtil.waitUntilElementVisible("Home_Tgl_Search2_btn", 20);
		webUtil.setTextBoxValue("Tgl_firstname", name[1]);
		webUtil.setTextBoxValue("Tgl_lastname", name[0]);
		webUtil.setTextBoxValue("Tgl_personid", personid);
		webUtil.click("Home_Tgl_Search2_btn");

		String fullnameresult = webUtil.getDriver().findElement(firstrowfullnamelocator).getText();
		String personidresult = webUtil.getDriver().findElement(firstrowpersonidlocator).getText();
		randompersonid = personidresult;

		if (fullname.equals(fullnameresult))
			flag = true;
		else {
			log.info("Search result doesnot match for Name");
			flag = false;
		}

		if (personid.equals(personidresult))
			flag = true;
		else {
			log.info("Search result doesnot match for PersonId");
			flag = false;
		}

		log.info("Searched for name:" + fullname + " and personid:" + personid);
		return flag;
	}

	public static String getpersonid() {

		return randompersonid;
	}
	//***********************************************************************************************************
	//***********************************************************************************************************
	//***********************************************************************************************************
	
	
	// This function is to verify default sort on the name field
	public boolean verifydefaultsort(){
		boolean flag = false;
		ArrayList<String> nameResults = new ArrayList<>();
		ArrayList<String> subNames = new ArrayList<>();
		String[] searchName = {"a","e","d"};
		webUtil.getDriver().navigate().refresh();
		webUtil.waitUntilElementVisible(CLEARBTN);
		webUtil.click(CLEARBTN);
		this.clickOnMoreSearchOptionsBtn();
		
		try {
			int len = searchName.length;
			for(int i =0;i<=len-1;i++) {
				webUtil.setTextBoxValue("Tgl_firstname", searchName[i]);
				clickOnSearchBtn();
				List<WebElement> searchresults = webUtil.getElementsList("Tgl_SearchResultsName_TB");
				if (searchresults.size() > 4) {
					for (int j = 0; j <= searchresults.size()-1;j++) {
						nameResults.add(searchresults.get(j).getText());
						subNames.add(nameResults.get(j).substring(0,3));
						flag = true;
					}
					break;
				}
			}
		} catch (Exception e) {
			log.info("No records found",e);
			flag = false;
		}
		if(flag) webUtil.isSorted(subNames);
		return flag;
	}
	// This method verify the default appyear selection 
	public boolean verifyAppYearDefaultSelection() {
		Select appYearDD= new Select(webUtil.getElement("Tgl_appyear_dd"));
		return appYearDD.getFirstSelectedOption().getText().contains("2021");
	}

	// This method is to verify ensures right fields show up on click of "more search options"
	public boolean verifymorelinkclick() {
		boolean flag = false;
		List<WebElement> extendedContainer = webUtil.getElementsList("Tgl_MoreSearchOptionsInputContainer_Content");
		for (WebElement element : extendedContainer) {
			if (!element.isDisplayed() && (!element.isEnabled()))
				soft.fail("Input container for More search options");
			else
				flag = true;
		}
		return flag;
	}
	// This method is to verify the right columns are listed in search results table
	public boolean verifyColumnHeaders(){
		boolean flag = false;
		String[] columnHeaders = data.getTestCaseDataMap().get("ColumnHeaders").split(";");
		List<WebElement> actualColumnHeader = webUtil.getElementsList("Tgl_ColumnHeaders_TB");
		for (int i = 0; i <= actualColumnHeader.size()-1; i++) {
			if (actualColumnHeader.get(i).getText().equals(columnHeaders[i])) {
				flag = true;
			}
		}
		return flag;
	}

	// This method verify the details for the given record
	public boolean verifyRowIsLinked() {
		webUtil.getDriver().navigate().refresh();
		webUtil.waitForBrowserToLoadCompletely();
		clickOnSearchBtn();
		String actualApplicantName = clickFirstRowColumnOnSearchResults();
		return (webUtil.getDriver().getCurrentUrl().contains("details") && webUtil.getText("Tgl_ApplicantNameHeading_txt").contains(actualApplicantName));
	}
	
	// Enter invalid Person id and verify the error msg 
	public boolean verifyErrorMessageForPersonID() {
		webUtil.waitUntilElementVisible("Tgl_moreSearchOptionsLink");
		this.clickOnMoreSearchOptionsBtn();
		webUtil.setTextBoxValue("Tgl_personid", "abc");
		return (webUtil.getElement("Tgl_SearchPIDValidation_lbl").getText().contains("Please enter a number."));
	}

	/*
	 * This function will clicks on the first value on the webtable returns
	 * Applicant Name
	 */
	public String clickFirstRowColumnOnSearchResults() {
		String getApplicantName = webUtil.getText("Tgl_FirstRowColumn_TB");
		webUtil.click("Tgl_FirstRowColumn_TB");
		webUtil.holdOn(5);
		return getApplicantName;
	}

	/*
	 * This function will clicks on remove Button
	 */
	public void clickOnRemoveBtn() {
		webUtil.click("Tgl_Remove_btn");
	}

	/*
	 * This function will clicks on Search Button
	 */
	public void clickOnSearchBtn() {
		webUtil.waitForBrowserToLoadCompletely();
		webUtil.waitUntilElementVisible("Tgl_Search_btn");
		webUtil.click("Tgl_Search_btn");
	}

	/*
	 * This function will clicks on Award Calculator Button
	 */
	public void clickOnAwardCalculatorBtn() {
		webUtil.click("Tgl_AwardCalculator_btn");
	}

	/*
	 * This function will enter send Email Notes
	 */
	public void enterSendEmailNotes(String locatorName, String emailNotes) {
		webUtil.setTextBoxValue(locatorName, emailNotes);
	}

	/*
	 * This function will clicks on Send email Button
	 */
	public void clickOnSendEmailBtn() {
		webUtil.click("Tgl_SendEmail_Btn");
		webUtil.holdOn(5);
	}

	/*
	 * This function will clicks on Confirm Send Button
	 */
	public void clickOnConfirmSendBtn() {
		webUtil.click("Tgl_ConfirmSend_Btn");
	}

	/*
	 * This function will selects TGL Status Button
	 */
	public void selectTGLStatusDD(String locatorNameTGLStatus) {
		webUtil.waitUntilElementVisible("Tgl_Search_btn", 10);
		webUtil.click("Tgl_TGLStatus_DD");
		webUtil.click(locatorNameTGLStatus);
	}

	/*
	 * This function will clicks on More Search option button
	 */
	public void clickOnMoreSearchOptionsBtn() {
		webUtil.click("Tgl_MoreSearchOptions_Btn");
	}

	/*
	 * This function will enters the Person ID
	 */
	public void enterPersonIDAndClickOnSearchButton(String personID) {
		webUtil.waitForBrowserToLoadCompletely();
		webUtil.waitUntilElementVisible(CLEARBTN);
		webUtil.click(CLEARBTN);
		this.clickOnMoreSearchOptionsBtn();
		try {
			webUtil.setTextBoxValue("Tgl_personid", personID);
			this.clickOnSearchBtn();
			this.clickFirstRowColumnOnSearchResults();
		} catch (Exception e) {
			log.info("unable to Click on the Applicant" + e);
		}
	}

	/*
	 * This function will get the Applicant id where stage = incomplete/Complete
	 */
	public String clickApplicantNameOnSearchResults() {
		String applicantId = "";
		List<WebElement> elemList = webUtil.getElementsList("Tgl_ApplicantIncomplete_Lk");
		if(elemList.isEmpty()) {
			webUtil.click(CLEARBTN);
			List<WebElement> link=webUtil.getElementsList("Home_tgl_applicationyear");
			link.get(2).click();
			selectTGLStatusDD("Tgl_Complete_LK");
	 		clickOnSearchBtn();
	 		elemList = webUtil.getElementsList("Tgl_ApplicantIncomplete_Lk");
			applicantId = elemList.get(0).getText();
	 		elemList.get(0).click();
		}else {
			applicantId = elemList.get(0).getText();
			elemList.get(0).click();
		}
		
		return applicantId;
	}

}
