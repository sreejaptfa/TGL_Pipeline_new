package org.tfa.framework.core;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * This class contains the method which are used for waiting the application like wait till element visible, wait till page load etc.
 * @author gaurav.garg
 *
 */
public class WaitUtil {

	private WebDriverUtil webUtil=WebDriverUtil.getObject();
    private static WaitUtil waitUtil;
	
	private WaitUtil(){
		
	}
	public static WaitUtil  getObject(){
		if(waitUtil==null){
			waitUtil=new WaitUtil();
		}
		return waitUtil;
	}
	
	/**
	 * This method is used to wait for a particular element till that element gets visible on the application. 
	 * @param locatorName Locator name for which the application needs to wait.
	 * @param timeOut Time in seconds, till what time system should wait for that element.
	 */
	public void waitUntilElementVisible(String locatorName, int timeOut){
		WebDriverWait wt = new WebDriverWait(WebDriverUtil.getObject().getDriver(), timeOut);
		wt.until(ExpectedConditions.visibilityOfElementLocated(webUtil.getLocatorBy(locatorName)));
	}

	/**
	 * This method is used to wait for a particular element till that element is enabled in the application. 
	 * @param locatorName Locator name for which the application needs to wait.
	 * @param timeOut Time in seconds, till what time system should wait for that element.
	 */
	public void waitUntilElementEnabled(String locatorName, int timeOut){
		WebDriverWait wt = new WebDriverWait(WebDriverUtil.getObject().getDriver(), timeOut);
		wt.until(ExpectedConditions.elementToBeClickable(webUtil.getLocatorBy(locatorName)));
	}
	
	/**
	 * This method is used to wait for a particular element till that element has the value specified in the argument. 
	 * @param locatorName Locator name for which the application needs to wait.
	 * @param textTobePresent String value which needs to be checked.
	 * @param timeOut Time in seconds, till what time system should wait for that element.
	 */
	public void waitUntilTextChangedToSpecified(String locatorName,String textTobePresent,  int timeOut){
		WebDriverWait wt = new WebDriverWait(WebDriverUtil.getObject().getDriver(), timeOut);
		wt.until(ExpectedConditions.textToBe(webUtil.getLocatorBy(locatorName), textTobePresent));
	}
	
	/**
	 * This method is used to wait for a particular element till that element gets invisible on the application. 
	 * @param locatorName Locator name for which the application needs to wait.
	 * @param timeOut Time in seconds, till what time system should wait for that element to become invisible.
	 */
	public void waitUntilElementInVisible(String locatorName, int timeOut){
		WebDriverWait wt = new WebDriverWait(WebDriverUtil.getObject().getDriver(), timeOut);
		wt.until(ExpectedConditions.invisibilityOfElementLocated(webUtil.getLocatorBy(locatorName)));	
	}


}
