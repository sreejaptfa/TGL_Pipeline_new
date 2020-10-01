package org.tfa.tgl.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.tfa.framework.core.BaseTestMethods;
import org.tfa.framework.utilities.general.RandomUtil;
import org.tfa.tgl.pages.LoginPageTgl;
import org.tfa.tgl.pages.SearchDetailsPageTGL;
import org.tfa.tgl.pages.SearchPageTGL;
import org.tfa.tgl.utilities.web.TGLConstants;
import org.tfa.tgl.utilities.web.TGLWebUtil;

/**
 **************************************************************************************************************
 * @Description  : This class Validate to verify that the TGL associate is able to enter text under send email text box and data is saved
 * @parent: BaseTestMethods class has been extended that has basic methods those will run before suite, before class,
 *          before method, after class, after method etc. 
 * @TestCase     :  TGL11124TestSendEmailTextboxTest()
 * @Author: Surya
 ************************************************************************************************************** 
 */

public class SendEmailTextboxTest extends BaseTestMethods{
	private SearchPageTGL searchPage= new SearchPageTGL();
	private SearchDetailsPageTGL searchDetailsPage = new SearchDetailsPageTGL();
	private TGLWebUtil webUtil=TGLWebUtil.getObject();
	private RandomUtil random=new RandomUtil();
	private static final String TGLSENDEMAILPOPUPMESSAGE="Tgl_SendEmailPopupMessage_header";
	private static final String CONFIRMATIONPOPUP="Verify Confirmation popup is Displayed";

	
	/**
	 **************************************************************************************************************
	 * @Description  : This function is to verify that the TGL associate is able to enter text under send email text box and data is saved
  	 * @Param: No Parameter
	 * @Return: No Return 
	 * @Author: Surya
	 **************************************************************************************************************
	 */
	
	@Test
	public void tgl11124TestSendEmailTextboxTest() throws Exception {
		
		/* Step 1 - Login to the TGL  portal application using valid user id < https://stageweb.tfanet.org/ada/login> */
		LoginPageTgl loginpage=new LoginPageTgl();
		loginpage.enterLoginInfo();
		
		/* Step 2 & 3 - Search for the applicants. Click on the search result row */
		webUtil.waitForBrowserToLoadCompletely();
		searchPage.clickOnSearchBtn();
		searchPage.clickFirstRowColumnOnSearchResults();

		/* Step 4 - Go to bottom of the page, Check the Send Email Test box and Send Button */
		webUtil.setTextBoxClear("Tgl_SendEmailTextBox_ED");
		webUtil.holdOn(2);

		/* Step 5 - Now Enter Data to Text Box */
		String enterSendEmailNotesValue = "QATesting Email Sent_"+random.generateRandomString(5)+random.generateRandomNumber(5);
		searchPage.enterSendEmailNotes("Tgl_SendEmailTextBox_ED",enterSendEmailNotesValue);
		Assert.assertTrue(webUtil.objectIsEnabled("Tgl_SendEmail_Btn"), "Verify Send Email button is Enabled");
				
		/* Step 6 - Click on Send */
		searchPage.clickOnSendEmailBtn();
		Assert.assertTrue(webUtil.objectIsVisible(TGLSENDEMAILPOPUPMESSAGE), CONFIRMATIONPOPUP);
		
		/* Step 7 - Now click on cancel */
		searchDetailsPage.clickOnCancelButton("Tgl_Cancel_btn");
		Assert.assertFalse(webUtil.objectIsVisible(TGLSENDEMAILPOPUPMESSAGE), CONFIRMATIONPOPUP);

		/* Step 8 - Now again click on Send */
		searchPage.clickOnSendEmailBtn();
		Assert.assertTrue(webUtil.objectIsVisible(TGLSENDEMAILPOPUPMESSAGE), CONFIRMATIONPOPUP);

		/* Step 9 - This time Click on Confirm Send */
		searchPage.clickOnConfirmSendBtn();
		webUtil.holdOn(3);
		String actualEmailSendMessage = webUtil.getText("Tgl_EmailSent_Msg");
		String expectedEmailSendMessage = testDataMap.get("EmailSendMessage");
		Assert.assertEquals(actualEmailSendMessage, expectedEmailSendMessage,"Verify you are able to send the message and confimation message display on TGL detail page that your message is send");

		/* Step 10 - End Script */
	}
	@Override
	public TGLConstants getConstants(){
		return new TGLConstants();
	}
}
