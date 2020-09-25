package org.tfa.tgl.tests;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.tfa.framework.core.BaseTestMethods;
import org.tfa.tgl.pages.AwardCalculatorPage;
import org.tfa.tgl.pages.LoginPageTgl;
import org.tfa.tgl.pages.SearchPageTGL;
import org.tfa.tgl.utilities.web.TGLConstants;
import org.tfa.tgl.utilities.web.TGLWebUtil;

/**
 **************************************************************************************************************
 * @Description  : This class Validate to verify Group Calculation
 * @parent: BaseTestMethods class has been extended that has basic methods those will run before suite, before class,
 *          before method, after class, after method etc. 
 * @TestCase     :  TGL11123TestGroupCalculationFieldValidation()
 * @Author: Surya
 ************************************************************************************************************** 
 */
public class GroupCalculationFieldValidationTest extends BaseTestMethods{
	
	private LoginPageTgl loginpage;
	private SearchPageTGL searchPage= new SearchPageTGL();
	private AwardCalculatorPage awardCalculatorPage = new AwardCalculatorPage();
	
	private TGLWebUtil webUtil=TGLWebUtil.getObject();
	static Logger log=Logger.getLogger("rootLogger");
	
	/**
	 **************************************************************************************************************
	 * @Description  : This function is to verify for Group Calculation
  	 * @Param: No Parameter
	 * @Return: No Return 
	 * @Author: Surya
	 **************************************************************************************************************
	 */
	@Test
	public void TGL11123TestGroupCalculationFieldValidation() throws Exception {
	
		/* Step 1 - Login to TGL portal */
		loginpage=new LoginPageTgl();
		loginpage.enterLoginInfo();
		
		/* Step 2 - Now check main page */
		Assert.assertTrue(webUtil.objectIsVisible("Tgl_AwardCalculator_btn"), "Verify Award Calculator button is Enabled");
		
		/* Step 3 - Click on Award Calculator link on top */
		searchPage.clickOnAwardCalculatorBtn();
		Assert.assertTrue(webUtil.objectIsVisible("Tgl_OfferDeadline_DD"), "Verify OfferDeadline DD is Enabled");
		Assert.assertTrue(webUtil.objectIsVisible("Tgl_Regions_DD"), "Verify Regions is Enabled");

		/* Step 4 - Now select Offer deadline or Region */
		webUtil.selectRandomValueFromListBox("Tgl_OfferDeadline_DD");
		Assert.assertTrue(webUtil.objectIsEnabled("Tgl_Calculate_Btn"), "Verify calculate button is Enabled");
		
		/* Step 5 - now select The calculate button */
		awardCalculatorPage.clickOnCalculateBtn();
		Assert.assertTrue(webUtil.objectIsVisible("Tgl_TGLCalculation_header"),"Displayed total applicant who can be calculated and confirmation popup");
		
		/* Step 6 & 7 - Now click "Not Yet.." */
		awardCalculatorPage.clickOnTGLCalculationCancelBtn();
		Assert.assertFalse(webUtil.objectIsVisible("Tgl_TGLCalculation_header"),"Verify award is not calcualted as it got cancelled");
		
		/* Step 8 - Now again go back to screen such a way that you have some data to calculate */
		//Click on Calculate again
		webUtil.selectByIndex("Tgl_OfferDeadline_DD", 0);
		String selectValue[]={"Buffalo","Alabama","Baltimore","Bay Area","D.C. Region","Houston","New Jersey","New York"};
		for(int i=0; i<=selectValue.length-1; i++){
			webUtil.selectByVisibleText("Tgl_Regions_DD", selectValue[i]);
			awardCalculatorPage.clickOnCalculateBtn();
			boolean flag = webUtil.objectIsEnabled("Tgl_TGLCalculation_Calculate_Btn");
			if (flag == true){
				Assert.assertTrue(webUtil.objectIsEnabled("Tgl_TGLCalculation_Calculate_Btn"), "Verify TGL Calculation calculate button is Enabled");
				awardCalculatorPage.clickOnTGLCalculationCalculateBtn();
				String actualAwardCalculationMessage = webUtil.getText("Tgl_AwardCalculation_Msg");
				Assert.assertTrue(actualAwardCalculationMessage.contains("awards calculated"));
				break;
			}else{
				webUtil.click("Tgl_TGLCalculation_Cancel_Btn");
			}
			
			//Assert.assertTrue(flag, "No Awards to Calculate for the Applicatants");
		}
		
		/* Step 9 - End Script */
	}
	
	@Override
	public TGLConstants getConstants(){
		return new TGLConstants();
	}
}
