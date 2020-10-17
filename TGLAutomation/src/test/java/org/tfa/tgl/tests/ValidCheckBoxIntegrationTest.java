package org.tfa.tgl.tests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.tfa.framework.core.BaseTestMethods;
import org.tfa.framework.core.JavaScriptUtil;
import org.tfa.framework.utilities.general.RandomUtil;
import org.tfa.tgl.pages.ApplicantCenterPage;
import org.tfa.tgl.pages.LoginPageAppCenter;
import org.tfa.tgl.pages.LoginPageTgl;
import org.tfa.tgl.pages.SearchDetailsPageTGL;
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
	
	private TGLWebUtil webUtil=TGLWebUtil.getObject();
	private SearchPageTGL searchPage= new SearchPageTGL();
	private ApplicantCenterPage applicantCenterPage = new ApplicantCenterPage();
	private LoginPageAppCenter loginAppCenter=new LoginPageAppCenter();
	private SearchDetailsPageTGL searchDetailsPage= new SearchDetailsPageTGL();
	private  Map<String, String> infoMap; 
	private RandomUtil random=new RandomUtil();
	private JavaScriptUtil jsUtil=JavaScriptUtil.getObject();
	Logger log;

	/**
	 **************************************************************************************************************
	 * @Description  : This function is to verify that the Education cost works as in Admissions-9046. 
  	 * @Param: No Parameter
	 * @Return: No Return 
	 * @Author: Surya
	 **************************************************************************************************************
	 */
	
	@Test
	public void tgl11131TestValidCheckBoxIntegrationPoint() throws Exception {
		String applicantID = "4373388";
				
		/* 
		* Step 1 - Login to the TGL  portal application using valid user id
		*/
		LoginPageTgl loginpage=new LoginPageTgl();
		loginpage.enterLoginInfo();	
	
		/* 
		* Step 2 - Search for the Person Id which is going to verify Tax information
		* Click on Search button.
		*/
		webUtil.click("Tgl_Clear_btn");
		searchPage.clickOnMoreSearchOptionsBtn();
		searchPage.enterPersonID(applicantID);
		// Clear app year value - change made to fix script <<NS 21 July 2020>>
		searchPage.clickOnSearchBtn();
		searchPage.clickFirstRowColumnOnSearchResults();
		jsUtil.scrollDownPage(500);
		searchDetailsPage.selectTGLStatusDD("Incomplete");


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
		* Step 5 -  Now login to online part 2 as qamerlin.teachforamerica.org/applicant-center
		* With same login which you have checked the valid check box 
		*/
		loginAppCenter.openLoginPage();
		loginAppCenter.enterLoginInfo();
		webUtil.holdOn(2);
		webUtil.openURL("https://qamerlin.teachforamerica.org/applicant-center/#expenses/transitional-funding");
		webUtil.holdOn(5);
		webUtil.waitForBrowserToLoadCompletely();
				
		/* 
		* Step 7 -  Now  go to cFunding link t check TGL status for check box and notes which you selected in TG
		*/	
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
		WebElement checkBoxValid = webUtil.getDriver().findElement(By.xpath("("+ locatorValue+"/tbody/tr//input)[2]"));
		if(checkBoxValid.isSelected()){
			webUtil.click(checkBoxValid);
		}
		
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
		if(name.equals("Tgl_PrivateLoan_Section")) getSectionName = "Private Loan";
		if(name.equals("Tgl_OtherLoan_Section")) getSectionName = "Other Loan";
		if(name.equals("Tgl_Savings_Section")) getSectionName = "Savings";
		if(name.equals("Tgl_Credit_Section")) getSectionName = "Credit Card debt";
		if(name.equals("Tgl_ApplicantTax_Section")) getSectionName = "Applicant's Tax Return";
		if(name.equals("Tgl_W2Income_Section")) getSectionName = "W-2 or Income Statement";
		if(name.equals("Tgl_ParentTax_section")) getSectionName = "Parent's Tax Return";
		if(name.equals("Tgl_ParentIncome_Section")) getSectionName = "Parent Income Statement";
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
		int len= getValues.size();
		for(int i = 1; i<=len; i++){
			try {
				boolean flag =  webUtil.getDriver().findElement(By.xpath(locatorValue +"["+i+"]/td")).isEnabled();
				if (flag) {
					WebElement sectionName = webUtil.getDriver().findElement(By.xpath(locatorValue +"["+i+"]/td"));
					String getText = sectionName.getText();
					if(getText.contains(valueToCompare)){
						WebElement getNotes = webUtil.getDriver().findElement(By.xpath(locatorValue +"["+i+"]/td[3]"));
						getNotesText = getNotes.getText();
						WebElement getDocumentChecked = webUtil.getDriver().findElement(By.xpath(locatorValue +"["+i+"]/td[2]/input"));
						documentValidCheck= getDocumentChecked.getAttribute("checked");
						addValidationData("SelectorPortalNotes",getNotesText);
						addValidationData("SelectorPortalCheckBox",documentValidCheck);

						break;
					}
				}
			}catch(Exception e) {
							}
		}
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
			WebElement checkBoxRequired = webUtil.getDriver().findElement(By.xpath("("+ locatorValue +"/tbody/tr//input)[1]"));
			if(checkBoxRequired.isSelected()){
				WebElement checkBoxValid = webUtil.getDriver().findElement(By.xpath("("+ locatorValue+"/tbody/tr//input)[2]"));
				locatorValue1=TGLWebUtil.getLocatorValue(locatorValueMap, sections[i]);
				if(!checkBoxValid.isSelected()){
					webUtil.click(checkBoxValid);
					checkBoxChecked ="true";
					selectedSection = sections[i];
					getSectionName = getSectionName(selectedSection);
					WebElement setApplicationNotesObject = webUtil.getDriver().findElement(By.xpath("("+ locatorValue1+"/tbody/tr//textarea)"));
					setApplicationNotesObject.clear();
					setApplicationNotesObject.sendKeys(enterNotes);
					setApplicationNotesObject.sendKeys(Keys.ENTER);
					webUtil.holdOn(10);
					checkValue = true;
					break;
				}else{
					selectedSection = sections[i];
					getSectionName = getSectionName(selectedSection);
					checkValue = false;
				}
			}	
		}
		if(!checkValue){
			getlocatorValue =locatorValue1;
			getSectionName = getSectionName(selectedSection);//NOSONAR
			WebElement setApplicationNotesObject = webUtil.getDriver().findElement(By.xpath("("+ getlocatorValue+"/tbody/tr//textarea)"));
			setApplicationNotesObject.clear();
			setApplicationNotesObject.sendKeys(enterNotes);
			setApplicationNotesObject.sendKeys(Keys.ENTER);
			webUtil.holdOn(10);
		}
		checkBoxValue = selectedSection;
		addValidationData("Notes",enterNotes);
		addValidationData("Section",checkBoxValue);
		addValidationData("SectionName",getSectionName);
		addValidationData("ValidCheckBox",checkBoxChecked);
		return checkBoxValue;
	}

}

