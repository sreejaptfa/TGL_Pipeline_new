package org.tfa.tgl.pages.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.tfa.tgl.utilities.web.TGLWebUtil;

@SuppressWarnings({"squid:S134","squid:S1141"})
public class ApplicantCenterPage {

	private TGLWebUtil webUtil=TGLWebUtil.getObject();
	Logger log = Logger.getLogger("rootLogger");

	/**
	 * This function will login to the Applicant Center application
	 */
	public void validLogin(String userName, String password){
		webUtil.click("AppCenter_Login_LK");
		webUtil.holdOn(3);
		webUtil.setTextBoxValue("Login_UserName_ED",userName);
		webUtil.setTextBoxValue("Login_Password_ED", password);
		webUtil.click("Login_SignIn");	
		webUtil.holdOn(5);
	}
	/**
	 * This function will click on TransitionalFunding link on applicant Center
	 */
	public void clickOnTransitionalFundingLink(){
		webUtil.click("AppCenter_TransitionalFunding_Lk");
	}
	/**
	 * This function will Logout from Applicant Center
	 */
	public void clickOnLogOutLink(){
		webUtil.click("AppCenter_LogOut_LK");
		webUtil.holdOn(5);
	}
	
	public boolean clickOnGoToAccountHomeLink(){
		boolean iflag = webUtil.objectIsVisible("AppCenter_GoToAccountHome_Lk");
		if (iflag){
			webUtil.click("AppCenter_GoToAccountHome_Lk");
		}
		return iflag;
	}
	public void openLoginPage() {
		webUtil.getDriver().navigate().to("https://qamerlin.teachforamerica.org/applicant-center");
	}

	public void enterLoginInfo() {
		webUtil.setTextBoxValueTestData("Login_UserName_ED", "Login_UserNameOnline");
		webUtil.setTextBoxValueTestData("Login_Password_ED", "Login_PasswordOnline");
		webUtil.click("Login_SignIn");
		webUtil.holdOn(4);
	}
	
	/* 
	* This function will get the values from the Applicant center document table 
	*/
	public Map<String, String> getValuesFromApplicantCenter(String locatorName,String valueToCompare){
		String getNotesText = null;
		String documentValidCheck = null;
		Map<String, String> objectMap = null;

		Map<String, String> locatorValueMap=webUtil.getLocatorValueMap(locatorName);
		String locatorValue=TGLWebUtil.getLocatorValue(locatorValueMap, locatorName);
		List<WebElement> getValues = webUtil.getDriver().findElements(By.xpath(locatorValue));
		int len= getValues.size();
		for(int i = 1; i<=len; i++){
			try {
				boolean flag =  webUtil.getDriver().findElement(By.xpath(locatorValue +"["+i+"]/td")).isEnabled();
				if (flag) {
					WebElement sectionName = webUtil.getDriver().findElement(By.xpath(locatorValue +"["+i+"]/td"));
					String getText = sectionName.getText();
					if(getText.contains(valueToCompare)){
						WebElement getNotes = webUtil.getDriver().findElement(By.xpath(locatorValue +"["+i+"]/td[3]"));
						getNotesText = getNotes.getText();
						WebElement getDocumentChecked = webUtil.getDriver().findElement(By.xpath(locatorValue +"["+i+"]/td[2]/input"));
						documentValidCheck= getDocumentChecked.getAttribute("checked");
						WebElement getDocument = webUtil.getDriver().findElement(By.xpath(locatorValue +"["+i+"]/td[4]"));
						String getDocText = getDocument.getText();
						objectMap = new HashMap<>();
						objectMap.put("TransitionalFundingNotes",getNotesText);
						objectMap.put("TransitionalFundingCheckBox",documentValidCheck);
						objectMap.put("TransitionalFundingDocument",getDocText);
						break;
					}
				}
			}catch(WebDriverException e) {
				log.info(e);
				log.info("Unable to get the values from Applicant center");
				objectMap = null;
			}
		}
		return objectMap;

	}
}
