package org.tfa.tgl.pages.searchdetailsection;

import org.tfa.framework.core.WebDriverUtil;

public class AssetsAndLiabilitiesSection {
	
	private WebDriverUtil webUtil=WebDriverUtil.getObject();
	
	//this method is to get the text comments
	public String getOrignalValue(String locatorName) {
		return webUtil.getElement(locatorName).getText();
	}
	//this method is to enter the text
	public void enterAdjustedAmount(String locatorName, String enterAmount) {
		webUtil.setTextBoxValue(locatorName, enterAmount);
	}
	
	

}
