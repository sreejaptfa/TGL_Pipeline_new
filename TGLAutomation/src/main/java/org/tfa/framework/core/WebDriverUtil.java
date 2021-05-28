package org.tfa.framework.core;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.UnexpectedTagNameException;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.tfa.framework.utilities.general.RandomUtil;
import org.tfa.framework.utilities.general.SystemUtil;
import org.tfa.framework.utilities.testdata.TestData;

/**
 * This class is having all the generic methods which are required to perform actions specific to Web Driver.
 * @author gaurav.garg
 *
 */
public class WebDriverUtil {

	private static final Logger logger = Logger.getLogger(WebDriverUtil.class);
	private WebElement element;
	private static WebDriverUtil webUtil;
	private TestData data=TestData.getObject();
	private RandomUtil random=new RandomUtil();
	private static final String USER_DIR="user.dir";
	private static WebDriver driver = null;
    private JavaScriptUtil jsUtil;
	long waitTime = 60;
    /**
	 * This function will launch browser according to ENV Sheet Browser Column Value, If the ENV sheet has Chrome then it will launch Chrome browser.
	 */
	public void launchDriver() {
		try {
			TestData testData = TestData.getObject();
			logger.debug("WebDriverUtil: testData: " + testData);
			Map<String, String> environmentDataMap = testData.getEnvironmentDataMap();
			logger.debug("environmentDataMap:" + environmentDataMap);

			String browserName = environmentDataMap.get("Browser");
			logger.debug("browserName:" + browserName);

			setBrowserWindow(browserName);
			driver.manage().timeouts().implicitlyWait(waitTime, TimeUnit.SECONDS);
		} catch (Exception e) {
			logger.debug( "Browser has not been launched", e);
		}


	}

	protected void getOptions(){
		//
	}

	protected void setBrowserWindow(String browserName) {
		if (("chrome").equalsIgnoreCase(browserName)) {
            ChromeOptions options = new ChromeOptions();
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
            capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
            options.addArguments("test-type");
           
            if(SystemUtils.IS_OS_LINUX){
            	 options.addArguments("--headless"); // this options is for linux environment on docker
            }
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            setDriver(new ChromeDriver(options));
            Dimension dim=new Dimension(1382, 744);
            driver.manage().window().setSize(dim);
            logger.info( "Chrome Browser has been launched successfully");
        }

        else if (("ie").equalsIgnoreCase(browserName)) {

            DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
            capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
                    true);
            capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
            capabilities.setCapability(InternetExplorerDriver.ENABLE_ELEMENT_CACHE_CLEANUP, true);
            setDriver(new InternetExplorerDriver(capabilities));
            driver.manage().window().maximize();
            logger.info( "Internet Explorer Browser has been launched successfully");
        }

        else if (("firefox").equalsIgnoreCase(browserName)) {
            setDriver(new FirefoxDriver());
            driver.manage().deleteAllCookies();
            driver.manage().window().maximize();
            logger.info( "Firefox Browser has been launched successfully");
        }
	}

	/**
	 * This function will close all the opened browser and will kill the driver object.
	 */
	public void stopDriver() {
		try {
			if (driver != null) {
				driver.quit();
				setDriver(null);
				logger.info("browser has been closed successfully");
			}
		} catch (Exception ignore) {
			logger.info( "browser has not been closed properly", ignore);
		}
	}

	/**
	 * This method will open the URL which was passed as parameter.
	 * @param url URL of the site which you want to get it opened.
	 */
	public void openURL(String url){
		try{
			driver.get(url);
			logger.info( url+" opened successfully");
		}catch(WebDriverException e){
			logger.debug( "Error occured while opening url- "+url, e);
		}catch(NullPointerException e){
			logger.debug( "Error occured while opening url. webdriver object has null value - ", e);
		}
	}

	/**
	 * This function is to get the object of WebdriverUtil.
	 * @return instance of WebdriverUtil class.
	 */
	public static WebDriverUtil getObject(){
		if(webUtil==null){
			webUtil=new WebDriverUtil();
		}
		return webUtil;
	}

	/**
	 * This function will click on web element by fetching the web element from the locator name and will wait for page to load completely.
	 * @param locatorName LocatorName of web element which you want to get clicked.
	 */
	public void click(String locatorName) {
		this.element = getElement(locatorName);
		try{
		   click(element);		
		}catch(StaleElementReferenceException e){
			logger.info("StaleElementException occured try again to click on "+locatorName, e);
			this.element = getElement(locatorName);
			click(element);
		}catch(WebDriverException e){
			logger.info(e);	
			jsUtil.scrollingToElementOfAPage(locatorName);
			jsUtil.jsClick(locatorName);
		}
		waitForBrowserToLoadCompletely();
	}
	
	/**
	 * This function will click on web element and will wait for page to load completely.
	 * @param element Web element which you want to get clicked.
	 */
	public void click(WebElement element) {
		try{
			element.click();
		}catch(ElementNotVisibleException e){
			logger.info("ElementNotVisibleException occured try again to click"+e);
			jsUtil.jsClick(element);	
		}
	}


	/**
	 * This function will input value in text boxes.
	 * @param locatorName Locator Name of webelement defined in Locator Source.
	 * @param fieldName Test data fieldName defined in TestData Sheet.
	 * @return String value.
	 */
	public String setTextBoxValueTestData(String locatorName, String fieldName) {
		String textToInput="";
		textToInput=data.getTestCaseDataMap().get(fieldName);
		setTextBoxValue(locatorName, textToInput);
		return textToInput;
	}

	/**
	 * This function will input value in Lookups present in the application and then click on search to get the desired result.
	 * @param locatorName Locator Name of webelement defined in Locator Source
	 * @return String value
	 */
	public String setTextBoxValue(String locatorName, String textToInput){
		try{
			this.element = getElement(locatorName);
			element.clear();
			element.sendKeys(textToInput);
			logger.info(textToInput+" is entered in "+locatorName+" text box");
		}catch(ElementNotVisibleException e){
			logger.info("ElementNotVisibleException occured  for textbox "+locatorName+" , trying javascript to enter value",e);			
			if(getElementsList(locatorName).size()==1){
				jsUtil.scrollingToElementOfAPage(locatorName);
				jsUtil.jsSetTextBoxValue(locatorName, textToInput);
			}else{
				logger.info("more than one element found for "+locatorName+". so entering value through java script",e);	
			}		
		}catch(StaleElementReferenceException e){
			logger.info(e);		
			this.element = getElement(locatorName);
			element.clear();
			element.sendKeys(textToInput);
		}catch(WebDriverException e){
			logger.info(e);	
			jsUtil.scrollingToElementOfAPage(locatorName);
			jsUtil.jsSetTextBoxValue(locatorName, textToInput);
		}

		return textToInput;
	}

	/**
	 * This function will create the By object using the type of locator mentioned in the Tets data sheet.
	 * @param locatorName Locator Name of webelement defined in Locator Source.
	 * @return By object.
	 */
	public By getLocatorBy( String locatorName){
		By locatorBy=null;
		String locatorValue=null;
		String locatorType=null;
		logger.debug("getLocatorBy method");
		Map<String, String> locatorValueMap=getLocatorValueMap(locatorName);
		locatorValue=getLocatorValue(locatorValueMap, locatorName);
		locatorType=getLocatorType(locatorValueMap, locatorName);
		try{
			if(locatorType.trim().equalsIgnoreCase(Constants.LOCATOR_XPATH)){
				locatorBy=By.xpath(locatorValue);
			}else if(("css").equalsIgnoreCase(locatorType.trim())){
				locatorBy=By.cssSelector(locatorValue);
			}else if(("id").equalsIgnoreCase(locatorType.trim())){
				locatorBy=By.id(locatorValue);
			}else if(("name").equalsIgnoreCase(locatorType.trim())){
				locatorBy=By.name(locatorValue);
			}else if(("linkText").equalsIgnoreCase(locatorType.trim())){
				locatorBy=By.linkText(locatorValue);
			}else if(("class").equalsIgnoreCase(locatorType.trim())){
				locatorBy=By.className(locatorValue);
			}else if(("tag").equalsIgnoreCase(locatorType.trim())){
				locatorBy=By.tagName(locatorValue);
			}
		}catch(Exception e){	
			logger.debug( "error occured during getting 'By' Object", e);
			throw e;
		}
		logger.info( locatorName+"- "+locatorType+" - "+locatorValue+" is used to locate the element");	    
		return locatorBy;
	}


	/**
	 * This function will pause the current thread by specified time in seconds.
	 * @param timeOutInSeconds time in seconds.
	 */
	public void holdOn(int timeOutInSeconds){
		try {
			long time=(long)1000*timeOutInSeconds;
			Thread.sleep(time);
		} catch (Exception e) {	
			logger.debug( "InterruptedException occured", e);
		}
	}

	/**
	 * This function will return inner-text of the web element which is fecthed from the locator name provided.
	 * @param locatorName Locator Name of webelement defined in Locator Source.
	 * @return String inner text of specified web element.
	 */
	public String getText(String locatorName){
		this.element = getElement(locatorName);
		return element.getText();
	}


	/**
	 * This function will return attribute value of provided attribute name for specified locator name.
	 * @param locatorName Locator Name of webelement defined in Locator Source.
	 * @param attributeName Attribute name for which you want to get the value.
	 * @return String attribute value of provided attribute name.
	 */
	public String getAttributeValue(String locatorName, String attributeName){
		this.element = getElement(locatorName);
		return element.getAttribute(attributeName);
	}

	/**
	 * This function will return title of current page. 
	 * @return  String value of title.
	 */
	public String getPageTitle(){
		return driver.getTitle();
	}

	/**
	 * This function will return status of webelement in boolean format.
	 * @param locatorName Locator name provided in the locator source.
	 * @return True if element is enabled, otherwise false.
	 */
	public boolean isEnabled(String locatorName){
		this.element = getElement(locatorName);
		return this.element.isEnabled();
	}

	/**
	 * This function will return display status of webelement in boolean format.
	 * @param locatorName Locator name provided in the locator source.
	 * @return True if element is visible, otherwise false.
	 */
	public boolean isVisible(String locatorName){

		this.element = getElement(locatorName);

		return this.element.isDisplayed();
	}

	/**
	 * This function will return List of webelements from the page on the basis of provided locator name
	 * @param locatorName LocatorName Defined in Locator Source
	 * @return List of Webelements found on the page on the basis of locator value
	 */
	public List<WebElement> getElementsList(String locatorName) {
		By locator=null;
		locator=getLocatorBy(locatorName);
		return driver.findElements(locator);
	}


	/**
	 * This function will return Object of WebElement Type on the basis of provided locator name.
	 * @param locatorName LocatorName Defined in Locator Source
	 * @return Webelement found on the page on the basis of locator value
	 */
	public WebElement getElement(String locatorName) {
		WebElement we=null;
		By locator=null;

		try {	
			locator=getLocatorBy(locatorName);	
			we=driver.findElement(locator);
		} catch (NoSuchElementException e) {
			logger.debug( Constants.ELEMENT_SEARCH_ERROR_MESSAGE, e);
			waitUntilElementVisible(locatorName, 60);
			return driver.findElement(locator);

		}
		return we;
	}

	/**
	 * This method is to wait for the element till its visible on the page.
	 * @param locatorName Locator name for which user want to wait for its visibility.
	 * @param timeOut Max wait time.
	 * @return Returns true or false.
	 */
	public boolean waitUntilElementVisible(String locatorName, int timeOut) {
		By locator=getLocatorBy(locatorName);	
		WebDriverWait wdWait=new WebDriverWait(driver, timeOut);
		try{
			wdWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			return true;
		}catch(TimeoutException e){
			logger.info( locatorName+" has been searched for "+timeOut+" seconds but not found.", e);

		}
		return false;
	}

	/**
	 * This method is to select the particular element
	 * @param locatorName Locator name of the element.
	 * @return Returns object of Select.
	 */
	protected Select select(String locatorName) {
		Select selectElement = null;
		this.element = getElement(locatorName);
		try {
			selectElement = new Select(element);
		} catch (UnexpectedTagNameException e) {
			logger.error("Element " + locatorName + " was not with select tag name   Error Message UnexpectedTagNameException  -->",e);
		}
		return selectElement;
	}


	/**
	 * This method is to de-select all the selected options from drop-down that is supporting multiple selection.
	 * @param locatorName LocatorName Defined in Locator Source
	 */
	public void deselectAll(String locatorName) {
		Select select =select(locatorName);
		try{
			if(select!=null){
				select.deselectAll();
			}
		}catch(Exception e){
			logger.error( Constants.ELEMENT_SEARCH_ERROR_MESSAGE, e);
		}	
	}


	/**
	 * This function will return List of all the selected options from a drop-down.
	 * @param locatorName LocatorName Defined in Locator Source.
	 * @return Returns List<WebElement> of selected options.
	 */
	public List<WebElement> getAllSelectedOptions(String locatorName) {
		Select select =select(locatorName);
		List<WebElement> listSelectedOptions=null;
		try{
			if(select!=null){
				listSelectedOptions= select.getAllSelectedOptions();
			}
		}catch(Exception e){
			logger.error( Constants.ELEMENT_SEARCH_ERROR_MESSAGE, e);
		}
		return listSelectedOptions;
	}


	/**
	 * This method will return WebElement Object of selected option in single selection dropdown and first selected option from multiple options selection dropdown.
	 * @param locatorName LocatorName Defined in Locator Source.
	 * @return Returns WebElemnt of selected options.
	 */
	public WebElement getFirstSelectedOption(String locatorName) {
		Select select =select(locatorName);
		WebElement selectedWebElement=null;	
		try{
			if(select!=null){
				selectedWebElement= select.getFirstSelectedOption();
			}	
		}catch(Exception e){
			logger.debug( Constants.ELEMENT_SEARCH_ERROR_MESSAGE, e);
		}
		return selectedWebElement;
	}

	/**
	 * This function will return text of selected option from dropdown.
	 * @param locatorName LocatorName Defined in Locator Source.
	 * @return Retruns text of selected option from dropdown.
	 */
	public String getSelectedOptionText(String locatorName) {
		Select select =select(locatorName);
		WebElement selectedWebElement=null;	
		try{
			if(select!=null){
				selectedWebElement= select.getFirstSelectedOption();
				return selectedWebElement.getText();
			}	
		}catch(Exception e){
			logger.error( Constants.ELEMENT_SEARCH_ERROR_MESSAGE, e);
		}

		return null;
	}


	/**
	 * This function will return List of all the selected options from a drop-down.
	 * @param locatorName LocatorName Defined in Locator Source.
	 * @return Returns List<WebElement> of selected options.
	 */
	public List<WebElement> getAllOptions(String locatorName) {
		Select select =select(locatorName);
		List<WebElement> listAllOptions=null;
		try{
			if(select!=null){
				listAllOptions= select.getOptions();
			}	
		}catch(Exception e){
			logger.error( Constants.ELEMENT_SEARCH_ERROR_MESSAGE, e);
		}
		return listAllOptions;

	}

	/**
	 * This function will switch focus on window or tab that has specified title.
	 * @param title Title of the page.
	 */
	public void switchWindowFocusByTitle(String title) {
		try{
			Set<String> handleValues=driver.getWindowHandles();
			for(String handleValue:handleValues){
				driver.switchTo().window(handleValue);	
				if(driver.getTitle().equalsIgnoreCase(title)){
					break;
				}
			}
		}catch(Exception e){
			logger.debug( Constants.ELEMENT_SEARCH_ERROR_MESSAGE, e);
		}
	}


	/**
	 * This function will select elements in dropdown according to index number.
	 * index number starts from 0.
	 * @param locatorName Locator name of the drop down.
	 * @param index Number which needs to be selected in the drop down.
	 * @return Returns the text of the selected option.
	 */
	public String selectByIndex(String locatorName, int index) {
		String selectedText=null;
		try{
			Select select=select(locatorName);
			if(select!=null){
				select.selectByIndex(index);
			}
			selectedText=getFirstSelectedOption(locatorName).getText(); 
		}catch(Exception e){
			logger.debug( Constants.ELEMENT_SEARCH_ERROR_MESSAGE, e);
		}
		return selectedText;
	}


	/**
	 * This function will select any option randomly and will return the text of selected random option.
	 * @param locatorName Locator name of the drop down
	 * @return Returns the text of the selected option.
	 */
	public String selectRandomValueFromListBox(String locatorName) {
		String selectedText=null;

		try{
			Select selectObj=select(locatorName);
			List<WebElement> dropDownOptionsList=null;
			if(selectObj!=null){
				dropDownOptionsList=selectObj.getOptions();
				int optionCount=dropDownOptionsList.size();
				int randomIndex=random.generateRandomNumber(1, optionCount-1);
				selectObj.selectByIndex(randomIndex);
				selectedText= selectObj.getFirstSelectedOption().getText();
			}
		}catch(Exception e){
			logger.debug(selectedText+" Option is not selected randomly", e);
			throw e;
		}
		return selectedText;
	}

	/**
	 * This method is used to select the option in the drop down using the value attribute of the field. 
	 * @param locatorName Drop down locator name.
	 * @param fieldName Field name for which value needs to be selected.
	 * @return Returns the text of the selected option.
	 */
	public String selectByValue(String locatorName, String fieldName) {
		String selectedText=null;
		try{
			String valueToSelect="";
			this.element = getElement(locatorName);
			valueToSelect=data.getTestCaseDataMap().get(fieldName);
			Select select=select(locatorName);
			if(select!=null){
				select.selectByValue(valueToSelect);
			}

			selectedText=getFirstSelectedOption(locatorName).getText();
		}catch(Exception e){
			logger.debug(selectedText+" Option is not selected randomly", e);
		}

		return selectedText;
	}

	/**
	 * This function will select elements in dropdown on the basis of visible text, but this method accepts the locator name provided in the data sheet.
	 * From the datasheet text value will be fetched and then the value will be selected in the fdrop down.
	 * @param locatorName Locator name of the drop down.
	 * @param fieldName Name of the field which is mentioned in the data sheet.
	 * @return
	 */
	public String selectByVisibleTextTestData(String locatorName, String fieldName) {
		String textToSelect="";
		textToSelect=data.getTestCaseDataMap().get(fieldName);
		return selectByVisibleText(locatorName, textToSelect);
	}


	/**
	 * This method is used to select the value in the dropdown on the basis of the text provided as argument.
	 * @param locatorName Drop down locator name.
	 * @param textToSelect Value which needs to be selected in the dropdown.
	 * @return String value.
	 */
	public String selectByVisibleText(String locatorName, String textToSelect) {
		this.element = getElement(locatorName);
		Select select=select(locatorName);
		if(select!=null){
			select.selectByVisibleText(textToSelect);
		}
		return textToSelect;
	}

	/**
	 * This method is to get the locator value from the test data sheet where all the locators are specified.
	 * @param locatorName for which we need the the value
	 * @return Map<String, String>
	 */
	public Map<String, String> getLocatorValueMap(String locatorName){
		logger.debug("getLocatorValueMap: ");
		logger.debug("data: "+ data);
		Map<String, Map<String, String>> locatorDataMap = data.getLocatorDataMap();
		logger.debug("locatorDataMap: "+ locatorDataMap);
		Map<String, String> locatorDetailMap= locatorDataMap.get(locatorName);
		if(locatorDetailMap==null){
			logger.error( locatorName+" is not found from locator data source");
		}else{
			logger.info( locatorName+" is found in locator data source");
		}
		return locatorDetailMap;
	}
	
	/**
	 * This method is get the exact value of the locator from the map created.
	 * @param locatorValueMap Map<String, String> where all the locators are stored
	 * @param locatorName Name of the locator for which we need the value.
	 * @return String
	 */
	private static String getLocatorValue(Map<String, String> locatorValueMap, String locatorName){
		String locatorValue=null;
		try{
			locatorValue=locatorValueMap.get("LocatorValue");
			if("".equals(locatorValue.trim())){
				logger.error( "LocatorValue is blank for "+locatorName);
			}
		}catch(Exception e){
			logger.error( "LocatorValueMap is blank for "+locatorName);
			throw e;
		}
		return locatorValue;
	}

	/**
	 * This method is to get the type of the locator whether it is text box, drop down etc.
	 * @param locatorValueMap Map<String, String> where all the locators are stored
	 * @param locatorName Name of the locator for which we need the type.
	 * @return
	 */
	private static String getLocatorType(Map<String, String> locatorValueMap, String locatorName){
		String locatorType=null;
		try{
			locatorType=locatorValueMap.get("LocatorType");
			if("".equals(locatorType.trim())){
				logger.error( "LocatorType is blank for "+locatorName);
			}
		}catch(Exception e){
			logger.error( "LocatorTypeMap is null for "+locatorName);
			throw e;
		}
		return locatorType;
	}

	/**
	 * This function will take screenshot of current html page and will save to specified location.
	 * @param methodName Test case name or method name for which screenshot has captured.
	 */
	public  void takeScreenShot(String methodName) {
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			String timeStamp=SystemUtil.getObject().getTimeStamp(Constants.DATE_FORMAT_PATTERN);
			File destFile=new File(System.getProperty(USER_DIR)+File.separator+"test-output"+ File.separator+methodName+"_"+timeStamp+".png");
			FileUtils.copyFile(scrFile, destFile);
			Reporter.log("<a href='"+ destFile.getAbsolutePath() + "'> <img src='"+ destFile.getAbsolutePath() + "' height='100' width='100'/> </a>");
		} catch (IOException e) {
			logger.error( "Not able to take snapshot of current page of application", e);
		}
	}

	/**
	 * This method is used to wait for the browser to load the page completely.
	 */
	public void waitForBrowserToLoadCompletely() {
		String state = null;
		String oldstate = null;
		String waitingMsg="Waiting for browser loading to complete";
		if(loadBrowser()){
			return;
		}
		logger.info( waitingMsg);
		int i = 0;

		while (true) {
			state = ((JavascriptExecutor) driver).executeScript("return document.readyState;").toString();
			if ("complete".equals(state))
				break;

			if (state.equals(oldstate))
				i++;
			else
				i = 0;
			if (i == 15 && "loading".equals(state)) {
				driver.navigate().refresh();
				logger.info( waitingMsg);
				i = 0;
			} else if (i == 6 && "interactive".equals(state)) {
				logger.info( waitingMsg);
				return;
			}
			oldstate = state;
		}
	}

	/**
	 * This method is used to check whether browser is loaded or not.
	 * @return Returns the boolean value.
	 */
	protected boolean loadBrowser(){
		String state = null;
		int i=0;
		while (i < 5) {

			state = ((JavascriptExecutor) driver).executeScript("return document.readyState;").toString();
			if ("interactive".equals(state) || "loading".equals(state))
				break;
			if (i == 1 && "complete".equals(state)) {

				return true;
			}
			i++;
		}
		return false;
	}

	/**
	 * This method is used to select the multiple values from a picklist on the basis of the values provided.
	 * @param locatorName Name of the multi picklist locator.
	 * @param arrayPickListOptions Array of the values from which we need to select the values.
	 */
	public void multiSelect(String locatorName, String[] arrayPickListOptions) {
		for(int i=0; i<=arrayPickListOptions.length-1; i++){
			Select selectPickList=select(locatorName);
			if(selectPickList!=null){
				selectPickList.selectByVisibleText(arrayPickListOptions[i]);
			}

		}

	}

	/**
	 * This function will set the driver to requested browser
	 * @param driver for Chrome, FireFox or IE
	 */
	protected static void setDriver(WebDriver driver) {
		WebDriverUtil.driver = driver;
	}

	/**
	 * This function will return the object of launched browser
	 */
	public WebDriver getDriver(){
		return driver;
	}

	/**
	 * This method will provide you the column number in the web table on the basis of column name provided.
	 * @param listElementColumns List of all the column names.
	 * @param columnName Actual column name for which the number is required.
	 * @return Returns the column number as Int.
	 */
	public int getTableColumnNumberByColumnName(List<WebElement> listElementColumns, String columnName){
		int columnNumber=-1;
		for(int i=0; i<=listElementColumns.size()-1; i++){
			WebElement elementColumn = listElementColumns.get(i);
			String columnText=elementColumn.getText().trim();
			if(columnText.equalsIgnoreCase(columnName)){
				columnNumber=i;

			}
		}

		return columnNumber;
	}
	
	/**
     * This function will switch focus into frame.
     * @param frameLocatorName of the iFrame from OR sheet.
     */
     public void switchToFrameByFrameLocator(String frameLocatorName) {
            try{
                   this.element = getElement(frameLocatorName);
                   driver.switchTo().frame(element);
            }catch(Exception e){
                   logger.error( "unable to locate frame by locator name - "+frameLocatorName, e);
            }
     }
     
     /**
     * This function will switch to parent frame.
     * @param no params.
     */
     public void switchToParentFrame() {
            try{
                   
                   driver.switchTo().parentFrame();
            }catch(Exception e){
                   logger.error( "parent frame unavailable", e);
            }
     }
     
     /**
     * This function will switch to Main Webdriver Window.
     * @param no params.
     */
     public void switchToMainWindowFromFrame() {
            try{
                   
                   driver.switchTo().defaultContent();
            }catch(Exception e){
                   logger.error( "unable to switch to main window", e);
            }
     }

	
	
	
	
	
}
