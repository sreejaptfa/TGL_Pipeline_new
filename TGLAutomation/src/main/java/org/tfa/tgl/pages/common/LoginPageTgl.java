package org.tfa.tgl.pages.common;

import org.apache.log4j.Logger;
import org.tfa.framework.core.WebDriverUtil;

@SuppressWarnings("squid:S2068")
public class LoginPageTgl {

	private WebDriverUtil webUtil= WebDriverUtil.getObject();
	Logger log = Logger.getLogger("rootLogger");
	
	private static final String LOGINUSERNAME ="LoginTgl_username_ED";
	protected static final String LOGINUSERPASSWORD="LoginTgl_password_ED";
	private static final String LOGINSIGNBTN="LoginTgl_Signin_btn";

		
	public boolean enterLoginInfo() {
		boolean flag = false;
		webUtil.waitUntilElementVisible(LOGINUSERNAME, 30);
		webUtil.holdOn(5);
		webUtil.setTextBoxValueTestData(LOGINUSERNAME, "Login_UserName");
		webUtil.setTextBoxValueTestData(LOGINUSERPASSWORD, "Login_Password");
		webUtil.click(LOGINSIGNBTN);
		webUtil.waitForBrowserToLoadCompletely();
		webUtil.holdOn(10);
		if (webUtil.getDriver().getCurrentUrl().contains("tgl")) {
			flag = true;
			log.info("Home Page landed");
		} else {
			flag = false;
			log.info("Home Page not found!");
		}
		return flag;
	}
	
	//this method is to verify Invalid Login
	public boolean verifyInvalidLogin(String userName, String userPassword) {
		boolean flag = false;
		webUtil.waitUntilElementVisible(LOGINUSERNAME, 10);
		webUtil.holdOn(2);
		webUtil.setTextBoxValue(LOGINUSERNAME, userName);
		webUtil.setTextBoxValue(LOGINUSERPASSWORD, userPassword);
		webUtil.click(LOGINSIGNBTN);
		webUtil.waitUntilElementVisible("Tgl_invalidloginalert_lbl", 10);
		if (webUtil.getElement("Tgl_invalidloginalert_lbl").getText().contains("Invalid username or password.")) {
			flag =true;
		}else {
			flag =false;
		}
		return flag;
	}
}