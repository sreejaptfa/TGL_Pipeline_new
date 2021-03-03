package org.tfa.tgl.tests;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.tfa.framework.core.BaseTestMethods;
import org.tfa.tgl.pages.common.LoginPageTgl;
import org.tfa.tgl.pages.search.SearchPage;
import org.tfa.tgl.utilities.web.TGLConstants;
import org.tfa.tgl.utilities.web.TGLWebUtil;

public class ValidateAdditionalInformationSection extends BaseTestMethods  {

	Logger log=Logger.getLogger("rootLogger");
	SoftAssert soft = new SoftAssert();
	private TGLWebUtil webUtil = TGLWebUtil.getObject();
	private SearchPage searchPage = new SearchPage();
	
	/**
	 **************************************************************************************************************
	 * @Description : This test verifies addition information section labels
	 * @Param: Login credential with admin role 
	 * @Return: No Return
	 * @Author: Nitin Sharma 
	 **************************************************************************************************************
	 */

	@Test
	public void tgl103AdditionalInformationSectionTest(){	
		
		//Step 1 - Login to TGL portal
		LoginPageTgl loginPage = webUtil.openLoginPage();
		loginPage.enterLoginInfo();
		
		//Step 2 - Click on the Search Button
		webUtil.waitUntilElementVisible("Tgl_Search_btn", 10);
		searchPage.clickOnSearchBtn();
		
		//Step 3 - Click on the Applicant
		searchPage.clickFirstRowColumnOnSearchResults();
		
		//Step 4 & 5 - Verify Additional Information Section - Step 4, Step - 5
		soft.assertTrue(webUtil.verifyDocumentInformationSection("AdditionalInformation"));
	}
	@Override
	public TGLConstants getConstants()	{
		return new TGLConstants();
	}
}
