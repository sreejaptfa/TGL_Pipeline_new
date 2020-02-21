package org.tfa.tgl.utilities.web;

import org.tfa.framework.core.WebDriverUtil;
import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.SystemUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.tfa.framework.utilities.testdata.TestData;

public class TGLWebUtil extends WebDriverUtil{
	private static final Logger logger = Logger.getLogger(TGLWebUtil.class);
	private TestData data=TestData.getObject();
	private static TGLWebUtil webUtil;
	
	String downloadedFilePath;
	
	
	
	/**
	 * This function will upload the file.
	 */
	public void uploadFile(String filePathKeyName, String locatorName){
		String relativeFilePath=data.getTestCaseDataMap().get(filePathKeyName);
		WebElement el=webUtil.getElement(locatorName);
		el.clear();
		el.sendKeys(new File(relativeFilePath).getAbsolutePath());
        webUtil.holdOn(5);
	}
	
	/**
	 * This function will download the file.
	 */
	public void downloadFile(String fileName){
		if(SystemUtils.IS_OS_LINUX) {
			downloadedFilePath="/home/"+System.getProperty("user.name")+"/Downloads/"+fileName;
		}else {
			downloadedFilePath="C:\\Users\\"+System.getProperty("user.name")+"\\Downloads\\"+fileName;
		}
		File file=new File(downloadedFilePath);
		if(file.exists()) {
			file.delete();
			logger.info("File downloaded Successfully");
			Assert.assertTrue(true);
			}else{
			logger.info("File not downloaded");
			Assert.assertFalse(true);
		}
			
	}

	/**
	 * This function will click on review or remove links on the particular document
	 */
	public WebElement getValuesFromDocumentsWebTable(String tableLocatorName, String refRowDataToSearch, String  expressionType){
		Map<String, String> locatorValueMap=webUtil.getLocatorValueMap(tableLocatorName);
		String locatorValue=getLocatorValue(locatorValueMap, tableLocatorName);
		List<WebElement> tableRowValues = webUtil.getDriver().findElements(By.xpath(locatorValue));
		for(int i = 1; i<=tableRowValues.size(); i++){
			String rowWiseCellData =  webUtil.getDriver().findElement(By.xpath(locatorValue+"//tbody/tr["+i+"]")).getText();
			try
				{
					if(rowWiseCellData.contains(refRowDataToSearch))
					{
						switch(expressionType) {
						case "Review":
							return webUtil.getDriver().findElement(By.xpath(locatorValue+"//tbody/tr["+i+"]//a"));
						case "Remove":
							return webUtil.getDriver().findElement(By.xpath(locatorValue+"//tbody/tr["+i+"]//button"));
					}

				}
			}
			catch(Exception e)
				{
					logger.info(e);
				}   
			}
			return null;
		}

	private static String getLocatorValue(Map<String, String> locatorValueMap, String locatorName){
		String locatorValue=null;
		try{
			locatorValue=locatorValueMap.get("LocatorValue");
			if("".equals(locatorValue.trim())){
				logger.info("LocatorValue is blank for "+locatorName);
			}
		}catch(Exception e){
			logger.info("LocatorValueMao is blank for "+locatorName);
			throw e;
		}

		return locatorValue;
	}

	/**
	 * This function is to get the object of WebdriverUtil.
	 * @return instance of WebdriverUtil class.
	 */
	public static TGLWebUtil getObject(){
		if(webUtil==null){
			webUtil=new TGLWebUtil();
		}
		return webUtil;
	}

}
