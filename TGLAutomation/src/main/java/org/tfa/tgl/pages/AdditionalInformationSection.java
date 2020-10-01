package org.tfa.tgl.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.tfa.framework.core.WebDriverUtil;
import org.tfa.framework.utilities.testdata.TestData;

public class AdditionalInformationSection extends PFactory{

	
	private WebDriverUtil webUtil;
	private TestData data;
	
	Logger log=Logger.getLogger("rootLogger");
	WebDriverWait explicitwait;
	
    public AdditionalInformationSection()    {
     	webUtil=WebDriverUtil.getObject();
    	data=TestData.getObject();
    	log=Logger.getLogger("rootLogger");
    	webUtil.openURL((String) data.getEnvironmentDataMap().get("ApplicationURL"));//NOSONAR
    	explicitwait= new WebDriverWait(webUtil.getDriver(), 20);
     } 
    
    public boolean verifyAdditionalInformationSection(){
    	boolean flag=false;
    	
    	// TestCase - Additional Information Section - Step 2
    	String pid=SearchPageTGL.getpersonid();
    	By assetandliabilitylocator=By.xpath("(//div[@class='verification-views'])//div[3]/h2");
    	
    	// This code will not be applicable when entire test suite will be run, this is temporary Person Id Search in case test is run directly
    	if(pid==null)
    			pid="4228467";
    	
    	webUtil.click("Tgl_moreSearchOptionsLink");  	
    	webUtil.setTextBoxValue("Tgl_personid", pid);
    	// Clear app year value - change made to fix script <<NS 21 July 2020>>
    	webUtil.selectByIndex("Tgl_appyear_dd", 0);
    	webUtil.holdOn(1);
		webUtil.click("Home_Tgl_Search2_btn");	
		WebElement firstrow=webUtil.getDriver().findElement(firstrowlocator);	
		// TestCase - Additional Information Section - Step 3
		firstrow.click();	
		explicitwait.until(ExpectedConditions.visibilityOfElementLocated(assetandliabilitylocator));
		
		// TestCase - Additional Information Section - Step 4, Step - 5
    	if((webUtil.getElement("Tgl_AdditionalInformationSection_lbl").isDisplayed())
    		&& (webUtil.getElement("Tgl_AdditionalInformationSection_lbl").getText().contains("Additional Information"))){
    	
    		flag=true;
    	}
    	else
    		return flag=false;
    	
    	if((webUtil.getElement("Tgl_LateFundingRqst_lbl").isDisplayed())
    		&& (webUtil.getElement("Tgl_LateFundingRqst_lbl").getText().contains("Late Funding Requested:"))){
    		flag=true;
    	}
    	else
    		return flag=false;
    	
    	if((webUtil.getElement("Tgl_LateFundingExplanation_lbl").isDisplayed())
    		&&(webUtil.getElement("Tgl_LateFundingExplanation_lbl").getText().contains("Late Funding Explanation:")))
    	{
    		flag=true;
    	}
    	else
    		return flag=false;
    	
    	if((webUtil.getElement("Tgl_WillYourDebt_lbl").isDisplayed())
    		&& (webUtil.getElement("Tgl_WillYourDebt_lbl").getText().contains("Will your debt be different")))
    	{
    		flag=true;
    	}
    	else
    		return flag=false;
    	
    	if((webUtil.getElement("Tgl_ChangeInFinancial_lbl").isDisplayed())
    			&&(webUtil.getElement("Tgl_ChangeInFinancial_lbl").getText().contains("Change in Financial Situation:")))
    	{
    		flag=true;
    	}
    	else
    		return flag=false;
    	
    	if((webUtil.getElement("Tgl_AdditionalComments_lbl").isDisplayed())
    			&&(webUtil.getElement("Tgl_AdditionalComments_lbl").getText().contains("Additional Comments:")))
    	{
    		flag=true;
    	}
    	else
    		return flag=false;
    	
    	
    	return flag;
    }
}
