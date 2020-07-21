package org.tfa.tgl.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.tfa.framework.core.WebDriverUtil;
import org.tfa.framework.utilities.testdata.TestData;

public class AssetsAndLiabilitiesSection {
	
	private WebDriverUtil webUtil;
	private TestData data;
	private boolean flag;
	Logger log=Logger.getLogger("rootLogger");;
	WebDriverWait explicitwait;
	
    public AssetsAndLiabilitiesSection()
    {
    	
    	webUtil=WebDriverUtil.getObject();
    	data=TestData.getObject();
    	flag=false;
    	log=Logger.getLogger("rootLogger");
    	webUtil.openURL((String) data.getEnvironmentDataMap().get("ApplicationURL"));
    	explicitwait= new WebDriverWait(webUtil.getDriver(), 20);
    	    	
    } 
    
    public boolean verifyAssetAndLiabilitySection(){
    	boolean flag=false;
    	
    	// TestCase - Assets & Liabilities Section - Step 2
    	String pid=SearchPageTGL.getpersonid();
    	By assetandliabilitylocator=By.xpath("(//div[@class='verification-views'])//div[3]/h2");
    	
    	// This code will not be applicable when entire test suite will be run, this is temporary Person Id Search in case test is run directly
    	if(pid==null)
    			pid="4228467";
    	
    	webUtil.click("Tgl_moreSearchOptionsLink");
    	//fetch person id saved in last testcase
    	webUtil.setTextBoxValue("Tgl_personid",pid );
    	// Clear app year value - change made to fix script <<NS 21 July 2020>>
    	webUtil.selectByIndex("Tgl_appyear_dd", 0);
    	webUtil.holdOn(1);
		webUtil.click("Home_Tgl_Search2_btn");
		
		// TestCase - Assets & Liabilities Section - Step 3
    	webUtil.getDriver().findElement(By.xpath("//tbody[@data-hook='results']/tr[1]/td[1]/a")).click();
    	
    	explicitwait.until(ExpectedConditions.visibilityOfElementLocated(assetandliabilitylocator));
    	
    	// TestCase - Assets & Liabilities Section - Step 4
    	if(webUtil.getElement("Tgl_AssetsAndLiabilitiesLabel_lbl").getText().contains("Assets and Liabilities")){
    		flag=true;		
    	}
    	else{
    		log.info("Asset and Libility section not visible");return flag=false;}
    	
    	// TestCase - Assets & Liabilities Section - Step 5,6
    	if(webUtil.getElement("Tgl_OwnsLaptop_lbl").getText().contains("Owns Laptop:")){
    		flag=true;		
    	}
    	else{
    		log.info("Owns laptop section not visible");return flag=false;}
    	
    	if(webUtil.getElement("Tgl_OwnsLaptop_No_lbl").getText().contains("No")){
    		flag=true;		
    	}
    	else{
    		 return flag=false;}
    	    	
    	if(webUtil.getElement("Tgl_OwnsLaptop_Yes_lbl").getText().contains("Yes")){
    		flag=true;		
    	}
    	else{
    		return flag=false;}
    	
    	if(webUtil.getElement("Tgl_OwnsLaptop_No_radio").isEnabled()  && webUtil.getElement("Tgl_OwnsLaptop_Yes_radio").isEnabled()){
    		
    		flag=true;
    	}
    	else
    		return flag=false;
    	
    	// TestCase - Assets & Liabilities Section - Step 7
    	if(webUtil.getElement("Tgl_SavingsCheckingsStock_lbl").getText().contains("Savings/Checking/Stocks Amount")){
    		flag=true;
    		
    	}
    	else
    		return flag=false;
    	
    	if(webUtil.getElement("Tgl_SavingsRequired_Chkbx").isEnabled())
    		flag=true;
    	else
    		return flag=false;
    	
    	if(webUtil.getElement("Tgl_SavingsValid_Chkbx").isEnabled())
    		flag=true;
    	else
    		return flag=false;
    	
    	if(webUtil.getElement("Tgl_SavingsOriginal_lbl").isDisplayed())
    		flag=true;
    	else
    		return flag=false;
    	
    	if(webUtil.getElement("Tgl_SavingsAdjusted_text").isEnabled())
    		flag=true;
    	else
    		return flag=false;
    	
    	if(webUtil.getElement("Tgl_SavingsApplicantNotes_txt").isEnabled())
    		flag=true;
    	else
    		return flag=false;
    	
    	
    	log.info("Assets & Libilities: Savings section verified");
    	//System.out.println("Assets & Liabilities: Savings section verified");
    	
    	// TestCase - Assets & Liabilities Section - Step 8
    	if(webUtil.getElement("Tgl_CreditStock_lbl").getText().contains("Credit Card Debt Amount")){
    		flag=true;
    		
    	}
    	else
    		return flag=false;
    	
    	if(webUtil.getElement("Tgl_CreditRequired_Chkbx").isEnabled())
    		flag=true;
    	else
    		return flag=false;
    	
    	if(webUtil.getElement("Tgl_CreaditValid_Chkbx").isEnabled())
    		flag=true;
    	else
    		return flag=false;
    	
    	if(webUtil.getElement("Tgl_CreaditOriginal_lbl").isDisplayed())
    		flag=true;
    	else
    		return flag=false;
    	
    	if(webUtil.getElement("Tgl_CreaditAdjusted_text").isEnabled())
    		flag=true;
    	else
    		return flag=false;
    	
    	if(webUtil.getElement("Tgl_CreaditApplicantNotes_txt").isEnabled())
    		flag=true;
    	else
    		return flag=false;
    	
    	//verify "original" values dont change for Savings and Credit card when Adjusted values are changed
    	
    	// TestCase - Assets & Liabilities Section - Step 9
    	String originalvalue=webUtil.getElement("Tgl_SavingsOriginal_lbl").getText();
    	webUtil.setTextBoxValue("Tgl_SavingsAdjusted_text", "1000");
    	if(originalvalue.contains(webUtil.getElement("Tgl_SavingsOriginal_lbl").getText())){
    		flag=true;
    		
    	}
    	else{
    		log.info("changing adjusted value for Savings caused error");return flag=false;}
    	
    	// TestCase - Assets & Liabilities Section - Step 9
    	originalvalue=webUtil.getElement("Tgl_CreaditOriginal_lbl").getText();
    	webUtil.setTextBoxValue("Tgl_CreaditAdjusted_text", "1000");
    	if(originalvalue.contains(webUtil.getElement("Tgl_CreaditOriginal_lbl").getText())){
    		flag=true;
    		
    	}
    	else{
    		log.info("changing adjusted for credit cards value caused error");return flag=false;}
    	
    	return flag;
    }
	
	

}
