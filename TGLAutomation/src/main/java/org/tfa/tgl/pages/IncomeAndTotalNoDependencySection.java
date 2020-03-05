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
		webUtil.click("Home_Tgl_Search2_btn");	
		List <WebElement> searchresults=webUtil.getDriver().findElements(searchresultstable);
		size=searchresults.size();
		
		// TestCase [Step-2]
		for(int i=1;i<size;i++){
			rowstatus=webUtil.getDriver().findElement(By.xpath("//tbody[@data-hook='results']/tr["+String.valueOf(i)+"]/td[3]/a"));
		
			if((rowstatus.getText().contains("INCOMPLETE")) || rowstatus.getText().contains("INPROGRESS")){			
				rowstatus.click();
				break;
			}
		
		}
		//TestCase [Step-3]
		// If Income and NoOfDependent fields are empty set values for them
		webUtil.setTextBoxValue("Tgl_Taxinfoincome_txt", "2500");
		webUtil.setTextBoxValue("Tgl_TaxInfonoofdependents_txt", "2");
		
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
		
		///TestCase [Step-4]
		// Verify validations show
		webUtil.setTextBoxValue("Tgl_Taxinfoincome_txt", "");
		webUtil.setTextBoxValue("Tgl_TaxInfonoofdependents_txt", "");
		webUtil.selectByVisibleText("Tgl_TGLStatus_dd", "In Progress");
		webUtil.holdOn(4);
		
		//TestCase [Step-5]
		webUtil.selectByVisibleText("Tgl_TGLStatus_dd", "Complete");
		
		validations = webUtil.getDriver().findElements(statusvalidations);		
		if(validations.size()>0){
			flag=true;}
		else
			return flag=false;	
		if(validations.get(0).getText().contains("Status cannot be changed to complete if Income or Total # of Dependents is blank."))
			flag = true;
		else
			return flag=false;
		
		//TestCase [Step-4]
		webUtil.setTextBoxValue("Tgl_Taxinfoincome_txt", "0");
		webUtil.setTextBoxValue("Tgl_TaxInfonoofdependents_txt", "0");
		webUtil.selectByVisibleText("Tgl_TGLStatus_dd", "Complete");
		
		validations = webUtil.getDriver().findElements(statusvalidations);		
		if(validations.size()>0){
			flag=true;}
		else
			return flag=false;	
		
		return flag;
	}
	
	

}