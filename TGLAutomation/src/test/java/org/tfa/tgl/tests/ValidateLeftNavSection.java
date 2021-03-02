package org.tfa.tgl.tests;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;
import org.tfa.framework.core.BaseTestMethods;
import org.tfa.tgl.pages.common.LoginPageTgl;
import org.tfa.tgl.pages.searchdetailsection.LeftNavSection;
import org.tfa.tgl.utilities.web.TGLConstants;
import org.tfa.tgl.utilities.web.TGLWebUtil;

public class ValidateLeftNavSection extends BaseTestMethods{

	private TGLWebUtil webUtil = TGLWebUtil.getObject();
	private LeftNavSection nav = new LeftNavSection();
	Logger log=Logger.getLogger("rootLogger");
	
	/*@Desc: This test verifies Left Nav section (Top Section), refer to testcase for more details - LeftNav - Automatable
	 *@Parameters: Login credential with admin role 
	 *@Author: Nitin Sharma 
	 */
	
	@Test
	public void tgl107TopNavSectionTest(){	
		
		String confirmedPID = testDataMap.get("PIDConfirmed");
		String assignmentPID = testDataMap.get("PIDAssignment");
		String deferAcceptPID = testDataMap.get("PIDDeferAccept");
		
		// Step 1 - Login to TGL portal
		LoginPageTgl loginPage = webUtil.openLoginPage();
		loginPage.enterLoginInfo();
		
		// Step 2 - Search for the Confirmed Person Id  and verify the TopNav Section
		nav.verifyTopNavSection(confirmedPID);
		
		// Step 3 - Search for the Assignment Person Id  and verify the TopNav Section
		nav.verifyTopNavSection(assignmentPID);
		
		// Step 4 - Search for DeferAccept Person Id and verify the TopNav Section
		nav.verifyTopNavSection(deferAcceptPID);
	}
	@Override
	public TGLConstants getConstants(){
		return new TGLConstants();
	}
}
