package org.tfa.tgl.pages;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.openqa.selenium.WebElement;

@SuppressWarnings({"squid:S1854","unused","squid:S1905"})
public class TaxInformationSection extends PFactory{

	private static final String TESTCOMMENTS= "Test Comments";
	private static final String VALUE="value";
	private static final String TGLTAXINFOPISAPPNOTESTXT="Tgl_TaxInfoPISAppNotes_txt";
	private static final String TGLTAXINFOAPPTAZRETURNAPPNOTESTXT="Tgl_TaxInfoAppTaxReturnAppNotes_txt";
	private static final String TGLTAXINFOINCOMEAPPNOTESTXT="Tgl_TaxInfoIncomeAppNotes_txt";
	public boolean verifyTaxInformationSection(){
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		boolean flag=false;
		
		webUtil.click("Tgl_Search_btn");
     	webUtil.selectByIndex("Tgl_appyear_dd",2);
    	webUtil.holdOn(1);
		webUtil.click("Home_Tgl_Search2_btn");	
		WebElement firstrow=webUtil.getDriver().findElement(firstrowlocator);				
		firstrow.click();	


		// TestCase - TaxInformation - Step 3
		flag = webUtil.getText("Tgl_TaxInformation_lbl").contains("Tax Information");
		flag = webUtil.getText("Tgl_TaxInfoCanClaim_lbl").contains("Can Claim Dependent:");
		flag = webUtil.getText("Tgl_TaxInfoDependentIncome_lbl").contains("Income:");
		flag = webUtil.getText("Tgl_TaxInfohowmany_lbl").contains("How many dependents:");
		flag = webUtil.getText("Tgl_TaxInfohowmany_lbl").contains("Total # of Dependents:");
		flag = webUtil.isEnabled("Tgl_Taxinfoincome_txt");
		flag = webUtil.isEnabled("Tgl_TaxInfonoofdependents_txt");

		
		// Verify TaxInformation Documents - Applicant's tax return
		//TestCase - TaxInformation - Step 4
		flag = webUtil.getText("Tgl_TaxInforAppTaxReturn_lbl").contains("Applicant's Tax Return");
		flag = webUtil.getText("Tgl_TaxInfoNoOfDependents_lbl").contains("Total # of Dependents:");
		flag = webUtil.isEnabled("Tgl_TaxInforAppTaxReturnedRequired_chk");
		flag = webUtil.isEnabled("Tgl_TaxInfoAppTaxReturnrValid_chk");
		flag = webUtil.isEnabled(TGLTAXINFOAPPTAZRETURNAPPNOTESTXT);
	
			
		// Add test comments in notes section with current date
		webUtil.setTextBoxValue(TGLTAXINFOAPPTAZRETURNAPPNOTESTXT, TESTCOMMENTS+dateFormat.format(date));
		webUtil.holdOn(3);
		
		// Verify Tax Information Document section - W2 or Income Statement
		flag = webUtil.getText("Tgl_TaxInfoIncome_lbl").contains("W-2 or Income Statement");
		flag = webUtil.isEnabled("Tgl_TaxInfoIncomeRequired_chk");
		flag = webUtil.isEnabled("Tgl_TaxInfoIncomeValid_chk");
		flag = webUtil.isEnabled(TGLTAXINFOINCOMEAPPNOTESTXT);
		
		// Add test comments in notes section with current date
		webUtil.setTextBoxValue(TGLTAXINFOINCOMEAPPNOTESTXT, TESTCOMMENTS+dateFormat.format(date));

		// Verify Tax Information Document section - Parent's Tax Return
		flag = webUtil.getText("Tgl_TaxInfoParentsTaxReturn_lbl").contains("Parent's Tax Return");
		flag = webUtil.isEnabled("Tgl_TaxInfoPTRRequired_chk");
		flag = webUtil.isEnabled("Tgl_TaxInfoPTRValid_chk");
		flag = webUtil.isEnabled("Tgl_TaxInfoPTRAppNotes_txt");
		
		// Add test comments in notes section with current date
		webUtil.setTextBoxValue("Tgl_TaxInfoPTRAppNotes_txt", TESTCOMMENTS+dateFormat.format(date));

		// Verify Tax Information Document section - Parent's Tax Return
		flag = webUtil.getText("Tgl_TaxInfoPIS_lbl").contains("Parent Income Statement");
		flag = webUtil.isEnabled("Tgl_TaxInfoPISRequired_chk");
		flag = webUtil.isEnabled("Tgl_TaxInfoPISValid_chk");
		flag = webUtil.isEnabled(TGLTAXINFOPISAPPNOTESTXT);
		
		
		// Add test comments in notes section with current date
		webUtil.setTextBoxValue(TGLTAXINFOPISAPPNOTESTXT, TESTCOMMENTS+dateFormat.format(date));
		webUtil.getDriver().navigate().refresh();
		webUtil.waitForBrowserToLoadCompletely();
		webUtil.holdOn(5);
		
		flag = webUtil.getAttributeValue(TGLTAXINFOPISAPPNOTESTXT, VALUE).contains(TESTCOMMENTS+dateFormat.format(date));
		flag = webUtil.getAttributeValue(TGLTAXINFOINCOMEAPPNOTESTXT, VALUE).contains(TESTCOMMENTS+dateFormat.format(date));
		flag = webUtil.getAttributeValue(TGLTAXINFOINCOMEAPPNOTESTXT, VALUE).contains(TESTCOMMENTS+dateFormat.format(date));
		flag = webUtil.getAttributeValue(TGLTAXINFOAPPTAZRETURNAPPNOTESTXT, VALUE).contains(TESTCOMMENTS+dateFormat.format(date));

		return flag;
	}
	
	// TestCase [Step - 6]
	public boolean verifyHelpLinks(){
		boolean flag=false;
		
		webUtil.click("Tgl_TaxInfo_ApplicantTaxReturnHelp_link");
		flag = webUtil.getText("Tgl_TaxInfoApplicantTaxReturnHelpModal_lbl").contains("More info about Applicant's Tax Return");
	
		webUtil.click("Tgl_TaxInfo_ApplicantTaxReturnClose_btn");		
		webUtil.click("Tgl_TaxInfoW2Help_link");
		flag = webUtil.getText("Tgl_TaxInfo_HelpModal_lbl").contains("More info about W-2 or Income Statement");

		webUtil.click("Tgl_TaxInfoW2HelpMOdalClose_btn");		
		webUtil.click("Tgl_TaxInfoParentsTaxReturnHelp_link");
		flag = webUtil.getText("Tgl_TaxInfoParentsTaxReturnHelpModal_lbl").contains("More info about Parent's Tax Return");

		webUtil.click("Tgl_TaxInfoParentsTaxReturnHelpModalClose_btn");
		webUtil.click("Tgl_TaxInfoPISHelp_link");
		flag = webUtil.getText("Tgl_TaxInfoPISHelpModal_lbl").contains("More info about Parent Income Statement");

		webUtil.click("Tgl_TaxInfoPISHelpModalClose_btn");	
		return flag;
	}

}
