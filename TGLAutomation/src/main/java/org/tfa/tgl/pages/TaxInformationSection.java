package org.tfa.tgl.pages;

import java.util.ArrayList;
import java.util.Random;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.tfa.framework.core.WebDriverUtil;
import org.tfa.framework.utilities.testdata.TestData;

public class TaxInformationSection extends PFactory{
	
	private WebDriverUtil webUtil=WebDriverUtil.getObject();
	private TestData data=TestData.getObject();
	private boolean flag;
	Logger log=Logger.getLogger("rootLogger");
	ArrayList <String> names=new ArrayList <String>() ;
	WebDriverWait localwait;
	private Random r;
	protected static String randompersonid;
	protected WebDriverWait explicitwait;
	
	public TaxInformationSection(){
		webUtil=WebDriverUtil.getObject();
    	data=TestData.getObject();
    	flag=false;
    	log=Logger.getLogger("rootLogger");
    	webUtil.openURL((String) data.getEnvironmentDataMap().get("ApplicationURL"));
    	explicitwait= new WebDriverWait(webUtil.getDriver(), 20);
		
		
	}
	
	public boolean verifyTaxInformationSection(){
		boolean flag=false;
		
		String pid=SearchPageTGL.getpersonid();
    	By assetandliabilitylocator=By.xpath("(//div[@class='verification-views'])//div[3]/h2");
    	
    	// This code will not be applicable when entire test suite will be run, this is temporary Person Id Search in case test is run directly
    	if(pid==null)
    			pid="4228467";
		
    	webUtil.click("Tgl_moreSearchOptionsLink");  	
    	webUtil.setTextBoxValue("Tgl_personid", pid);
		webUtil.click("Home_Tgl_Search2_btn");	
		WebElement firstrow=webUtil.getDriver().findElement(firstrowlocator);				
		firstrow.click();	
		explicitwait.until(ExpectedConditions.visibilityOfElementLocated(assetandliabilitylocator));
		
		if(webUtil.getElement("Tgl_TaxInformation_lbl").getText().contains("Tax Information"))
			flag=true;
		else
			return flag=false;
		
		if(webUtil.getElement("Tgl_TaxInfoCanClaim_lbl").getText().contains("Can Claim Dependent:"))
			flag=true;
		else
			return flag=false;
		
		if(webUtil.getElement("Tgl_TaxInfoDependentIncome_lbl").getText().contains("Income:"))
			flag=true;
		else
			return flag=false;
		
		if(webUtil.getElement("Tgl_TaxInfohowmany_lbl").getText().contains("How many dependents:"))
			flag=true;
		else
			return flag=false;
		
		if(webUtil.getElement("Tgl_TaxInfoNoOfDependents_lbl").getText().contains("Total # of Dependents:"))
			flag=true;
		else
			return flag=false;
		
		if(webUtil.getElement("Tgl_Taxinfoincome_txt").isEnabled()){
			flag=true;
		}
		else
			return flag=false;
		
		if(webUtil.getElement("Tgl_TaxInfonoofdependents_txt").isEnabled())
			flag=true;
		else
			flag=false;
		
		
		// Verify TaxInformation Documents - Applicant's tax return
		if(webUtil.getElement("Tgl_TaxInforAppTaxReturn_lbl").getText().contains("Applicant's Tax Return"))
			flag=true;
		else
			return flag=false;
		
		if(webUtil.getElement("Tgl_TaxInforAppTaxReturnedRequired_chk").isEnabled()){
			flag=true;
		}
		else
			return flag=false;
		
		if(webUtil.getElement("Tgl_TaxInfoAppTaxReturnrValid_chk").isEnabled()){
			flag=true;
		}
		else
			return flag=false;
		

		if(webUtil.getElement("Tgl_TaxInfoAppTaxReturnAppNotes_txt").isEnabled()){
			flag=true;
		}
		else
			return flag=false;
		
		// Add test comments in notes section
		//webUtil.getElement("Tgl_TaxInfoAppTaxReturnAppNotes_txt").sendKeys("Test Comments");
		
		// Verify Tax Information Document section - W2 or Income Statement
		if(webUtil.getElement("Tgl_TaxInfoIncome_lbl").getText().contains("W-2 or Income Statement"))
			flag=true;
		else
			return flag=false;
		
		if(webUtil.getElement("Tgl_TaxInfoIncomeRequired_chk").isEnabled()){
			flag=true;
		}
		else
			return flag=false;
		
		if(webUtil.getElement("Tgl_TaxInfoIncomeValid_chk").isEnabled()){
			flag=true;
		}
		else
			return flag=false;
		
		if(webUtil.getElement("Tgl_TaxInfoIncomeAppNotes_txt").isEnabled()){
			flag=true;
		}
		else
			return flag=false;
		
		// Add test comments in notes section
		//webUtil.getElement("Tgl_TaxInfoIncomeAppNotes_txt").sendKeys("Test Comments");
		
		// Verify Tax Information Document section - Parent's Tax Return
		if(webUtil.getElement("Tgl_TaxInfoParentsTaxReturn_lbl").getText().contains("Parent's Tax Return"))
			flag=true;
		else
			return flag=false;
		
		if(webUtil.getElement("Tgl_TaxInfoPTRRequired_chk").isEnabled()){
			flag=true;
		}
		else
			return flag=false;
		
		if(webUtil.getElement("Tgl_TaxInfoPTRValid_chk").isEnabled()){
			flag=true;
		}
		else
			return flag=false;
		
		if(webUtil.getElement("Tgl_TaxInfoPTRAppNotes_txt").isEnabled()){
			flag=true;
		}
		else
			return flag=false;
		
		// Add test comments in notes section
		//webUtil.getElement("Tgl_TaxInfoPTRAppNotes_txt").sendKeys("Test Comments");
		
		// Verify Tax Information Document section - Parent's Tax Return
		if(webUtil.getElement("Tgl_TaxInfoPIS_lbl").getText().contains("Parent Income Statement"))
			flag=true;
		else
			return flag=false;
		
		if(webUtil.getElement("Tgl_TaxInfoPISRequired_chk").isEnabled()){
			flag=true;
		}
		else
			return flag=false;
		
		if(webUtil.getElement("Tgl_TaxInfoPISValid_chk").isEnabled()){
			flag=true;
		}
		else
			return flag=false;
		
		if(webUtil.getElement("Tgl_TaxInfoPISAppNotes_txt").isEnabled()){
			flag=true;
		}
		else
			return flag=false;
		
		// Add test comments in notes section
		//webUtil.getElement("Tgl_TaxInfoPISAppNotes_txt").sendKeys("Test Comments");
		
		webUtil.getDriver().navigate().refresh();
		webUtil.waitForBrowserToLoadCompletely();
		
		
		/*if((webUtil.getElement("Tgl_TaxInfoPISAppNotes_txt").getText().contains("Test Comments"))
			&& (webUtil.getElement("Tgl_TaxInfoPTRAppNotes_txt").getText().contains("Test Comments"))
			&& (webUtil.getElement("Tgl_TaxInfoIncomeAppNotes_txt").getText().contains("Test Comments"))
			&& (webUtil.getElement("Tgl_TaxInfoAppTaxReturnAppNotes_txt").getText().contains("Test Comments")))
			
			flag=true;
		else
			return flag=false;*/
		
		
		return flag;
	}
	
	

}
