package org.tfa.tgl.pages;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.tfa.framework.core.WebDriverUtil;
import org.tfa.framework.utilities.testdata.TestData;

public class IncomeAndTotalNoDependencySection extends PFactory {
	
	private WebDriverUtil webUtil=WebDriverUtil.getObject();
	private TestData data=TestData.getObject();
	private boolean flag;
	Logger log=Logger.getLogger("rootLogger");
	protected WebDriverWait explicitwait;
	private int size;
	
	public IncomeAndTotalNoDependencySection(){
		
	
		
	}
	
	public boolean verifyIncomeAndTotalNodependencySection(){
		boolean flag=false;
		WebElement rowstatus;
		
		webUtil.getDriver().navigate().refresh();
		webUtil.waitForBrowserToLoadCompletely();
		webUtil.selectByIndex("Tgl_appyear_dd", 0);
		webUtil.holdOn(1);
		webUtil.getElement("Tgl_TGLStatusSingle_txt").click();
		webUtil.getDriver().findElement(By.xpath("(//div[@class='selectize-dropdown-content'])[2]/div[2]")).click();
		webUtil.holdOn(1);
		webUtil.click("Home_Tgl_Search2_btn");	
		webUtil.holdOn(5);
		List <WebElement> searchresults=webUtil.getDriver().findElements(searchresultstable);
		size=searchresults.size();
		if(size==0){
			webUtil.getElement("Tgl_TGLStatusHasItems_txt").click();
			webUtil.getDriver().findElement(By.xpath("(//div[@class='selectize-dropdown-content'])[2]/div[3]")).click();
			webUtil.click("Home_Tgl_Search2_btn");	
			searchresults=webUtil.getDriver().findElements(searchresultstable);
			size=searchresults.size();
			// If Still there is no result return false
			if(size==0){
				log.info("No records found with InProgress or Incomplete Status"); return false;
			}
		}
		
		// TestCase [Step-2]
		for(int i=1;i<=size;i++){
			rowstatus=webUtil.getDriver().findElement(By.xpath("//tbody[@data-hook='results']/tr["+String.valueOf(i)+"]/td[3]/a"));
		
			if((rowstatus.getText().contains("INCOMPLETE")) || rowstatus.getText().contains("INPROGRESS")){			
				rowstatus.click();
				webUtil.holdOn(10);
				break;
			}
		
		}
		//TestCase [Step-3]
		// If Income and NoOfDependent fields are empty set values for them
		webUtil.setTextBoxValue("Tgl_Taxinfoincome_txt", "2500");
		webUtil.setTextBoxValue("Tgl_TaxInfonoofdependents_txt", "2");
		webUtil.holdOn(3);
		// Mark All documents that are required as Valid		
		if(webUtil.getElement("Tgl_EducationCost_privateloanrequired_chk").isSelected())
			while(!webUtil.getElement("Tgl_EducationCost_privateloanValid_chk").isSelected())
					webUtil.getElement("Tgl_EducationCost_privateloanValid_chk").click();
		
		if(webUtil.getElement("Tgl_EducationCost_Otherloanrequired_chk").isSelected())
			while(!webUtil.getElement("Tgl_EducationCost_OtherloanValid_chk").isSelected())
					webUtil.getElement("Tgl_EducationCost_OtherloanValid_chk").click();
		
		if(webUtil.getElement("Tgl_SavingsRequired_Chkbx").isSelected())
			while(!webUtil.getElement("Tgl_SavingsValid_Chkbx").isSelected())
					webUtil.getElement("Tgl_SavingsValid_Chkbx").click();
		
		if(webUtil.getElement("Tgl_CreditRequired_Chkbx").isSelected())
			while(!webUtil.getElement("Tgl_CreaditValid_Chkbx").isSelected())
					webUtil.getElement("Tgl_CreaditValid_Chkbx").click();
		
		if(webUtil.getElement("Tgl_ApplicanttaxRequired_chk").isSelected())
			while(!webUtil.getElement("Tgl_ApplicantstaxValid_chk").isSelected())
					webUtil.getElement("Tgl_ApplicantstaxValid_chk").click();
		
		if(webUtil.getElement("Tgl_TaxInfoIncomeRequired_chk").isSelected())
			while(!webUtil.getElement("Tgl_TaxInfoIncomeValid_chk").isSelected())
					webUtil.getElement("Tgl_TaxInfoIncomeValid_chk").click();
		
		if(webUtil.getElement("Tgl_TaxInfoPTRRequired_chk").isSelected())
			while(!webUtil.getElement("Tgl_TaxInfoPTRValid_chk").isSelected())
					webUtil.getElement("Tgl_TaxInfoPTRValid_chk").click();
		
		if(webUtil.getElement("Tgl_TaxInfoPISRequired_chk").isSelected())
			while(!webUtil.getElement("Tgl_TaxInfoPISValid_chk").isSelected())
					webUtil.getElement("Tgl_TaxInfoPISValid_chk").click();
		
		// Set TGL status as Complete
		webUtil.selectByVisibleText("Tgl_TGLStatus_dd", "Complete");
		// Verify there is no error message
		List <WebElement> validations = webUtil.getDriver().findElements(statusvalidations);
		
		if(validations.size()==0){
			flag=true;}
		else
			return flag=false;	
		
		// TestCase - IncomeAndTotalNoDependency [Step-4]
		// Verify validations show
		webUtil.selectByVisibleText("Tgl_TGLStatus_dd", "In Progress");
		webUtil.holdOn(2);
		webUtil.getElement("Tgl_Taxinfoincome_txt").clear();
		webUtil.getElement("Tgl_Taxinfoincome_txt").sendKeys(" ");
		webUtil.getElement("Tgl_TaxInfonoofdependents_txt").clear();
		webUtil.getElement("Tgl_TaxInfonoofdependents_txt").sendKeys(" ");
		
		/*if(webUtil.getElement("Tgl_Taxinfoincome_txt").getAttribute("value")!=null){
			log.info("Value is not cleared from Income textbox"); return false;
		}*/
		
		webUtil.holdOn(4);
		
		//TestCase [Step-5]
		webUtil.selectByVisibleText("Tgl_TGLStatus_dd", "Complete");
		webUtil.holdOn(2);
			
		validations = webUtil.getDriver().findElements(statusvalidations);		
		if(validations.size()>0){
			flag=true;}
		else{
			log.info("Validation should Show but it does NOT!");return flag=false;	}
		if(validations.get(0).getText().contains("Status cannot be changed to complete if Income or Total # of Dependents is blank."))
			flag = true;
		else
			return flag=false;
		
		//TestCase [Step-4]
		webUtil.setTextBoxValue("Tgl_Taxinfoincome_txt", "0");
		webUtil.setTextBoxValue("Tgl_TaxInfonoofdependents_txt", "0");
		webUtil.holdOn(4);
		webUtil.selectByVisibleText("Tgl_TGLStatus_dd", "Complete");
		webUtil.holdOn(2);
		validations = webUtil.getDriver().findElements(statusvalidations);		
		if(validations.size()==0){
			flag=true;}
		else{
			log.info("Validation should not show but it does!");return flag=false;	}
		
		return flag;
	}
	
	


}



