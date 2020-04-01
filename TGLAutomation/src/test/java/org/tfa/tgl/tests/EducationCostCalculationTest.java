package org.tfa.tgl.tests;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.tfa.framework.core.BaseTestMethods;
import org.tfa.tgl.pages.LoginPageTgl;
import org.tfa.tgl.pages.SearchDetailsPageTGL;
import org.tfa.tgl.pages.SearchPageTGL;
import org.tfa.tgl.utilities.web.TGLConstants;
import org.tfa.tgl.utilities.web.TGLWebUtil;

/**
 **************************************************************************************************************
 * @Description  : This class Validate to verify Education cost works
 * @parent: BaseTestMethods class has been extended that has basic methods those will run before suite, before class,
 *          before method, after class, after method etc. 
 * @TestCase     :  TGL11127TestEducationCostCalculation()
 * @Author: Surya
 ************************************************************************************************************** 
 */
public class EducationCostCalculationTest extends BaseTestMethods{
	
	private LoginPageTgl loginpage;
	private TGLWebUtil webUtil=TGLWebUtil.getObject();
	private SearchPageTGL searchPage= new SearchPageTGL();
	private SearchDetailsPageTGL searchDetailsPage = new SearchDetailsPageTGL();
	
	/**
	 **************************************************************************************************************
	 * @throws Exception 
	 * @Description  : This function is to verify for Education cost works
  	 * @Param: No Parameter
	 * @Return: No Return 
	 * @Author: Surya
	 **************************************************************************************************************
	 */
	
	@Test
	public void TGL11127TestEducationCostCalculation() throws Exception{
		
		//Step - 1 -------- Login to the TGL  portal application using valid user id < https://qamerlin.teachforamerica.org/ada
		loginpage=new LoginPageTgl();
		loginpage.enterLoginInfo();
		
		//Step - 2 -------- Search for the Person Id which is going to verify Education Cost Click on Search button.
		webUtil.click("Tgl_TGLStatus_DD");
		webUtil.click("Tgl_Complete_LK");
		searchPage.clickOnSearchBtn();
		searchPage.clickFirstRowColumnOnSearchResults();
		
		//Step - 3 --------  Check the document type section
		String[] expectedValues_1 ={"Private Loans Amount","Other Loans Amount"};
		String[] expectedValues_2 ={"Pell Grant:","Federal Loan Amount:","Undergraduate University:","Undergrad Degree Date:","Total Education Cost:","Parent Contribution:","Applicant Cash Contribution:","Parent Student Loan Amount:","Grants and Scholarships Amount:"};
		searchDetailsPage.verifyDocumentTypeList("Tgl_SearchDetailRow_TB","Tgl_SearchDetailCol_TB",expectedValues_1);
		searchDetailsPage.verifyDocumentTypeList("Tgl_DocumenTypeSectionRow_TB","Tgl_DocumenTypeSectionCol_TB",expectedValues_2);
		
		//Step - 4 --------  Verify sub section for private and other loan
		String[] expectedValues_3 ={"Required?","Valid?","Original","Adjusted","Applicant Notes"};
		searchDetailsPage.verifyDocumentTypeList("Tgl_PrivateLoanSectionRow_TB",expectedValues_3);
		searchDetailsPage.verifyDocumentTypeList("Tgl_OtherLoanSectionRow_TB",expectedValues_3);

		//Step - 5 --------  Now Enter Adjusted loan and verify 
		String actualOriginalValue = webUtil.getText("Tgl_PrivateLoan_Original_ST");
		webUtil.setTextBoxValue("Tgl_PrivateLoansAmountAdjusted_ED", "100");
		String expOriginalValue = webUtil.getText("Tgl_PrivateLoan_Original_ST");
		Assert.assertEquals(actualOriginalValue, expOriginalValue, "Verify Original loan remain same");
		
		//Step - 6 --------  End Script 
	}
	@Override
	public TGLConstants getConstants(){
		return new TGLConstants();
	}
}
