package org.tfa.tgl.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.tfa.framework.core.WebDriverUtil;
import org.tfa.framework.utilities.testdata.TestData;

@SuppressWarnings({"squid:S1854","squid:MethodCyclomaticComplexity", "squid:ClassCyclomaticComplexity"})
public class AssetsAndLiabilitiesSection {
	
	private WebDriverUtil webUtil=WebDriverUtil.getObject();
	private TestData data=TestData.getObject();
	Logger log=Logger.getLogger("rootLogger");
	private WebDriverWait explicitwait;
	private static final String TGLCREADITORIGINALLBL="Tgl_CreaditOriginal_lbl";
	private static final String TGLSAVINGSORIGINALLBL="Tgl_SavingsOriginal_lbl";
	
    public AssetsAndLiabilitiesSection()
    {
    	webUtil.openURL(data.getEnvironmentDataMap().get("ApplicationURL"));
    	explicitwait = new WebDriverWait(webUtil.getDriver(), 20);
    	    	
    } 
    
    public boolean verifyAssetAndLiabilitySection(){
    	boolean flag = false;
    	
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
    		log.info("Asset and Libility section not visible");
    		flag=false;}
    	
    	// TestCase - Assets & Liabilities Section - Step 5,6
    	if(webUtil.getElement("Tgl_OwnsLaptop_lbl").getText().contains("Owns Laptop:")){
    		flag=true;		
    	}
    	else{
    		log.info("Owns laptop section not visible"); flag=false;}
    	
    	if(webUtil.getElement("Tgl_OwnsLaptop_No_lbl").getText().contains("No")){
    		flag=true;		
    	}
    	else{
    		 flag=false;}
    	    	
    	if(webUtil.getElement("Tgl_OwnsLaptop_Yes_lbl").getText().contains("Yes")){
    		flag=true;		
    	}
    	else{
    		flag=false;}
    	
    	if(webUtil.getElement("Tgl_OwnsLaptop_No_radio").isEnabled()  && webUtil.getElement("Tgl_OwnsLaptop_Yes_radio").isEnabled()){
    		
    		flag=true;
    	}
    	else
    		flag=false;
    	
    	// TestCase - Assets & Liabilities Section - Step 7
    	if(webUtil.getElement("Tgl_SavingsCheckingsStock_lbl").getText().contains("Savings/Checking/Stocks Amount")){
    		flag=true;
    		
    	}
    	else
    		flag=false;
    	
    	if(webUtil.getElement("Tgl_SavingsRequired_Chkbx").isEnabled())
    		flag=true;
    	else
    		flag=false;
    	
    	if(webUtil.getElement("Tgl_SavingsValid_Chkbx").isEnabled())
    		flag=true;
    	else
    		flag=false;
    	
    	if(webUtil.getElement(TGLSAVINGSORIGINALLBL).isDisplayed())
    		flag=true;
    	else
    		flag=false;
    	
    	if(webUtil.getElement("Tgl_SavingsAdjusted_text").isEnabled()) {
    		flag=true;
    	}else {
    		flag=false;}
    	
    	if(webUtil.getElement("Tgl_SavingsApplicantNotes_txt").isEnabled())
    		flag=true;
    	else
    		flag=false;
      	if(webUtil.getElement("Tgl_CreditStock_lbl").getText().contains("Credit Card Debt Amount")){
    		flag=true;
     	}
    	else
    		flag=false;
    	
    	if(webUtil.getElement("Tgl_CreditRequired_Chkbx").isEnabled())
    		flag=true;
    	else
    		flag=false;
    	
    	if(webUtil.getElement("Tgl_CreaditValid_Chkbx").isEnabled())
    		flag=true;
    	else
    		flag=false;
    	
    	if(webUtil.getElement(TGLCREADITORIGINALLBL).isDisplayed())
    		flag=true;
    	else
    		flag=false;
    	
    	if(webUtil.getElement("Tgl_CreaditAdjusted_text").isEnabled())
    		flag=true;
    	else
    		flag=false;
    	
    	if(webUtil.getElement("Tgl_CreaditApplicantNotes_txt").isEnabled())
    		flag=true;
    	else
    		flag=false;
    	
    	//verify "original" values dont change for Savings and Credit card when Adjusted values are changed
    	
    	// TestCase - Assets & Liabilities Section - Step 9
    	String originalvalue=webUtil.getElement(TGLSAVINGSORIGINALLBL).getText();
    	webUtil.setTextBoxValue("Tgl_SavingsAdjusted_text", "1000");
    	if(originalvalue.contains(webUtil.getElement(TGLSAVINGSORIGINALLBL).getText())){
    		flag=true;
    		
    	}
    	else{
    		log.info("changing adjusted value for Savings caused error"); flag=false;}
    	
    	// TestCase - Assets & Liabilities Section - Step 9
    	originalvalue=webUtil.getElement(TGLCREADITORIGINALLBL).getText();
    	webUtil.setTextBoxValue("Tgl_CreaditAdjusted_text", "1000");
    	if(originalvalue.contains(webUtil.getElement(TGLCREADITORIGINALLBL).getText())){
    		flag=true;
    	}
    	else{
    		log.info("changing adjusted for credit cards value caused error"); flag=false;}
    	
    	return flag;
    }
	
	

}
