package org.tfa.tgl.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.tfa.framework.core.BaseTestMethods;
import org.tfa.tgl.pages.LoginPageTgl;
import org.tfa.tgl.pages.TaxInformationSection;
import org.tfa.tgl.utilities.web.TGLConstants;

public class TaxInformationSectionTest extends BaseTestMethods {

	@Test
	public void tgl104ValidateTaxlInformationSection() {
		boolean result;
		TaxInformationSection taxinfosection = new TaxInformationSection();

		LoginPageTgl loginpage = new LoginPageTgl();

		// Below Assert ensures success login
		result = loginpage.enterLoginInfo();
		Assert.assertTrue(result, "EnterloginInfo method failed");

		// Below Assert ensures that searched user has additional information section
		Assert.assertTrue(taxinfosection.verifyTaxInformationSection(), "verifyTaxInformation method failed");
		Assert.assertTrue(taxinfosection.verifyHelpLinks(), "VerifyHelpLinks method failed");

	}

	@Override
	public TGLConstants getConstants() {
		return new TGLConstants();
	}

}
