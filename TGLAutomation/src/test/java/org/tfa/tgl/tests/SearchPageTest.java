package org.tfa.tgl.tests;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.tfa.framework.core.BaseTestMethods;
import org.tfa.tgl.pages.LoginPageTgl;
import org.tfa.tgl.pages.SearchPageTGL;
import org.tfa.tgl.utilities.web.TGLConstants;
import org.tfa.framework.core.Constants;

public class SearchPageTest extends BaseTestMethods {
	
	private boolean flag;
	private LoginPageTgl loginpage;
	Logger log=Logger.getLogger("rootLogger");
	SoftAssert soft=new SoftAssert();
	SearchPageTGL search=new SearchPageTGL();
	
	/*@Desc: below test verifies filters and ensures search results are expected
	 *@Parameters: Login credential with admin role 
	 *@Important Note: Random search is implemented on InterviewDeadline and TGLStatus together
	 * records are searched and researched until there is minimum one record with given deadline and status
	 *@Author: Nitin Sharma 
	 */
	@Test
	public void TGL101verifySearchResults(){
		
		loginpage=new LoginPageTgl();
		try {
			soft.assertTrue(loginpage.enterLoginInfo(), "TGL login failed");
			soft.assertTrue(search.verifyAppYearDefaultSelection(), "App Year Default Selection failed");
			soft.assertTrue(search.verifymorelinkclick(), "More Link fields test failed");
			soft.assertTrue(search.verifydefaultsort(),"Default sort test failed");	
			soft.assertTrue(search.verifyColumnHeaders(),"Column headers test failed.");
			soft.assertTrue(search.verifyRowIsLinked(), "Row link test failed");
			soft.assertTrue(search.verifyEachFilter());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			soft.fail("Exception Occured"+e);
			log.error("Exception Occured"+e);
			
			
		} finally{
			
			soft.assertAll();
			log.info(this.getClass().getEnclosingMethod() + " Test Execution Completion - Success!");
		}
		
		
	}
	
	@Override
	public TGLConstants getConstants()
	{
		return new TGLConstants();
	}
	
	

}
