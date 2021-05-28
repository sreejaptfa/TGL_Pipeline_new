package org.tfa.framework.core;

import org.apache.log4j.Logger;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class MouseUtil {

	private static MouseUtil mouseUtil;
	private static WebDriverUtil webUtil;
	private WebElement element;
	private static final Logger logger = Logger.getLogger(MouseUtil.class);
	public static MouseUtil getObject(){
		if(mouseUtil==null){
			mouseUtil=new MouseUtil();
		}
		return mouseUtil;
	}

	private MouseUtil(){

	}

	public  void mouseOver(String locatorName) {

		try{
			this.element = webUtil.getElement(locatorName);
			Actions action = new Actions(webUtil.getDriver()).moveToElement(element);
			action.build().perform(); 
			logger.info("Mouseover performed on "+locatorName+"Successfully");

		}catch(StaleElementReferenceException e){
			logger.info(e);		
			this.element = webUtil.getElement(locatorName);
			Actions action = new Actions(webUtil.getDriver()).moveToElement(element);
			action.build().perform();
		}

	}

	public  void click(String locatorName) {
		try{
			this.element = webUtil.getElement(locatorName);
			Actions action = new Actions(webUtil.getDriver()).click(element);
			action.build().perform(); 
			logger.info("Mouse Click performed on "+locatorName+"Successfully");

		}catch(StaleElementReferenceException e){
			logger.info(e);		
			this.element = webUtil.getElement(locatorName);
			Actions action = new Actions(webUtil.getDriver()).click(element);
			action.build().perform();
		}
	}


	public  void doubleClick(String locatorName) {
		try{
			this.element = webUtil.getElement(locatorName);
			Actions action = new Actions(webUtil.getDriver()).doubleClick(element);
			action.build().perform(); 
			logger.info("Mouseover performed on "+locatorName+"Successfully");

		}catch(StaleElementReferenceException e){
			logger.info(e);		
			this.element = webUtil.getElement(locatorName);
			Actions action = new Actions(webUtil.getDriver()).doubleClick(element);
			action.build().perform();
		}
	}

	public  void dragAndDrop(String dragLocatorName, String dropLocatorName) {
		WebElement dragElement = webUtil.getElement(dragLocatorName);
		WebElement dropElement = webUtil.getElement(dropLocatorName);
		try{
			Actions action = new Actions(webUtil.getDriver()).dragAndDrop(dragElement, dropElement);
			action.build().perform(); 
			logger.info(dragLocatorName+" Dragged and drop on "+dropLocatorName+"Successfully");

		}catch(StaleElementReferenceException e){
			logger.info(e);	 
			dragElement = webUtil.getElement(dragLocatorName);
			dropElement = webUtil.getElement(dropLocatorName);
			Actions action = new Actions(webUtil.getDriver()).dragAndDrop(dragElement, dropElement);
			action.build().perform(); 

		}
	}

}
