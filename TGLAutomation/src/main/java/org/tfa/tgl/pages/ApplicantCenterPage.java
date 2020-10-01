package org.tfa.tgl.pages;

import org.tfa.tgl.utilities.web.TGLWebUtil;

public class ApplicantCenterPage {

	private TGLWebUtil webUtil=TGLWebUtil.getObject();
	

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
}
