package org.tfa.tgl.tests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.tfa.framework.core.BaseTestMethods;
import org.tfa.framework.core.JavaScriptUtil;
import org.tfa.framework.utilities.general.RandomUtil;
import org.tfa.tgl.pages.ApplicantCenterPage;
import org.tfa.tgl.pages.IMPSPage;
import org.tfa.tgl.pages.LoginPageTgl;
import org.tfa.tgl.pages.SearchPageTGL;
import org.tfa.tgl.utilities.web.TGLConstants;
import org.tfa.tgl.utilities.web.TGLWebUtil;

/**
 **************************************************************************************************************
 * @Description  : This class Validate to verify that the Education cost works as in Admissions-9046. 
 * @parent: BaseTestMethods class has been extended that has basic methods those will run before suite, before class,
 *          before method, after class, after method etc. 
 * @TestCase     :  TGL11130TestValidCheckBoxIntegrationPoint()
 * @Author: Surya
 ************************************************************************************************************** 
 */
public class ValidCheckBoxIntegrationTest extends BaseTestMethods{
	
	private LoginPageTgl loginpage;
	private TGLWebUtil webUtil=TGLWebUtil.getObject();
	private SearchPageTGL searchPage= new SearchPageTGL();
	private ApplicantCenterPage applicantCenterPage = new ApplicantCenterPage();
	private IMPSPage IMPSpage = new IMPSPage();
	private  Map<String, String> infoMap; 
	private RandomUtil random=new RandomUtil();
	private JavaScriptUtil jsUtil=JavaScriptUtil.getObject();

	/**
	 **************************************************************************************************************
	 * @Description  : This function is to verify that the Education cost works as in Admissions-9046. 
  	 * @Param: No Parameter
	 * @Return: No Return 
	 * @Author: Surya
	 **************************************************************************************************************
	 */
	
	@Test
	public void TGL11131TestValidCheckBoxIntegrationPoint() throws Exception {
		String url = testDataMap.get("IMPSURL");
		String userNameIMPS = testDataMap.get("IMPSUserName");
		String passwordIMPS = testDataMap.get("IMPSPassword");
		String urlPart2 = testDataMap.get("OnlinePart2URL");
		String passwordPart2 = testDataMap.get("OnlinePart2Password");
		String updateNotes="Unchecked Documentation Verified CheckBox -"+random.generateRandomNumber(5);
			
		/* 
		* Step 1 - Login to the TGL  portal application using valid user id
		*/
		loginpage=new LoginPageTgl();
		loginpage.enterLoginInfo();	
	
		/* 
		* Step 2 - Search for the Person Id which is going to verify Tax information
		* Click on Search button.
		*/
		searchPage.selectTGLStatusDD("Tgl_InComplete_LK");
		searchPage.clickOnSearchBtn();
		
		/* 
		* Step 3 - Now click on applicant  
		*/
		String applicantID = clickApplicantNameOnSearchResults();
		Assert.assertNotNull(applicantID, "Not returned any related data on Search results");
		webUtil.holdOn(5);
		jsUtil.scrollDownPage(500);

		/* 
		* Step 4 - 		Click Valid check box  for any of the available doc type which has required check box checked 
		* Enter Notes for same field
		*/
		String[] sectionsOnTGL = {"Tgl_PrivateLoan_Section","Tgl_OtherLoan_Section","Tgl_Savings_Section","Tgl_Credit_Section","Tgl_ApplicantTax_Section","Tgl_W2Income_Section","Tgl_ParentTax_section","Tgl_ParentIncome_Section"};
		String getValue = checkValidCheckBoxAndEnterNotes(sectionsOnTGL);
		String getSelectedDocumentSectionFromTGL = getValue;
		String getNotesFromTGL = infoMap.get("Notes");
		String getSectionNameFromTGL = infoMap.get("SectionName");
		String getValidCheckBoxValueFromTGL =infoMap.get("ValidCheckBox");

		/* 
		* Step 5 - Now Go to IMPS and reassign the diff region for same applicant Note: steps to reassign the region
		* Go to IMPS, Search for same applicant, Click on Applicant, Click on Assignment link, You can assign new position there
		*/
		//--> Go to IMPS
		webUtil.openLoginPage(url);
		IMPSpage.validLogin(userNameIMPS,passwordIMPS);
		IMPSpage.clickOnAdmissionsButton();
		
		//--> Search for same applicant
		webUtil.switchToFrameByFrameLocator("CommonMainframeWithName_Frm");
		IMPSpage.enterPersonID(applicantID);
		IMPSpage.clickOnSearchButton();
		String getEmailFromIMPSApplicant = IMPSpage.getEmailFromIMPSApplicantID(applicantID);

		/* 
		* Step 6 -  Now login to online part 2 as qamerlin.teachforamerica.org/applicant-center
		* With same login which you have checked the valid check box 
		*/
		webUtil.openLoginPage((String) urlPart2);
		applicantCenterPage.validLogin(getEmailFromIMPSApplicant,passwordPart2);
				
		/* 
		* Step 7 -  Now  go to cFunding link t check TGL status for check box and notes which you selected in TG
		*/		
		applicantCenterPage.clickOnTransitionalFundingLink();
		getValuesFromSelectorPortal("AppCenter_TGLDocuments_TB",getSectionNameFromTGL);
		String getNotesFromSelectorPortal = infoMap.get("SelectorPortalNotes");
		String getValidCheckBoxValueFromSelectorPortal = infoMap.get("SelectorPortalCheckBox");
		
		Assert.assertEquals(getNotesFromSelectorPortal,getNotesFromTGL, "Notes updated in SelectorPortal");
		Assert.assertEquals(getValidCheckBoxValueFromSelectorPortal,getValidCheckBoxValueFromTGL,"Documentation Verified Checkbox is Checked in Selector Portal ");
		applicantCenterPage.clickOnLogOutLink();
		
		/* 
		* Step 8 -  Now come back to TGL portal and uncheck the check box and verify
		*/
		loginpage=new LoginPageTgl();
		loginpage.enterLoginInfo();	
		searchPage.clickOnMoreSearchOptionsBtn();
		searchPage.enterPersonID(applicantID);
		searchPage.clickOnSearchBtn();
		searchPage.clickFirstRowColumnOnSearchResults();
		Map<String, String> locatorValueMap=webUtil.getLocatorValueMap(getSelectedDocumentSectionFromTGL);
		String locatorValue=TGLWebUtil.getLocatorValue(locatorValueMap, getSelectedDocumentSectionFromTGL);
		WebElement checkBox_Valid = webUtil.getDriver().findElement(By.xpath("("+ locatorValue+"/tbody/tr//input)[2]"));
		if(checkBox_Valid.isSelected()){
			webUtil.click(checkBox_Valid);
			webUtil.holdOn(2);
			addValidationData("UpdatedcheckBoxCheckedFromTGL",null);
		}else{
			addValidationData("UpdatedcheckBoxCheckedFromTGL","true");
		}
		WebElement setApplicationNotes_Object = webUtil.getDriver().findElement(By.xpath("("+ locatorValue+"/tbody/tr//textarea)"));
		setApplicationNotes_Object.clear();
		setApplicationNotes_Object.sendKeys(updateNotes);
		setApplicationNotes_Object.sendKeys(Keys.ENTER);
		webUtil.holdOn(5);
		addValidationData("UpdatedNotesFromTGL",updateNotes);
		
		/* 
		* Step 09 -  go to online part 2 and for same applicant check TGL status
		*/
		webUtil.openLoginPage((String) urlPart2);
		applicantCenterPage.validLogin(getEmailFromIMPSApplicant,passwordPart2);
		applicantCenterPage.clickOnTransitionalFundingLink();
		getValuesFromSelectorPortal("AppCenter_TGLDocuments_TB",getSectionNameFromTGL);
		String getUpdatedNotesFromSelectorPortal = infoMap.get("SelectorPortalNotes");
		String getUpdatedValidCheckBoxValueFromSelectorPortal = infoMap.get("SelectorPortalCheckBox");
		String UpdatedNotesFromTGL = infoMap.get("UpdatedNotesFromTGL");
		String UpdatedcheckBoxCheckedFromTGL = infoMap.get("UpdatedcheckBoxCheckedFromTGL");

		Assert.assertEquals(getUpdatedNotesFromSelectorPortal, UpdatedNotesFromTGL,"Notes updated in SelectorPortal");
		Assert.assertEquals(getUpdatedValidCheckBoxValueFromSelectorPortal, UpdatedcheckBoxCheckedFromTGL,"Documentation Verified Checkbox is Checked in Selector Portal ");
		
		/* 
		* Step 10 - Logout from Applicant Center
		*/
		applicantCenterPage.clickOnLogOutLink();
	}
	@Override
	public TGLConstants getConstants(){
		return new TGLConstants();
	}
	/* 
	* This function will returns the document section names
	* return the getSectionName
	*/
	private String getSectionName(String name){
		String getSectionName= null;
		if(name =="Tgl_PrivateLoan_Section") getSectionName = "Private Loan";
		if(name =="Tgl_OtherLoan_Section") getSectionName = "Other Loan";
		if(name =="Tgl_Savings_Section") getSectionName = "Savings";
		if(name =="Tgl_Credit_Section") getSectionName = "Credit Card debt";
		if(name =="Tgl_ApplicantTax_Section") getSectionName = "Applicant's Tax Return";
		if(name =="Tgl_W2Income_Section") getSectionName = "W-2 or Income Statement";
		if(name =="Tgl_ParentTax_section") getSectionName = "Parent's Tax Return";
		if(name =="Tgl_ParentIncome_Section") getSectionName = "Parent Income Statement";
		return getSectionName;
	}
	private void addValidationData(String validationName, String expectedValue){
		if(infoMap==null){
			infoMap=new HashMap<>();
		}
		infoMap.put(validationName, expectedValue);
	}
	/* 
	* This function will get the values from the Applicant center document table 
	*/
	public String getValuesFromSelectorPortal(String locatorName,String valueToCompare){
		String getNotesText = null;
		String documentValidCheck = null;
		Map<String, String> locatorValueMap=webUtil.getLocatorValueMap(locatorName);
		String locatorValue=TGLWebUtil.getLocatorValue(locatorValueMap, locatorName);
		List<WebElement> getValues = webUtil.getDriver().findElements(By.xpath(locatorValue));
		for(int i = 1; i<=getValues.size(); i++){
			WebElement sectionName = webUtil.getDriver().findElement(By.xpath(locatorValue +"["+i+"]/td"));
			String getText = sectionName.getText();
			if(getText.contains(valueToCompare)){
				WebElement getNotes = webUtil.getDriver().findElement(By.xpath(locatorValue +"["+i+"]/td[3]"));
				getNotesText = getNotes.getText();
				WebElement getDocumentChecked = webUtil.getDriver().findElement(By.xpath(locatorValue +"["+i+"]/td[2]/input"));
				documentValidCheck= getDocumentChecked.getAttribute("checked");
				break;
			}
		}
		addValidationData("SelectorPortalNotes",getNotesText);
		addValidationData("SelectorPortalCheckBox",documentValidCheck);
		return null;
	}
	/* 
	* This function will click on the valid checkbox and enter the notes
	* return the locator
	*/
	public String checkValidCheckBoxAndEnterNotes(String[] sections){
		String getlocatorValue;
		String checkBoxValue;
		boolean checkValue = false;
		String locatorValue = null;
		String locatorValue1 = null;
		String selectedSection = null;
		String getSectionName = null;
		String checkBoxChecked ="true";
		String enterNotes="Checked Documentation Verified CheckBox-"+random.generateRandomNumber(5);
		int len = sections.length-1;
		for(int i= 0; i<=len; i++){
			Map<String, String> locatorValueMap=webUtil.getLocatorValueMap(sections[i]);
			locatorValue=TGLWebUtil.getLocatorValue(locatorValueMap, sections[i]);
			WebElement checkBox_Required = webUtil.getDriver().findElement(By.xpath("("+ locatorValue +"/tbody/tr//input)[1]"));
			if(checkBox_Required.isSelected()){
				WebElement checkBox_Valid = webUtil.getDriver().findElement(By.xpath("("+ locatorValue+"/tbody/tr//input)[2]"));
				locatorValue1=TGLWebUtil.getLocatorValue(locatorValueMap, sections[i]);
				if(!checkBox_Valid.isSelected()){
					webUtil.click(checkBox_Valid);
					checkBoxChecked ="true";
					selectedSection = sections[i];
					checkBoxValue = selectedSection;
					getSectionName = getSectionName(selectedSection);
					WebElement setApplicationNotes_Object = webUtil.getDriver().findElement(By.xpath("("+ locatorValue1+"/tbody/tr//textarea)"));
					setApplicationNotes_Object.clear();
					setApplicationNotes_Object.sendKeys(enterNotes);
					setApplicationNotes_Object.sendKeys(Keys.ENTER);
					webUtil.holdOn(5);
					checkValue = true;
					break;
				}else{
					selectedSection = sections[i];
					checkBoxValue = selectedSection;
					getlocatorValue =locatorValue1;
					getSectionName = getSectionName(selectedSection);
					checkValue = false;
				}
			}	
		}
		if(!checkValue){
			checkBoxValue = selectedSection;
			getlocatorValue =locatorValue1;
			getSectionName = getSectionName(selectedSection);
			WebElement setApplicationNotes_Object = webUtil.getDriver().findElement(By.xpath("("+ getlocatorValue+"/tbody/tr//textarea)"));
			setApplicationNotes_Object.clear();
			setApplicationNotes_Object.sendKeys(enterNotes);
			setApplicationNotes_Object.sendKeys(Keys.ENTER);
			webUtil.holdOn(5);
		}
		checkBoxValue = selectedSection;
		addValidationData("Notes",enterNotes);
		addValidationData("Section",checkBoxValue);
		addValidationData("SectionName",getSectionName);
		addValidationData("ValidCheckBox",checkBoxChecked);
		return checkBoxValue;
	}

	private String clickApplicantNameOnSearchResults() {
		String getApplicantID = null;
		int len = webUtil.getDriver().findElements(By.xpath("//tbody[@data-hook='results']/tr")).size();
		try {
			for(int i=1; i<=len; i++){
				WebElement getPersonID =  webUtil.getDriver().findElement(By.xpath("//tbody[@data-hook='results']/tr["+i+"]/td[2]/a"));
				WebElement getStep =  webUtil.getDriver().findElement(By.xpath("//tbody[@data-hook='results']/tr["+i+"]/td[7]/a"));
				WebElement getExitCode =  webUtil.getDriver().findElement(By.xpath("//tbody[@data-hook='results']/tr["+i+"]/td[8]/a"));
				if((getStep.getText().equals("ASSIGNMENT")) || (getStep.getText().equals("ACCEPTED")) && (getExitCode.getText().equals("N/A"))){
					getApplicantID = getPersonID.getText();
					getPersonID.click();
					break;
				}
			}
		}
			catch (Exception e) {
            try {
				throw new Exception(e);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        } 
		return getApplicantID;
	}
	
}

