package org.tfa.tgl.tests;


import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.tfa.framework.core.BaseTestMethods;
import org.tfa.tgl.pages.common.LoginPageTgl;
import org.tfa.tgl.pages.search.SearchPage;
import org.tfa.tgl.utilities.web.TGLConstants;
import org.tfa.tgl.utilities.web.TGLWebUtil;

public class ValidateSearchPage extends BaseTestMethods {
	
	Logger log=Logger.getLogger("rootLogger");
	SearchPage search=new SearchPage();
	private TGLWebUtil webUtil = TGLWebUtil.getObject();

	/**
	 **************************************************************************************************************
	 * @throws InterruptedException 
	 * @Description : below test verifies filters and ensures search results are expected
	 * Verify when TGL Admin searches for the data it displays based on the search criteria and validate the data is displaying correctly
	 * @Param: Login credential with admin role 
	 * @Important Note: Random search is implemented on InterviewDeadline and TGLStatus together
	 * records are searched and researched until there is minimum one record with given deadline and status
	 * @Author: Nitin Sharma 
	 **************************************************************************************************************
	 */
	
	@Test
	public void tgl101verifySearchResultsTest() throws InterruptedException{
	
		/* Step 1 - Login to TGL portal */
		LoginPageTgl loginPage = webUtil.openLoginPage();
		loginPage.enterLoginInfo();
		
		// Below assert ensures validation for incorrect input in Person Id field
		Assert.assertTrue(search.verifyErrorMessageForPersonID(), "Method verifyErrorMessageForPersonID Selection failed");
						
		// Below assert ensures  default appyear selection 
		Assert.assertTrue(search.verifyAppYearDefaultSelection(), "App Year Default Selection failed");
			
		// Below assert ensures right fields show up on click of "more search options"
		Assert.assertTrue(search.verifymorelinkclick(), "More Link fields test failed");
			
		// Below assert ensures that table has default sort on the name field
		Assert.assertTrue(search.verifydefaultsort(),"Default sort test failed");	
			
		// Below assert ensures that right columns are listed in search results table
		Assert.assertTrue(search.verifyColumnHeaders(),"Column headers test failed.");
			
		// Below assert ensures that clicking on search result row, opens the details for the given record
		Assert.assertTrue(search.verifyRowIsLinked(), "Row link test failed");

		// Below assert ensures that each Filter is working as expected
		Assert.assertTrue(search.verifySearchFilter());
	}
	
	@Override
	public TGLConstants getConstants()	{
		return new TGLConstants();
	}
}
