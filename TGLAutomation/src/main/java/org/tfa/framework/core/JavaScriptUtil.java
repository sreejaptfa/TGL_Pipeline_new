package org.tfa.framework.core;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

/**
 * This class is having the all the methods which we are calling using the javascript like click, scroll etc.
 * @author gaurav.garg
 *
 */
public class JavaScriptUtil {
	private static JavaScriptUtil jsUtil;
	private WebElement element;

	private JavaScriptUtil(){

	}
	public   JavascriptExecutor getJSExecutor(){
		return ((JavascriptExecutor) WebDriverUtil.getObject().getDriver());
		
	}

	public  static JavaScriptUtil getObject(){
		if(jsUtil==null){			 
			jsUtil=new JavaScriptUtil();	
		}

		return jsUtil;
	}
	
	private  static final String JS_SCROLL_CODE="arguments[0].scrollIntoView(true);";
	
	/**
	 * This method is used to scroll the page till the Locator provided, this method will find the element from the locator name and then scroll the page till that element
	 * @param locatorName Locator name 
	 */
	public void scrollingToElementOfAPage(String locatorName) {
		this.element = WebDriverUtil.getObject().getElement(locatorName);
		getJSExecutor().executeScript(JS_SCROLL_CODE, element);
	}

	/**
	 * This method is for scrolling the page up
	 */
	public void scrollUpPage() {
		getJSExecutor().executeScript("window.scrollTo(2000,0)");
	}
	
	/**
	 * This method is for scrolling the page down
	 */
	public void scrollDownPage(Integer numberOfPixels) {
		getJSExecutor().executeScript("window.scrollTo(0,\""+numberOfPixels+"\")");

	}
	
	/**
	 * This method is used to scroll the page till the exact web element provided.
	 * @param we Web element name 
	 */
	public void scrollingToElementOfAPage(WebElement we) {

		getJSExecutor().executeScript(JS_SCROLL_CODE, we);
	}

	/**
	 * This method is used to click on the particular element by getting the locator name of that element.
	 * @param locatorName Name of locator
	 */
	public void jsClick(String locatorName) {
		this.element = WebDriverUtil.getObject().getElement(locatorName);
		getJSExecutor().executeScript("arguments[0].click()", element);

	}

	/**
	 * This method is used to click on the particular element.
	 * @param element Web Element
	 */
	public void jsClick(WebElement element) {

		getJSExecutor().executeScript("arguments[0].click()", element);

	}

	/**
	 * This method is used to set the particular value in the text box by using the Java Script.
	 * @param locatorName Name of the locator for which you want to set the value.
	 * @param value A string value which needs to be passed to the locator.
	 */
	public void jsSetTextBoxValue(String locatorName, String value) {
		this.element = WebDriverUtil.getObject().getElement(locatorName);
		getJSExecutor().executeScript("document.getElementById('elementID').setAttribute('value', '"+value+"')");

	}

}
