package org.tfa.tgl.pages;

import org.tfa.framework.core.WebDriverUtil;

public class LoginPageAppCenter {

	private WebDriverUtil webUtil = WebDriverUtil.getObject();

	public void openLoginPage() {
		webUtil.getDriver().navigate().to("https://qamerlin.teachforamerica.org/applicant-center");
	}

	public void enterLoginInfo() {

		webUtil.setTextBoxValueTestData("Login_UserName_ED", "Login_UserNameOnline");
		webUtil.setTextBoxValueTestData("Login_Password_ED", "Login_PasswordOnline");
		webUtil.click("Login_SignIn");
		webUtil.holdOn(4);
	}

}