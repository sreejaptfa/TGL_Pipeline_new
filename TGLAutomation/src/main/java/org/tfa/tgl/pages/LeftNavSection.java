package org.tfa.tgl.pages;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.tfa.framework.utilities.testdata.TestData;

@SuppressWarnings({ "squid:S1854","squid:MethodCyclomaticComplexity", "squid:ClassCyclomaticComplexity" })
public class LeftNavSection extends PFactory {


	private TestData data = TestData.getObject();
	Logger log = Logger.getLogger("rootLogger");
	List<WebElement> validations;
	private static final String EXCEPTIONOCCURED="Exception occured";
	private static final String VALIDATIONMSG="Validation should not show but it does!";
	private static final String COMPLETE="Complete";
	public LeftNavSection() {
		// call parent class constrctor
		explicitwait = new WebDriverWait(webUtil.getDriver(), 10);
		validations = new ArrayList<>();
		// TestCase: LeftNav(Automatable) Step - 1
		webUtil.openURL(data.getEnvironmentDataMap().get("ApplicationURL"));
	}

	public boolean verifyTopNavSection() {
		boolean flag = false;
		webUtil.click("Tgl_moreSearchOptionsLink");
		webUtil.setTextBoxValueTestData("Tgl_personid", "PIDConfirmed");
		// Clear app year value - change made to fix script <<NS 21 July 2020>>
		webUtil.selectByIndex("Tgl_appyear_dd", 0);
		webUtil.holdOn(1);
		webUtil.click("Home_Tgl_Search2_btn");
		WebElement firstrow = webUtil.getDriver().findElement(firstrowlocator);

		// TestCase: LeftNav(Automatable) Step - 3
		firstrow.click();

		// Uncheck Valid for All documents that are "Required" (to throw validation)
		if (webUtil.getElement("Tgl_EducationCost_privateloanrequired_chk").isSelected())
			while (webUtil.getElement("Tgl_EducationCost_privateloanValid_chk").isSelected())
				webUtil.getElement("Tgl_EducationCost_privateloanValid_chk").click();

		if (webUtil.getElement("Tgl_EducationCost_Otherloanrequired_chk").isSelected())
			while (webUtil.getElement("Tgl_EducationCost_OtherloanValid_chk").isSelected())
				webUtil.getElement("Tgl_EducationCost_OtherloanValid_chk").click();

		if (webUtil.getElement("Tgl_SavingsRequired_Chkbx").isSelected())
			while (webUtil.getElement("Tgl_SavingsValid_Chkbx").isSelected())
				webUtil.getElement("Tgl_SavingsValid_Chkbx").click();

		if (webUtil.getElement("Tgl_CreditRequired_Chkbx").isSelected())
			while (webUtil.getElement("Tgl_CreaditValid_Chkbx").isSelected())
				webUtil.getElement("Tgl_CreaditValid_Chkbx").click();

		if (webUtil.getElement("Tgl_ApplicanttaxRequired_chk").isSelected())
			while (webUtil.getElement("Tgl_ApplicantstaxValid_chk").isSelected())
				webUtil.getElement("Tgl_ApplicantstaxValid_chk").click();

		if (webUtil.getElement("Tgl_TaxInfoIncomeRequired_chk").isSelected())
			while (webUtil.getElement("Tgl_TaxInfoIncomeValid_chk").isSelected())
				webUtil.getElement("Tgl_TaxInfoIncomeValid_chk").click();

		if (webUtil.getElement("Tgl_TaxInfoPTRRequired_chk").isSelected())
			while (webUtil.getElement("Tgl_TaxInfoPTRValid_chk").isSelected())
				webUtil.getElement("Tgl_TaxInfoPTRValid_chk").click();

		if (webUtil.getElement("Tgl_TaxInfoPISRequired_chk").isSelected())
			while (webUtil.getElement("Tgl_TaxInfoPISValid_chk").isSelected())
				webUtil.getElement("Tgl_TaxInfoPISValid_chk").click();
		webUtil.holdOn(3);
		// Empty Mandatory Fields
		webUtil.getElement("Tgl_Taxinfoincome_txt").clear();
		webUtil.getElement("Tgl_TaxInfonoofdependents_txt").clear();
		webUtil.holdOn(4);
		webUtil.getElement("Tgl_Taxinfoincome_txt").sendKeys(" ");
		webUtil.getElement("Tgl_TaxInfonoofdependents_txt").sendKeys(" ");
		webUtil.holdOn(4);

		// Verify there is no validation when the Status is updated as New
		webUtil.selectByVisibleText("Tgl_TGLStatus_dd", "New");
		webUtil.holdOn(3);
		try {
			explicitwait.until(ExpectedConditions.visibilityOfElementLocated(statusvalidations));
			validations = webUtil.getDriver().findElements(statusvalidations);
		}catch (Exception e) {
			log.error(EXCEPTIONOCCURED, e);
			validations.clear();
		}
		if (validations.isEmpty()) {
			flag = true;
		} else {
			log.info(VALIDATIONMSG);
			flag = false;
		}

		// Verify there is no validation when the Status is updated as In Progress
		webUtil.selectByVisibleText("Tgl_TGLStatus_dd", "In Progress");
		webUtil.holdOn(3);
		try {
			explicitwait.until(ExpectedConditions.visibilityOfElementLocated(statusvalidations));
			validations = webUtil.getDriver().findElements(statusvalidations);
		} catch (Exception e) {
			log.error(EXCEPTIONOCCURED, e);
			validations.clear();
		}
		if (validations.isEmpty()) {
			flag = true;
		} else {
			log.info(VALIDATIONMSG);
			flag = false;
		}

		// Verify there is no validation when the Status is updated as Review
		webUtil.selectByVisibleText("Tgl_TGLStatus_dd", "Manager Review");
		webUtil.holdOn(3);
		try {
			explicitwait.until(ExpectedConditions.visibilityOfElementLocated(statusvalidations));
			validations = webUtil.getDriver().findElements(statusvalidations);
		} catch (Exception e) {
			log.error(EXCEPTIONOCCURED, e);
			validations.clear();
		}
		if (validations.isEmpty()) {
			flag = true;
		} else {
			log.info(VALIDATIONMSG);
			flag = false;
		}

		// Verify there is no validation when the Status is updated as Incomplete
		webUtil.selectByVisibleText("Tgl_TGLStatus_dd", "Incomplete");
		webUtil.holdOn(3);
		try {
			explicitwait.until(ExpectedConditions.visibilityOfElementLocated(statusvalidations));
			validations = webUtil.getDriver().findElements(statusvalidations);
		} catch (Exception e) {
			log.error(EXCEPTIONOCCURED, e);
			validations.clear();
		}
		if (validations.isEmpty()) {
			flag = true;
		} else {
			log.info(VALIDATIONMSG);
			flag = false;
		}

		// Verify there is no validation when the Status is updated as Recheck
		webUtil.selectByVisibleText("Tgl_TGLStatus_dd", "Recheck");
		webUtil.holdOn(3);
		try {
			explicitwait.until(ExpectedConditions.visibilityOfElementLocated(statusvalidations));
			validations = webUtil.getDriver().findElements(statusvalidations);
		} catch (Exception e) {
			log.error(EXCEPTIONOCCURED, e);
			validations.clear();
		}
		if (validations.isEmpty()) {
			flag = true;
		} else {
			log.info(VALIDATIONMSG);
			flag = false;
		}

		// Verify there Are validation when the Status is updated as Complete
		webUtil.selectByVisibleText("Tgl_TGLStatus_dd", COMPLETE);
		webUtil.holdOn(3);
		try {
			explicitwait.until(ExpectedConditions.visibilityOfElementLocated(statusvalidations));
			validations = webUtil.getDriver().findElements(statusvalidations);
		} catch (Exception e) {
			log.error(EXCEPTIONOCCURED, e);
			validations.clear();
		}
		if (!validations.isEmpty()) {
			flag = true;
		} else {
			log.info(VALIDATIONMSG);
			flag = false;
		}
		verifyTopNavSectionAssignment();
		// Verify above steps with DEFER Accepted Applicant
		verifyTopNavSectionAccepted();

		return flag;
	}

	public boolean verifyTopNavSectionAssignment() {
		boolean flag = false;
		log.info("verifyTopNavSectionAssignment method called");
		validations.clear();
		//webUtil.getDriver().navigate().to("https://qamerlin.teachforamerica.org/ada/tgl");
		webUtil.getDriver().navigate().to(data.getEnvironmentDataMap().get("ApplicationURL")) ;
		webUtil.holdOn(3);
		webUtil.click("Tgl_moreSearchOptionsLink");
		webUtil.setTextBoxValueTestData("Tgl_personid", "PIDAssignment");
		// Clear app year value - change made to fix script <<NS 21 July 2020>>
		webUtil.selectByIndex("Tgl_appyear_dd", 0);
		webUtil.holdOn(1);
		webUtil.click("Home_Tgl_Search2_btn");
		WebElement firstrow = webUtil.getDriver().findElement(firstrowlocator);

		// TestCase - Additional Information Section - Step 3
		firstrow.click();

		// Uncheck Valid for All documents that are "Required" (to throw validation)
		if (webUtil.getElement("Tgl_EducationCost_privateloanrequired_chk").isSelected())
			while (webUtil.getElement("Tgl_EducationCost_privateloanValid_chk").isSelected())
				webUtil.getElement("Tgl_EducationCost_privateloanValid_chk").click();

		if (webUtil.getElement("Tgl_EducationCost_Otherloanrequired_chk").isSelected())
			while (webUtil.getElement("Tgl_EducationCost_OtherloanValid_chk").isSelected())
				webUtil.getElement("Tgl_EducationCost_OtherloanValid_chk").click();

		if (webUtil.getElement("Tgl_SavingsRequired_Chkbx").isSelected())
			while (webUtil.getElement("Tgl_SavingsValid_Chkbx").isSelected())
				webUtil.getElement("Tgl_SavingsValid_Chkbx").click();

		if (webUtil.getElement("Tgl_CreditRequired_Chkbx").isSelected())
			while (webUtil.getElement("Tgl_CreaditValid_Chkbx").isSelected())
				webUtil.getElement("Tgl_CreaditValid_Chkbx").click();

		if (webUtil.getElement("Tgl_ApplicanttaxRequired_chk").isSelected())
			while (webUtil.getElement("Tgl_ApplicantstaxValid_chk").isSelected())
				webUtil.getElement("Tgl_ApplicantstaxValid_chk").click();

		if (webUtil.getElement("Tgl_TaxInfoIncomeRequired_chk").isSelected())
			while (webUtil.getElement("Tgl_TaxInfoIncomeValid_chk").isSelected())
				webUtil.getElement("Tgl_TaxInfoIncomeValid_chk").click();

		if (webUtil.getElement("Tgl_TaxInfoPTRRequired_chk").isSelected())
			while (webUtil.getElement("Tgl_TaxInfoPTRValid_chk").isSelected())
				webUtil.getElement("Tgl_TaxInfoPTRValid_chk").click();

		if (webUtil.getElement("Tgl_TaxInfoPISRequired_chk").isSelected())
			while (webUtil.getElement("Tgl_TaxInfoPISValid_chk").isSelected())
				webUtil.getElement("Tgl_TaxInfoPISValid_chk").click();
		webUtil.holdOn(3);
		// Empty Mandatory Fields
		webUtil.getElement("Tgl_Taxinfoincome_txt").clear();
		webUtil.getElement("Tgl_TaxInfonoofdependents_txt").clear();
		webUtil.holdOn(4);

		// Verify there is no validation when the Status is updated as New
		webUtil.selectByVisibleText("Tgl_TGLStatus_dd", "New");
		webUtil.holdOn(3);
		try {
			explicitwait.until(ExpectedConditions.visibilityOfElementLocated(statusvalidations));
			validations = webUtil.getDriver().findElements(statusvalidations);
		} catch (Exception e) {
			log.error(EXCEPTIONOCCURED, e);
			validations.clear();
		}
		if (validations.isEmpty()) {
			flag = true;
		} else {
			log.info(VALIDATIONMSG);
			flag = false;
		}

		// Verify there is no validation when the Status is updated as In Progress
		webUtil.selectByVisibleText("Tgl_TGLStatus_dd", "In Progress");
		webUtil.holdOn(3);
		try {
			explicitwait.until(ExpectedConditions.visibilityOfElementLocated(statusvalidations));
			validations = webUtil.getDriver().findElements(statusvalidations);
		} catch (Exception e) {
			log.error(EXCEPTIONOCCURED, e);
			validations.clear();
		}
		if (validations.isEmpty()) {
			flag = true;
		} else {
			log.info(VALIDATIONMSG);
			flag = false;
		}

		// Verify there is no validation when the Status is updated as Review
		webUtil.selectByVisibleText("Tgl_TGLStatus_dd", "Manager Review");
		webUtil.holdOn(3);
		try {
			explicitwait.until(ExpectedConditions.visibilityOfElementLocated(statusvalidations));
			validations = webUtil.getDriver().findElements(statusvalidations);
		} catch (Exception e) {
			log.error(EXCEPTIONOCCURED, e);
			validations.clear();
		}
		if (validations.isEmpty()) {
			flag = true;
		} else {
			log.info(VALIDATIONMSG);
			flag = false;
		}

		// Verify there is no validation when the Status is updated as Incomplete
		webUtil.selectByVisibleText("Tgl_TGLStatus_dd", "Incomplete");
		webUtil.holdOn(3);
		try {
			explicitwait.until(ExpectedConditions.visibilityOfElementLocated(statusvalidations));
			validations = webUtil.getDriver().findElements(statusvalidations);
		} catch (Exception e) {
			log.error(EXCEPTIONOCCURED, e);
			validations.clear();
		}
		if (validations.isEmpty()) {
			flag = true;
		} else {
			log.info(VALIDATIONMSG);
			flag = false;
		}

		// Verify there is no validation when the Status is updated as Recheck
		webUtil.selectByVisibleText("Tgl_TGLStatus_dd", "Recheck");
		webUtil.holdOn(3);
		try {
			explicitwait.until(ExpectedConditions.visibilityOfElementLocated(statusvalidations));
			validations = webUtil.getDriver().findElements(statusvalidations);
		} catch (Exception e) {
			log.error(EXCEPTIONOCCURED, e);
			validations.clear();
		}
		if (validations.isEmpty()) {
			flag = true;
		} else {
			log.info(VALIDATIONMSG);
			flag = false;
		}

		// Verify there Are validation when the Status is updated as Complete
		webUtil.selectByVisibleText("Tgl_TGLStatus_dd", COMPLETE);
		webUtil.holdOn(3);
		try {
			explicitwait.until(ExpectedConditions.visibilityOfElementLocated(statusvalidations));
			validations = webUtil.getDriver().findElements(statusvalidations);
		} catch (Exception e) {
			log.error(EXCEPTIONOCCURED, e);
			validations.clear();
		}
		if (!validations.isEmpty()) {
			flag = true;
		} else {
			log.info(VALIDATIONMSG);
			flag = false;
		}
		return flag;
	}

	public boolean verifyTopNavSectionAccepted() {
		boolean flag = false;
		log.info("verifyTopNavSectionAccepted method called");
		validations.clear();
		//webUtil.getDriver().navigate().to("https://qamerlin.teachforamerica.org/ada/tgl");
		webUtil.getDriver().navigate().to(data.getEnvironmentDataMap().get("ApplicationURL")) ;
		webUtil.holdOn(3);
		webUtil.click("Tgl_moreSearchOptionsLink");
		webUtil.setTextBoxValueTestData("Tgl_personid", "PIDDeferAccept");
		webUtil.selectByIndex("Tgl_appyear_dd", 0);
		webUtil.holdOn(1);
		webUtil.click("Home_Tgl_Search2_btn");
		WebElement firstrow = webUtil.getDriver().findElement(firstrowlocator);

		// TestCase - Additional Information Section - Step 3
		firstrow.click();
		if (webUtil.getElement("Tgl_EducationCost_privateloanrequired_chk").isSelected())
			while (webUtil.getElement("Tgl_EducationCost_privateloanValid_chk").isSelected())
				webUtil.getElement("Tgl_EducationCost_privateloanValid_chk").click();

		if (webUtil.getElement("Tgl_EducationCost_Otherloanrequired_chk").isSelected())
			while (webUtil.getElement("Tgl_EducationCost_OtherloanValid_chk").isSelected())
				webUtil.getElement("Tgl_EducationCost_OtherloanValid_chk").click();

		if (webUtil.getElement("Tgl_SavingsRequired_Chkbx").isSelected())
			while (webUtil.getElement("Tgl_SavingsValid_Chkbx").isSelected())
				webUtil.getElement("Tgl_SavingsValid_Chkbx").click();

		if (webUtil.getElement("Tgl_CreditRequired_Chkbx").isSelected())
			while (webUtil.getElement("Tgl_CreaditValid_Chkbx").isSelected())
				webUtil.getElement("Tgl_CreaditValid_Chkbx").click();

		if (webUtil.getElement("Tgl_ApplicanttaxRequired_chk").isSelected())
			while (webUtil.getElement("Tgl_ApplicantstaxValid_chk").isSelected())
				webUtil.getElement("Tgl_ApplicantstaxValid_chk").click();

		if (webUtil.getElement("Tgl_TaxInfoIncomeRequired_chk").isSelected())
			while (webUtil.getElement("Tgl_TaxInfoIncomeValid_chk").isSelected())
				webUtil.getElement("Tgl_TaxInfoIncomeValid_chk").click();

		if (webUtil.getElement("Tgl_TaxInfoPTRRequired_chk").isSelected())
			while (webUtil.getElement("Tgl_TaxInfoPTRValid_chk").isSelected())
				webUtil.getElement("Tgl_TaxInfoPTRValid_chk").click();

		if (webUtil.getElement("Tgl_TaxInfoPISRequired_chk").isSelected())
			while (webUtil.getElement("Tgl_TaxInfoPISValid_chk").isSelected())
				webUtil.getElement("Tgl_TaxInfoPISValid_chk").click();
		webUtil.holdOn(3);
		// Empty Mandatory Fields
		webUtil.getElement("Tgl_Taxinfoincome_txt").clear();
		webUtil.getElement("Tgl_TaxInfonoofdependents_txt").clear();
		webUtil.holdOn(4);

		// Verify there is no validation when the Status is updated as New
		webUtil.selectByVisibleText("Tgl_TGLStatus_dd", "New");
		webUtil.holdOn(3);
		try {
			explicitwait.until(ExpectedConditions.visibilityOfElementLocated(statusvalidations));
			validations = webUtil.getDriver().findElements(statusvalidations);
		} catch (Exception e) {
			log.error(EXCEPTIONOCCURED, e);
			validations.clear();
		}
		if (validations.isEmpty()) {
			flag = true;
		} else {
			log.info(VALIDATIONMSG);
			flag = false;
		}

		// Verify there is no validation when the Status is updated as In Progress
		webUtil.selectByVisibleText("Tgl_TGLStatus_dd", "In Progress");
		webUtil.holdOn(3);
		try {
			explicitwait.until(ExpectedConditions.visibilityOfElementLocated(statusvalidations));
			validations = webUtil.getDriver().findElements(statusvalidations);
		} catch (Exception e) {
			log.error(EXCEPTIONOCCURED, e);
			validations.clear();
		}
		if (validations.isEmpty()) {
			flag = true;
		} else {
			log.info(VALIDATIONMSG);
			flag = false;
		}

		// Verify there is no validation when the Status is updated as Review
		webUtil.selectByVisibleText("Tgl_TGLStatus_dd", "Manager Review");
		webUtil.holdOn(3);
		try {
			explicitwait.until(ExpectedConditions.visibilityOfElementLocated(statusvalidations));
			validations = webUtil.getDriver().findElements(statusvalidations);
		} catch (Exception e) {
			log.error(EXCEPTIONOCCURED, e);
			validations.clear();
		}
		if (validations.isEmpty()) {
			flag = true;
		} else {
			log.info(VALIDATIONMSG);
			flag = false;
		}

		// Verify there is no validation when the Status is updated as Incomplete
		webUtil.selectByVisibleText("Tgl_TGLStatus_dd", "Incomplete");
		webUtil.holdOn(3);
		try {
			explicitwait.until(ExpectedConditions.visibilityOfElementLocated(statusvalidations));
			validations = webUtil.getDriver().findElements(statusvalidations);
		} catch (Exception e) {
			log.error(EXCEPTIONOCCURED, e);
			validations.clear();
		}
		if (validations.isEmpty()) {
			flag = true;
		} else {
			log.info(VALIDATIONMSG);
			flag = false;
		}

		// Verify there is no validation when the Status is updated as Recheck
		webUtil.selectByVisibleText("Tgl_TGLStatus_dd", "Recheck");
		webUtil.holdOn(3);
		try {
			explicitwait.until(ExpectedConditions.visibilityOfElementLocated(statusvalidations));
			validations = webUtil.getDriver().findElements(statusvalidations);
		} catch (Exception e) {
			log.error(EXCEPTIONOCCURED, e);
			validations.clear();
		}
		if (validations.isEmpty()) {
			flag = true;
		} else {
			log.info(VALIDATIONMSG);
			flag = false;
		}

		// Verify there Are validation when the Status is updated as Complete
		webUtil.selectByVisibleText("Tgl_TGLStatus_dd", COMPLETE);
		webUtil.holdOn(3);
		try {
			explicitwait.until(ExpectedConditions.visibilityOfElementLocated(statusvalidations));
			validations = webUtil.getDriver().findElements(statusvalidations);
		} catch (Exception e) {
			log.error(EXCEPTIONOCCURED, e);
			validations.clear();
		}
		if (!validations.isEmpty()) {
			flag = true;
		} else {
			log.info(VALIDATIONMSG);
			flag = false;
		}
		return flag;
	}
}
