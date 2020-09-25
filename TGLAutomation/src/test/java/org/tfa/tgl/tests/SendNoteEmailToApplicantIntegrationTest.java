package org.tfa.tgl.tests;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.tfa.framework.core.BaseTestMethods;
import org.tfa.framework.utilities.general.RandomUtil;
import org.tfa.tgl.pages.LoginPageTgl;
import org.tfa.tgl.pages.SearchPageTGL;
import org.tfa.tgl.utilities.web.TGLConstants;
import org.tfa.tgl.utilities.web.TGLWebUtil;

/**
 **************************************************************************************************************
 * @Description  : This class Validate to verify that the TGL associate able to send an email to an applicant so that they know what needs to happen next. 
 * @parent: BaseTestMethods class has been extended that has basic methods those will run before suite, before class,
 *          before method, after class, after method etc. 
 * @TestCase     :  TGL11124TestSendEmailTextboxTest()
 * @Author: Surya
 ************************************************************************************************************** 
 */
public class SendNoteEmailToApplicantIntegrationTest extends BaseTestMethods{

	private LoginPageTgl loginpage;
	private SearchPageTGL searchPage= new SearchPageTGL();
	private TGLWebUtil webUtil=TGLWebUtil.getObject();
	private RandomUtil random=new RandomUtil();
	static Logger log=Logger.getLogger("rootLogger");
	
	/**
	 **************************************************************************************************************
	 * @Description  : This function is to verify that the TGL associate able to send an email to an applicant so that they know what needs to happen next.
  	 * @Param: No Parameter
	 * @Return: No Return 
	 * @Author: Surya
	 **************************************************************************************************************
	 */
	
	@Test
	public void TGL11129TestSendNoteEmailToApplicantIntegrationPoint() throws Exception {
		String host = testDataMap.get("Host");
		String userEmail = testDataMap.get("userEmail");
		String password = testDataMap.get("emailPassword");
		/* 
		 * Step 1 - Login to the TGL  portal application using valid user id < https://stageweb.tfanet.org/ada/login> 
		 */
		loginpage=new LoginPageTgl();
		loginpage.enterLoginInfo();
	
		/* 
		 * Step 2 - Search for the applicants. Click on the search result row 
		 */
		searchPage.clickOnSearchBtn();
		
		/* 
		 * Step 3 - Click on any applicant 
		 */
		searchPage.clickFirstRowColumnOnSearchResults();

		/* 
		 * Step 4 - Go to bottom of the page, enter text in the Send Email text box.
		 * click on Send button and click on Confirm send button on pop up.   
		 */
		webUtil.setTextBoxClear("Tgl_SendEmailTextBox_ED");
		webUtil.holdOn(2);
		String enterSendEmailNotesValue = "QATesting Email Sent_"+random.generateRandomString(5)+random.generateRandomNumber(5);
		searchPage.enterSendEmailNotes("Tgl_SendEmailTextBox_ED",enterSendEmailNotesValue);
		Assert.assertTrue(webUtil.objectIsEnabled("Tgl_SendEmail_Btn"), "Verify Send Email button is Enabled");
		searchPage.clickOnSendEmailBtn();
		searchPage.clickOnConfirmSendBtn();
		webUtil.holdOn(3);
		String actualEmailSendMessage = webUtil.getText("Tgl_EmailSent_Msg");
		String expectedEmailSendMessage = testDataMap.get("EmailSendMessage");
		Assert.assertEquals(actualEmailSendMessage, expectedEmailSendMessage,"Verify you are able to send the message and confimation message display on TGL detail page that your message is send");
		webUtil.holdOn(60);
		
		/* 
		 * Step 5 - Login to mailbox for test email account and verify the email content match with Email template
		 */
		boolean flag = webUtil.checkEmailContentFromTestEmailAccount(host, userEmail, password, enterSendEmailNotesValue);
		if(!flag){
			Assert.assertTrue(false, "Email Content not found in Test Email Account -> "+enterSendEmailNotesValue);
		}else{
			log.info(enterSendEmailNotesValue +" - Email Content found in Test Email Account");
		}
	}
	
	
	
	@Override
	public TGLConstants getConstants(){
		return new TGLConstants();
	}
}
