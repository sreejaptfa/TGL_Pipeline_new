package org.tfa.tgl.pages.searchdetails;

import org.tfa.framework.core.WebDriverUtil;

public class AwardCalculatorPage {
	
	private WebDriverUtil webUtil=WebDriverUtil.getObject();
	
	public void clickOnCalculateBtn(){
		webUtil.click("Tgl_Calculate_Btn");
		webUtil.holdOn(5);
	}
	
	public void clickOnTGLCalculationCancelBtn(){
		webUtil.click("Tgl_TGLCalculation_Cancel_Btn");
		webUtil.holdOn(5);
	}
	
	public void clickOnTGLCalculationCalculateBtn(){
		webUtil.click("Tgl_TGLCalculation_Calculate_Btn");
		webUtil.holdOn(5);
	}
}
