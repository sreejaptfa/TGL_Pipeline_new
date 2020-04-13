package org.tfa.tgl.pages;

import org.tfa.tgl.utilities.web.TGLWebUtil;

import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;


public class IMPSPage {
	
	private TGLWebUtil webUtil=TGLWebUtil.getObject();
	private static final Logger logger = Logger.getLogger(TGLWebUtil.class);
	private WebElement element;
	
	/**
	 * This function will open the login page of IMPS.
	 */
	public void openLoginPage(String url){
		webUtil.getDriver().manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		logger.debug("url -  "+url);
		if(url==null){
			logger.debug("data object is null");
		}else{
			logger.debug("data object is not null");
		}
		webUtil.getDriver().get(url);
		webUtil.holdOn(3);
		webUtil.waitForBrowserToLoadCompletely();
	}
	
	/**
	 * This function will login to the IMPS application
	 */
	public void validLogin(String userName, String password){
        webUtil.setTextBoxValue("IMPS_UserName_ED", userName);
        webUtil.setTextBoxValue("IMPS_Password_ED", password);
        webUtil.click("IMP_Submit_Btn");  
	}
	
	/**
	 * This function will Switch to the frame and Clicks on the Admission button
	 */
	public void clickOnAdmissionsButton(){
		webUtil.switchToFrameByFrameLocator("CommonTopframeWithName_Frm");
		webUtil.click("IMPS_Admissions_Btn");
		webUtil.switchToWindowFromFrame();
	}
	
	/**
	 * This function will enters the personID
	 */
	public void enterPersonID(String personID){
		webUtil.setTextBoxValue("IMPS_PersonID_ED", personID);
	}
	/*
	 * This function will clicks on Search Button
	 */
	public void clickOnSearchButton(){
		webUtil.click("IMPS_Search_Btn");
	}
	
	/**
	 * This function will clicks on the Person Applicant on the Admission Page. 
	 * If the Applicant  not found it will search on Historical Search
	 */
	public boolean clickOnApplicantLink(String personID){
		boolean iflag = webUtil.objectIsVisible("IMPS_Applicant_LK");
		if(iflag){
			webUtil.click("IMPS_Applicant_LK");
		}else{
			webUtil.switchToWindowFromFrame();
			webUtil.switchToFrameByFrameLocator("CommonTopframeWithName_Frm");
			webUtil.click("IMPS_HistoricalSearch_Btn");
			webUtil.switchToWindowFromFrame();
			webUtil.switchToFrameByFrameLocator("CommonMainframeWithName_Frm");
			enterPersonID(personID);
			clickOnSearchButton();
			webUtil.click("IMPS_Applicant_LK");
		}
		return iflag;
	}
	public void clickOnAssignment(){
		webUtil.click("IMPS_Assignment_Btn");
	}
	/*
	 * This function will Logout from IMPS
	 */
	public void clickOnIMPSLogoutButton(){
		webUtil.switchToFrameByFrameLocator("CommonTopframeWithName_Frm");
		this.element = webUtil.getElement("IMPS_Logout_Btn");
		webUtil.click(element);
		webUtil.holdOn(5);
		element.sendKeys(Keys.ESCAPE);
		webUtil.switchToWindowFromFrame();
	}
}
