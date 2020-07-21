package org.tfa.tgl.pages;

import org.tfa.tgl.utilities.web.TGLWebUtil;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;


public class IMPSPage {
	
	private TGLWebUtil webUtil=TGLWebUtil.getObject();
	private WebElement element;
	

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
		webUtil.holdOn(2);
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
			webUtil.holdOn(5);
		}else{
			webUtil.switchToWindowFromFrame();
			webUtil.switchToFrameByFrameLocator("CommonTopframeWithName_Frm");
			webUtil.click("IMPS_HistoricalSearch_Btn");
			webUtil.switchToWindowFromFrame();
			webUtil.switchToFrameByFrameLocator("CommonMainframeWithName_Frm");
			enterPersonID(personID);
			clickOnSearchButton();
			webUtil.click("IMPS_Applicant_LK");
			webUtil.holdOn(10);
		}
		return iflag;
	}
	public void clickOnAssignment(){
		webUtil.click("IMPS_Assignment_Btn");
	}
	public String getEmailFromIMPSApplicantID(String personID){
		String getValue = null;
		boolean iflag = webUtil.objectIsVisible("IMPS_Applicant_LK");
		if(iflag){
			getValue = webUtil.getText("IMPS_Email_TB");
		}else{
			webUtil.switchToWindowFromFrame();
			webUtil.switchToFrameByFrameLocator("CommonTopframeWithName_Frm");
			webUtil.click("IMPS_HistoricalSearch_Btn");
			webUtil.switchToWindowFromFrame();
			webUtil.switchToFrameByFrameLocator("CommonMainframeWithName_Frm");
			enterPersonID(personID);
			clickOnSearchButton();
			getValue = webUtil.getText("IMPS_HistoricalSearchEmail_TB");
		}
		return getValue;
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
	/*
	 * This function will Assign the new Qualified Position
	 */
	public String assignNewQualifiedPosition(){
		String getQualifiedPosition = webUtil.getText("IMPS_QualifiedFirstPosition_LK");
	String getAssignStatus = webUtil.getText("IMPS_QualifiedFirstPositionAssign_LK");
	if(getAssignStatus.equals("Unassign")){
		if(getQualifiedPosition.contains("Bay Area")){
			webUtil.click("IMPS_NewYorkAssign_LK");
			webUtil.holdOn(2);
		}else if(getQualifiedPosition.contains("New York")){
			webUtil.click("IMPS_BayAreaAssign_LK");
			webUtil.holdOn(2);
		}else if(getQualifiedPosition.contains("D.C. Region")){
			webUtil.click("IMPS_BayAreaAssign_LK");
			webUtil.holdOn(2);
		}else{
		webUtil.click("IMPS_DCRegionAssign_LK");
		webUtil.holdOn(2);
		}
	}
	String selectedQualifiedPositon = webUtil.getText("IMPS_QualifiedFirstPosition_LK");
	webUtil.switchToWindowFromFrame();
	return selectedQualifiedPositon;
}
}
