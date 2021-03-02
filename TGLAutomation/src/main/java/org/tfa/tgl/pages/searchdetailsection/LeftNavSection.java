package org.tfa.tgl.pages.searchdetailsection;
import org.testng.Assert;
import org.tfa.framework.utilities.testdata.TestData;
import org.tfa.tgl.pages.search.SearchPage;
import org.tfa.tgl.pages.searchdetails.SearchDetailsPage;
import org.tfa.tgl.utilities.web.TGLWebUtil;

public class LeftNavSection {

	private SearchPage searchPage = new SearchPage();
	private SearchDetailsPage searchDetailsPage = new SearchDetailsPage();
	private IncomeAndTotalNoDependencySection income = new IncomeAndTotalNoDependencySection();
	private TestData data = TestData.getObject();
	private TGLWebUtil webUtil = TGLWebUtil.getObject();
	private static final String NOMESSAGE="No Message";

	public void verifyTopNavSection(String applicantID) {

		webUtil.openURL(data.getEnvironmentDataMap().get("ApplicationURL"));
		webUtil.holdOn(2);
		
		searchPage.enterPersonIDAndClickOnSearchButton(applicantID);
		searchDetailsPage.selectCheckBoxsForObjectValid("Tgl_SelectCheckBox_chk","Uncheck");
		searchDetailsPage.enterTotalNumberOfDependentsAndIncomeAmount(" ", " ");

		// Verify there is no validation when the Status is updated as New
		Assert.assertTrue(income.verifyValidationMessageForStatusChangeToComplete(NOMESSAGE,"New"));

		// Verify there is no validation when the Status is updated as In Progress
		Assert.assertTrue(income.verifyValidationMessageForStatusChangeToComplete(NOMESSAGE,"In Progress"));

		// Verify there is no validation when the Status is updated as Review
		Assert.assertTrue(income.verifyValidationMessageForStatusChangeToComplete(NOMESSAGE,"Manager Review"));

		// Verify there is no validation when the Status is updated as Incomplete
		Assert.assertTrue(income.verifyValidationMessageForStatusChangeToComplete(NOMESSAGE,"Incomplete"));

		// Verify there is no validation when the Status is updated as Recheck
		Assert.assertTrue(income.verifyValidationMessageForStatusChangeToComplete(NOMESSAGE,"Recheck"));

		// Verify there Are validation when the Status is updated as Complete
		Assert.assertTrue(income.verifyValidationMessageForStatusChangeToComplete("Valid Message","Complete"));
	}

}
