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
import org.tfa.tgl.pages.SearchDetailsPageTGL;
import org.tfa.tgl.pages.SearchPageTGL;
import org.tfa.tgl.pages.common.ApplicantCenterPage;
import org.tfa.tgl.pages.common.LoginPageTgl;
import org.tfa.tgl.utilities.web.TGLConstants;
import org.tfa.tgl.utilities.web.TGLWebUtil;

/**
 **************************************************************************************************************
 * @Description : This class Validate to verify that the Education cost works as
 *              in Admissions-9046.
 * @parent: BaseTestMethods class has been extended that has basic methods those
 *          will run before suite, before class, before method, after class,
 *          after method etc.
 * @TestCase : TGL11130TestValidCheckBoxIntegrationPoint()
 * @Author: Surya
 ************************************************************************************************************** 
 */
public class ValidCheckBoxIntegrationTest extends BaseTestMethods {

	private TGLWebUtil webUtil = TGLWebUtil.getObject();
	private SearchPageTGL searchPage = new SearchPageTGL();
	private ApplicantCenterPage applicantCenterPage = new ApplicantCenterPage();
	private SearchDetailsPageTGL searchDetailsPage = new SearchDetailsPageTGL();
	private RandomUtil random = new RandomUtil();
	private JavaScriptUtil jsUtil = JavaScriptUtil.getObject();
	Logger log;

	/**
	 **************************************************************************************************************
	 * @Description : This function is to verify that the Education cost works as in
	 *              Admissions-9046.
	 * @Param: No Parameter
	 * @Return: No Return
	 * @Author: Surya
	 **************************************************************************************************************
	 */

	@Test
	public void tgl11131TestValidCheckBoxIntegrationPoint() throws Exception {
		String applicantID = testDataMap.get("ApplicantID");
		String navToTransFundingUrl = testDataMap.get("TransFundingUrl");

		/* 
		* Step 1 - Login to the TGL  portal application using valid user id
		*/
		LoginPageTgl loginPage = webUtil.openLoginPage();
		loginPage.enterLoginInfo();
	
		/* 
		* Step 2 - Search for the Person Id which is going to verify Tax information
		* Click on Search button.
		*/

		searchPage.enterPersonIDAndClickOnSearchButton(applicantID);
		// Clear app year value - change made to fix script <<NS 21 July 2020>>
		jsUtil.scrollDownPage(500);
		searchDetailsPage.selectTGLStatusDD("Incomplete");


		/* 
		* Step 4 - 		Click Valid check box  for any of the available doc type which has required check box checked 
		* Enter Notes for same field
		*/
		String[] sectionsOnTGL = {"Tgl_PrivateLoan_Section","Tgl_OtherLoan_Section","Tgl_Savings_Section","Tgl_Credit_Section","Tgl_ApplicantTax_Section","Tgl_W2Income_Section","Tgl_ParentTax_section","Tgl_ParentIncome_Section"};
		Map<String, String> objectMap = checkValidCheckBoxAndEnterNotes(sectionsOnTGL);
		String getSelectedDocumentSectionFromTGL = objectMap.get("Section");
		String getNotesFromTGL = objectMap.get("Notes");
		String getSectionNameFromTGL = objectMap.get("SectionName");
		String getValidCheckBoxValueFromTGL =objectMap.get("ValidCheckBox");

		/* 
		* Step 5 -  Now login to online part 2 as qamerlin.teachforamerica.org/applicant-center
		* With same login which you have checked the valid check box 
		*/
		applicantCenterPage.openLoginPage();
		applicantCenterPage.enterLoginInfo();
		webUtil.holdOn(2);
		webUtil.openURL(navToTransFundingUrl);
		webUtil.holdOn(5);
		webUtil.waitForBrowserToLoadCompletely();
				
		/* 
		* Step 7 -  Now  go to cFunding link t check TGL status for check box and notes which you selected in TG
		*/	
		Map<String, String> transitionalFundingMap = getValuesFromSelectorPortal("AppCenter_TGLDocuments_TB",getSectionNameFromTGL);
		String getNotesFromSelectorPortal = transitionalFundingMap.get("SelectorPortalNotes");
		String getValidCheckBoxValueFromSelectorPortal = transitionalFundingMap.get("SelectorPortalCheckBox");
		
		Assert.assertEquals(getNotesFromSelectorPortal,getNotesFromTGL, "Notes updated in SelectorPortal");
		Assert.assertEquals(getValidCheckBoxValueFromSelectorPortal,getValidCheckBoxValueFromTGL,"Documentation Verified Checkbox is Checked in Selector Portal ");
		applicantCenterPage.clickOnLogOutLink();
		
		
		/* 
		* Step 8 -  Now come back to TGL portal and uncheck the check box and verify
		*/
		webUtil.openLoginPage();
		searchPage.enterPersonIDAndClickOnSearchButton(applicantID);
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
	/* 
	* This function will get the values from the Applicant center document table 
	*/
	public Map<String, String> getValuesFromSelectorPortal(String locatorName,String valueToCompare){
		String getNotesText = null;
		String documentValidCheck = null;
		Map<String, String> objectMap=new HashMap<>();
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
						objectMap.put("SelectorPortalNotes",getNotesText);
						objectMap.put("SelectorPortalCheckBox",documentValidCheck);

						break;
					}
				}
			}catch(Exception e) {
							}
		}
		return objectMap;

	}
	/* 
	* This function will click on the valid checkbox and enter the notes
	* return the locator
	*/
	
	public Map<String, String> checkValidCheckBoxAndEnterNotes(String[] sections){
		String getlocatorValue;
		String checkBoxValue;
		boolean checkValue = false;
		String locatorValue = null;
		String locatorValue1 = null;
		String selectedSection = null;
		String getSectionName = null;
		String checkBoxChecked ="true";
		String enterNotes="Checked Documentation Verified CheckBox-"+random.generateRandomNumber(5);
		Map<String, String> objectMap=new HashMap<>();
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
		objectMap.put("Notes",enterNotes);
		objectMap.put("Section",checkBoxValue);
		objectMap.put("SectionName",getSectionName);
		objectMap.put("ValidCheckBox",checkBoxChecked);
		return objectMap;
	}

}
