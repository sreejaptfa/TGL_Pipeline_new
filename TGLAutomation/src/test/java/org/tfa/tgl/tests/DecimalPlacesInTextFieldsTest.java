package org.tfa.tgl.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.tfa.framework.core.BaseTestMethods;
import org.tfa.tgl.pages.LoginPageTgl;
import org.tfa.tgl.pages.SearchPageTGL;
import org.tfa.tgl.utilities.web.TGLConstants;
import org.tfa.tgl.utilities.web.TGLWebUtil;

/**
 **************************************************************************************************************
 * @Description  : This class Validate to verify Decimal Places In Text Fields
 * @parent: BaseTestMethods class has been extended that has basic methods those will run before suite, before class,
 *          before method, after class, after method etc. 
 * @TestCase     :  TGL11126TestDecimalPlacesInTextFields()
 * @Author: Surya
 ************************************************************************************************************** 
 */
public class DecimalPlacesInTextFieldsTest extends BaseTestMethods{
	
	private LoginPageTgl loginpage;
	private TGLWebUtil webUtil=TGLWebUtil.getObject();
	private SearchPageTGL searchPage= new SearchPageTGL();
	
	String actualPromptMessage;
	String expectedPromptMessage;
	
	/**
	 **************************************************************************************************************
	 * @throws Exception 
	 * @Description  : This function is to verify for Decimal Places In Text Fields
  	 * @Param: No Parameter
	 * @Return: No Return 
	 * @Author: Surya
	 **************************************************************************************************************
	 */
	@Test
	public void TGL11126TestDecimalPlacesInTextFields() throws Exception  {
		
		//Step - 1 -------- Login to the TGL  portal application using valid user id < https://qamerlin.teachforamerica.org/ada
		loginpage=new LoginPageTgl();
		loginpage.enterLoginInfo();
		
		//Step - 2 -------- Search for Personid to verify Total award section who has already cal award 
		searchPage.clickOnSearchBtn();
		searchPage.clickFirstRowColumnOnSearchResults();
			
		//Step - 3 -------- Enter more than two decimal places in all amount fields
		String enterAmountValue = testDataMap.get("EnterAmount");
		expectedPromptMessage = testDataMap.get("errorMessage_Validation_1");
		
		webUtil.setTextBoxValue("Tgl_PrivateLoansAmountAdjusted_ED", enterAmountValue);
		actualPromptMessage = webUtil.getText("Tgl_PrivateLoansAmountAdjustedErrorMsg_ST");
		Assert.assertTrue(actualPromptMessage.contains(expectedPromptMessage), "Verified the PromptMessage.Please round to the nearest 2 decimal points");
		
		webUtil.setTextBoxValue("Tgl_OtherLoansAmountAdjusted_ED", enterAmountValue);
		actualPromptMessage = webUtil.getText("Tgl_OtherLoansAmountErrorMsg_ST");
		Assert.assertTrue(actualPromptMessage.contains(expectedPromptMessage), "Verified the PromptMessage.Please round to the nearest 2 decimal points.");
		
		//Step - 4 -------- Enter a decimal value in "Total no of dependents" field
		expectedPromptMessage = testDataMap.get("ValidationMessage_2");
		webUtil.setTextBoxValue("Tgl_TotalNoOfDependents_ED", enterAmountValue);
		actualPromptMessage = webUtil.getText("Tgl_TotalNoOfDependentsErrorMsg_ST");
		Assert.assertTrue(actualPromptMessage.contains(expectedPromptMessage), "Verified the PromptMessage.Please round to the nearest whole number.");

		//Step - 5 -------- End
	}
	@Override
	public TGLConstants getConstants(){
		return new TGLConstants();
	}
}