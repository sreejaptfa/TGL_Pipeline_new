package org.tfa.tgl.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;
import org.tfa.framework.utilities.general.RandomUtil;
import org.tfa.framework.utilities.testdata.TestData;
import org.tfa.tgl.utilities.web.TGLWebUtil;

public class TaxInformationSection{

	private TGLWebUtil webUtil = TGLWebUtil.getObject();
	SoftAssert soft = new SoftAssert();
	private TestData data = TestData.getObject();
	Logger log=Logger.getLogger("rootLogger");
	private RandomUtil random = new RandomUtil();
	private static final String OBJECTNOTFOUND ="Object not found";
	
	//this method is to verify the objects on Tax Information section
	public boolean verifyDocumentInformationSection(String sectionName) {
		boolean flag = false;
		String[] sectionObjects= data.getTestCaseDataMap().get(sectionName).split(":");
		int len = sectionObjects.length;
		try { 
			for (int i = 0; i < len-1; i++) {
				WebElement element = webUtil.getElement(sectionObjects[i]);
				flag = element.isEnabled();
				if(!flag) {
					soft.assertTrue(flag, sectionObjects[i]+" not exist on Section");
				}
			}
		} catch (Exception e) {
			soft.assertTrue(flag, OBJECTNOTFOUND);
			log.info(OBJECTNOTFOUND);
			flag = false;
		}
		return flag;
	}
	
	//this method is to get the text comments
	public String getTextFromElement(String locatorName) {
		return webUtil.getAttributeValue(locatorName,"value");
	}
	
	//this method is to enter text comments
	public String enterTextComment(String locatorName) {
		return webUtil.setTextBoxValue(locatorName, "Test Comments-" + random.generateRandomString(5) + random.generateRandomNumber(5));
	}
	
	
	//Verify help link on Tax Information section
	public boolean verifyHelpLinksOnTaxInformation(){
		boolean flag=false;
		String[] helpLinks= data.getTestCaseDataMap().get("HelpLinks").split(":");
		String[] modalLabels= data.getTestCaseDataMap().get("ModalLables").split(":");
		String[] textContains= data.getTestCaseDataMap().get("TextContains").split(":");
		String[] closeButtons= data.getTestCaseDataMap().get("CloseButtons").split(":");
		int len = helpLinks.length;
		try {
			for (int i = 0; i < len; i++) {
				WebElement element1 = webUtil.getElement(helpLinks[i]);
				WebElement element2 = webUtil.getElement(closeButtons[i]);
				webUtil.click(element1);
				flag = webUtil.getElement(modalLabels[i]).getText().contains(textContains[i]);
				if(!flag) {
					soft.assertTrue(flag, textContains[i]+" windows NOT displayed");
				}
				webUtil.click(element2);
				webUtil.holdOn(2);
				
				}
		} catch (Exception e) {
			soft.assertTrue(flag, OBJECTNOTFOUND);
			log.info(OBJECTNOTFOUND);
			flag = false;
		}
		return flag;
	}

}

