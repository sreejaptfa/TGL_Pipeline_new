package org.tfa.tgl.utilities.web;

import org.tfa.framework.core.WebDriverUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;
import org.apache.commons.lang3.SystemUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import org.tfa.framework.utilities.testdata.TestData;
import org.tfa.tgl.pages.common.LoginPageTgl;

@SuppressWarnings({ "squid:S4042", "squid:S899","squid:S134","squid:S1319"})
public class TGLWebUtil extends WebDriverUtil {
	private static final Logger logger = Logger.getLogger(TGLWebUtil.class);
	private TestData data = TestData.getObject();
	private static TGLWebUtil webUtil;
	WebDriverWait localwait;
	private static final String TRUE = "true";
	private static final String MAIL_POP3_HOST = "mail.pop3.host";
	private static final String MAIL_POP3_PORT = "mail.pop3.port";
	private static final String MAIL_POP3_STARTTLS_ENABLE = "mail.pop3.starttls.enable";
	private static final String MAIL_FOLDER_INBOX = "INBOX";
//	Logger log = Logger.getLogger("rootLogger");
	String downloadedFilePath;
	protected LoginPageTgl loginPage;
	SoftAssert soft = new SoftAssert();

	/*
	 * This function will upload the file.
	 */
	public void uploadFile(String filePathKeyName, String locatorName) {
		String relativeFilePath = data.getTestCaseDataMap().get(filePathKeyName);
		WebElement el = webUtil.getElement(locatorName);
		el.clear();
		el.sendKeys(new File(relativeFilePath).getAbsolutePath());
		webUtil.holdOn(5);
	}

	public LoginPageTgl openLoginPage(){
		this.getDriver().manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		String url=data.getEnvironmentDataMap().get("ApplicationURL");
		logger.debug("url -  "+url);
		openURL(url);
		return new LoginPageTgl();
	}
	
	public void waitUntilElementVisible(String locatorName){
		localwait = new WebDriverWait(webUtil.getDriver(), 15);
		By locatorValue = webUtil.getLocatorBy(locatorName);
		localwait.until(ExpectedConditions.visibilityOfElementLocated(locatorValue));
	}
	/*
	 * This function will download the file.
	 */
	public void downloadFile(String fileName) {
		if (SystemUtils.IS_OS_LINUX) {
			downloadedFilePath = "/home/" + System.getProperty("user.name") + "/Downloads/" + fileName;
		} else {
			downloadedFilePath = "C:\\Users\\" + System.getProperty("user.name") + "\\Downloads\\" + fileName;
		}
		File file = new File(downloadedFilePath);
		if (file.exists()) {
			file.delete();
			logger.info("File downloaded Successfully");
			Assert.assertTrue(true);
		} else {
			logger.info("File not downloaded");
			Assert.assertFalse(true);
		}

	}

	/*
	 * This function will click on review or remove links on the particular document
	 */
	public WebElement getValuesFromDocumentsWebTable(String tableLocatorName, String refRowDataToSearch,
			String expressionType) {
		WebElement element = null;
		Map<String, String> locatorValueMap = webUtil.getLocatorValueMap(tableLocatorName);
		String locatorValue = getLocatorValue(locatorValueMap, tableLocatorName);
		List<WebElement> tableRowValues = webUtil.getDriver().findElements(By.xpath(locatorValue));
		for (int i = 1; i <= tableRowValues.size()-1; i++) {
			String rowWiseCellData = tableRowValues.get(i).getText();
			try {
				if (rowWiseCellData.contains(refRowDataToSearch)) {
					switch (expressionType) {
					case "Review":
						return webUtil.getDriver().findElement(By.xpath(locatorValue + "["+ i+ "]//td[3]//a"));
					case "Remove":
						return webUtil.getDriver().findElement(By.xpath(locatorValue + "["+ i+ "]//td[3]//button"));
					default:
						break;
					}
				}
			} catch (Exception e) {
				logger.info(e);
			}
		}
		return element;
	}

	/*
	 * This function will gets the Locator Value
	 */
	public static String getLocatorValue(Map<String, String> locatorValueMap, String locatorName) {
		String locatorValue = null;
		try {
			locatorValue = locatorValueMap.get("LocatorValue");
			if ("".equals(locatorValue.trim())) {
				logger.info("LocatorValue is blank for " + locatorName);
			}
		} catch (Exception e) {
			logger.info("LocatorValueMao is blank for " + locatorName);
			throw e;
		}

		return locatorValue;
	}

	/*
	 * This function will Clears the TextBox value
	 */
	public void setTextBoxClear(String locatorName) {
		By locator = null;
		locator = webUtil.getLocatorBy(locatorName);
		webUtil.getDriver().findElement(locator).clear();
	}

	/*
	 * This function will verifies the object is visible on the Page
	 */
	public boolean objectIsVisible(String locatorName) {
		boolean flag = false;
		By locator = null;
		try {
			locator = webUtil.getLocatorBy(locatorName);
			return (webUtil.getDriver().findElement(locator).isDisplayed());
		} catch (Exception e) {
			flag = false;
			logger.info(e);
			}
		return flag;
	}

	/*
	 * This function will verifies the object is enabled on the Page
	 */
	public boolean objectIsEnabled(String locatorName) {
		boolean flag = false;
		By locator = null;
		try {
			locator = webUtil.getLocatorBy(locatorName);
			flag = (webUtil.getDriver().findElement(locator).isEnabled());
		} catch (Exception e) {
			flag = false;
			logger.info(e);
		}
		return flag;
	}

	/*
	 * This function is to get the object of WebdriverUtil.
	 * 
	 * @return instance of WebdriverUtil class.
	 */
	public static TGLWebUtil getObject() {
		if (webUtil == null) {
			webUtil = new TGLWebUtil();
		}
		return webUtil;
	}

	/*
	 * This function is to verify the values from WebTable
	 * 
	 * @return true or false.
	 */
	public boolean verifyTheValueInWebTableElement(String rowLocatorName, String columnLocatorName,
			String[] expectedValues) {
		String elementText = null;
		By rowLocator = getLocatorBy(rowLocatorName);
		By colLocator = getLocatorBy(columnLocatorName);
		Set<String> expectedValuesSet = new HashSet<>();
		expectedValuesSet.addAll(Arrays.asList(expectedValues));
		List<WebElement> tableRowValues = getDriver().findElements(rowLocator);
		for (WebElement trElement : tableRowValues) {
			WebElement tdElement = trElement.findElement(colLocator);
			elementText = tdElement.getText();
			if (expectedValuesSet.contains(elementText)) {
				expectedValuesSet.remove(elementText);
			}
		}
		return (expectedValuesSet.isEmpty());

	}

	/*
	 * This function switches the Frame to the default
	 */
	public void switchToWindowFromFrame() {
		try {
			getDriver().switchTo().defaultContent();
		} catch (WebDriverException e) {
			logger.info(e);

		}
	}

	/*
	 * Login to mailbox for test email account and verify the email content match
	 * with Email template returns true and false
	 */
	public boolean checkEmailContentFromTestEmailAccount(String host, String userEmail, String password,
			String bodyContent) {
		Store emailStore = null;
		try {
			Properties properties = new Properties();
			properties.put(MAIL_POP3_HOST, "pop3s");
			properties.put(MAIL_POP3_PORT, "995");
			properties.put(MAIL_POP3_STARTTLS_ENABLE, TRUE);
			Session emailSession = Session.getDefaultInstance(properties);

			emailStore = emailSession.getStore("pop3s");
			emailStore.connect(host, userEmail, password);
			Folder emailFolder = emailStore.getFolder(MAIL_FOLDER_INBOX);
			emailFolder.open(Folder.READ_WRITE);

			Message[] emailMessages = emailFolder.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
			if (emailMessages.length == 0) {
				return false;
			} else {
				int len = emailMessages.length - 10;
				for (int i = len; i < emailMessages.length; i++) {
					Message emailMessage = emailMessages[i];
					Object content = emailMessage.getContent();
					if (content.toString().contains(bodyContent)) {
						emailFolder.close(false);
						return true;
					}
				}
			}
		} catch (Exception e) {
			logger.error("email not found ", e);
		}
		return false;
	}

	/**
	 * This function will open the login page
	 */
	public void openLoginPage(String url) {
		webUtil.getDriver().manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		logger.debug("url -  " + url);
		if (url == null) {
			logger.debug("data object is null");
		} else {
			logger.debug("data object is not null");
		}
		webUtil.getDriver().get(url);
		webUtil.holdOn(3);
		webUtil.waitForBrowserToLoadCompletely();
	}
	
	/**
	 * this method is to verify the objects on Document Information section
	 */
	public boolean verifyDocumentInformationSection(String sectionName) {
		boolean flag = false;
		String[] sectionObjects= data.getTestCaseDataMap().get(sectionName).split(":");
		int len = sectionObjects.length;
		try {
			for (int i = 0; i < len; i++) {
				if ((objectIsEnabled(sectionObjects[i])) || (objectIsVisible(sectionObjects[i])) ) {
					flag = true;
					soft.assertTrue(true);
				} else {
					soft.assertTrue(false, "Object Not found ->" + sectionObjects[i]);
				}
			}
		} catch (WebDriverException e) {
			soft.assertTrue(false, "Object not found");
			soft.fail();
			logger.info("Object not found",e);
		} 
		finally {
			soft.assertAll();
		}
		return flag;
	}
	
	//this method is to verify the sorted Order
	public boolean isSorted(ArrayList<String> sort) {
		Iterator<String> iter = sort.iterator();
		String current;
		String previous = iter.next();
		previous = previous.toLowerCase();
		while (iter.hasNext()) {
			current = iter.next();
			current = current.toLowerCase();
			if (previous.compareTo(current) > 0) {
				return false;
			}
			previous = current;
		}
		return true;
	}
}
