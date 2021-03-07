package org.tfa.tgl.pages.searchdetailsection;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriverException;
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
				if(flag) {
					flag = true;
					soft.assertTrue(true);
				}else {
					soft.assertTrue(false, textContains[i]+" windows NOT displayed");
				}
				webUtil.click(element2);
				webUtil.holdOn(2);
			}
		} catch (WebDriverException e) {
			log.info("Object not found",e);
			flag = false;
			soft.assertTrue(flag, "Object not found");
			soft.fail();
		}
		finally {
			soft.assertAll();
		}
		return flag;
	}

}

